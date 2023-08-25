package com.project.LIA.service;


import com.project.LIA.domain.BookDomain;
import com.project.LIA.domain.BookImgDomain;
import com.project.LIA.domain.UserDomain;
import com.project.LIA.repository.BookImgRepository;
import com.project.LIA.repository.BookRepository;
import com.project.LIA.repository.UserRepository;
import com.project.LIA.util.U;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;
    private UserRepository userRepository;
    private BookImgRepository bookImgRepository;

    //파일 경로
    @Value("${app.upload.path}")
    private String uploadDir;

    //페이징 관련 변수
    @Value("${app.pagination.write_pages}")
    private int WRITE_PAGES;
    @Value("${app.pagination.page_rows}")
    private int PAGE_ROWS;

    @Autowired
    public void setPostRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Autowired
    public void setAttachmentRepository(BookImgRepository bookImgRepository) {
        this.bookImgRepository = bookImgRepository;
    }
    @Autowired
    public BookServiceImpl(){
        System.out.println("BoardService() 생성");
    }


    // 1. 글 작성
    @Override
    public int write(BookDomain book){
        // 로그인한 시점의 유저 정보이다.
        UserDomain user = U.getLoggedUser();

        // 위 정보는 session 의 정보 이고, DB 에서 다시 읽어온다
        // 로그인 후 정보가 변경됐을 가능성 때문에 !
        user = userRepository.findById(user.getId()).orElse(null);
        book.setUser(user);  // 글 작성자 세팅!

        // DB 에 저장 -> repository
        bookRepository.saveAndFlush(book);

        return 1;
    } // -첨부파일 X

    public int write(BookDomain book, Map<String, MultipartFile> files){
        // 로그인한 시점의 유저 정보이다.
        UserDomain user = U.getLoggedUser();

        // 위 정보는 session 의 정보 이고, DB 에서 다시 읽어온다
        // 로그인 후 정보가 변경됐을 가능성 때문에 !
        user = userRepository.findById(user.getId()).orElse(null);
        book.setUser(user);  // 글 작성자 세팅!

        // DB 에 저장 -> repository
        bookRepository.save(book);
        // 첨부파일 추가
        addFiles(files, book.getId());

        return 1;
    } // -첨부파일이 O

    //2. 단일 글 보기
    @Override
    public BookDomain selectById(int id){
        BookDomain book= bookRepository.findById((long) id).orElse(null);
        if(book != null){
            // 첨부파일 정보 가져오기
            List<BookImgDomain> fileList = bookImgRepository.findByBook(book.getId());
            setImage(fileList); // 이미지 파일 여부 세팅
            book.setFileList(fileList);

        }

        return book;
    }

    //3. 글 리스트
    @Override   // 전체 리스트
    public List<BookDomain> list(){     // -전체 글 리스트
        return bookRepository.findAll();
    }

    @Override   // 카테고리별 리스트
    public List<BookDomain> cateList(String cate,Integer page, Model model){      // -카테고리별 리스트
        // 현재 페이지 parameter
        if(page == null) page = 1;  // 디폴트는 1 page
        if(page < 1) page = 1;

        // 페이징
        // writePages: 한 [페이징] 당 몇개의 페이지가 표시되나
        // pageRows: 한 '페이지'에 몇개의 글을 리스트 할것인가?
        HttpSession session = U.getSession();
        Integer writePages = (Integer)session.getAttribute("writePages");
        if(writePages == null) writePages = WRITE_PAGES;  // session 에 저장된 값이 없으면 기본값으로 동작
        Integer pageRows = (Integer)session.getAttribute("pageRows");
        if(pageRows == null) pageRows = PAGE_ROWS;

        session.setAttribute("page", page);  // 현재 페이지 번호 -> session 에 저장

        // 주의! PageRequest.of(page, ..)   page 값은 0-base 다!
        Page<BookDomain> pageWrites = bookRepository.findByCate(cate,PageRequest.of(page - 1, pageRows, Sort.by(Sort.Order.desc("id"))));

        long cnt = pageWrites.getTotalElements(); // 글 목록 전체의 개수
        int totalPage =  pageWrites.getTotalPages();  // 총 몇 '페이지' 분량?

        // page 값 보정
        if(page > totalPage) page = totalPage;

        // fromRow 계산 (몇번째 데이터부터?)
        int fromRow = (page - 1) * pageRows;

        // [페이징] 에 표시할 '시작페이지' 와 '마지막페이지' 계산
        int startPage = (((page - 1) / writePages) * writePages) + 1;
        int endPage = startPage + writePages - 1;
        if (endPage >= totalPage) endPage = totalPage;

        model.addAttribute("cnt", cnt);  // 전체 글 개수
        model.addAttribute("page", page); // 현재 페이지
        model.addAttribute("totalPage", totalPage);  // 총 '페이지' 수
        model.addAttribute("pageRows", pageRows);  // 한 '페이지' 에 표시할 글 개수

        // [페이징]
        model.addAttribute("url", U.getRequest().getRequestURI());  // 목록 url
        model.addAttribute("writePages", writePages); // [페이징] 에 표시할 숫자 개수
        model.addAttribute("startPage", startPage);  // [페이징] 에 표시할 시작 페이지
        model.addAttribute("endPage", endPage);   // [페이징] 에 표시할 마지막 페이지

        // 해당 페이지의 글 목록 읽어오기
        List<BookDomain> list = pageWrites.getContent();
        model.addAttribute("list", list);

        return list;
    }

    @Override   //검색 리스트
    public List<BookDomain> searchList(String keyword,Integer page, Model model){
        // 현재 페이지 parameter
        if(page == null) page = 1;  // 디폴트는 1 page
        if(page < 1) page = 1;

        // 페이징
        // writePages: 한 [페이징] 당 몇개의 페이지가 표시되나
        // pageRows: 한 '페이지'에 몇개의 글을 리스트 할것인가?
        HttpSession session = U.getSession();
        Integer writePages = (Integer)session.getAttribute("writePages");
        if(writePages == null) writePages = WRITE_PAGES;  // session 에 저장된 값이 없으면 기본값으로 동작
        Integer pageRows = (Integer)session.getAttribute("pageRows");
        if(pageRows == null) pageRows = PAGE_ROWS;

        session.setAttribute("page", page);  // 현재 페이지 번호 -> session 에 저장

        // 주의! PageRequest.of(page, ..)   page 값은 0-base 다!
        Page<BookDomain> pageWrites = bookRepository.findByNameContaining(keyword,PageRequest.of(page - 1, pageRows, Sort.by(Sort.Order.desc("id"))));

        long cnt = pageWrites.getTotalElements(); // 글 목록 전체의 개수
        int totalPage =  pageWrites.getTotalPages();  // 총 몇 '페이지' 분량?

        // page 값 보정
        if(page > totalPage) page = totalPage;

        // fromRow 계산 (몇번째 데이터부터?)
        int fromRow = (page - 1) * pageRows;

        // [페이징] 에 표시할 '시작페이지' 와 '마지막페이지' 계산
        int startPage = (((page - 1) / writePages) * writePages) + 1;
        int endPage = startPage + writePages - 1;
        if (endPage >= totalPage) endPage = totalPage;

        model.addAttribute("cnt", cnt);  // 전체 글 개수
        model.addAttribute("page", page); // 현재 페이지
        model.addAttribute("totalPage", totalPage);  // 총 '페이지' 수
        model.addAttribute("pageRows", pageRows);  // 한 '페이지' 에 표시할 글 개수

        // [페이징]
        model.addAttribute("url", U.getRequest().getRequestURI());  // 목록 url
        model.addAttribute("writePages", writePages); // [페이징] 에 표시할 숫자 개수
        model.addAttribute("startPage", startPage);  // [페이징] 에 표시할 시작 페이지
        model.addAttribute("endPage", endPage);   // [페이징] 에 표시할 마지막 페이지

        // 해당 페이지의 글 목록 읽어오기
        List<BookDomain> list = pageWrites.getContent();
        model.addAttribute("list", list);

        return list;
    }

    @Override   //페이징
    public List<BookDomain> list(Integer page, Model model) {
        // 현재 페이지 parameter
        if(page == null) page = 1;  // 디폴트는 1 page
        if(page < 1) page = 1;

        // 페이징
        // writePages: 한 [페이징] 당 몇개의 페이지가 표시되나
        // pageRows: 한 '페이지'에 몇개의 글을 리스트 할것인가?
        HttpSession session = U.getSession();
        Integer writePages = (Integer)session.getAttribute("writePages");
        if(writePages == null) writePages = WRITE_PAGES;  // session 에 저장된 값이 없으면 기본값으로 동작
        Integer pageRows = (Integer)session.getAttribute("pageRows");
        if(pageRows == null) pageRows = PAGE_ROWS;

        session.setAttribute("page", page);  // 현재 페이지 번호 -> session 에 저장

        // 주의! PageRequest.of(page, ..)   page 값은 0-base 다!
        Page<BookDomain> pageWrites = bookRepository.findAll(PageRequest.of(page - 1, pageRows, Sort.by(Sort.Order.desc("id"))));

        long cnt = pageWrites.getTotalElements(); // 글 목록 전체의 개수
        int totalPage =  pageWrites.getTotalPages();  // 총 몇 '페이지' 분량?

        // page 값 보정
        if(page > totalPage) page = totalPage;

        // fromRow 계산 (몇번째 데이터부터?)
        int fromRow = (page - 1) * pageRows;

        // [페이징] 에 표시할 '시작페이지' 와 '마지막페이지' 계산
        int startPage = (((page - 1) / writePages) * writePages) + 1;
        int endPage = startPage + writePages - 1;
        if (endPage >= totalPage) endPage = totalPage;

        model.addAttribute("cnt", cnt);  // 전체 글 개수
        model.addAttribute("page", page); // 현재 페이지
        model.addAttribute("totalPage", totalPage);  // 총 '페이지' 수
        model.addAttribute("pageRows", pageRows);  // 한 '페이지' 에 표시할 글 개수

        // [페이징]
        model.addAttribute("url", U.getRequest().getRequestURI());  // 목록 url
        model.addAttribute("writePages", writePages); // [페이징] 에 표시할 숫자 개수
        model.addAttribute("startPage", startPage);  // [페이징] 에 표시할 시작 페이지
        model.addAttribute("endPage", endPage);   // [페이징] 에 표시할 마지막 페이지

        // 해당 페이지의 글 목록 읽어오기
        List<BookDomain> list = pageWrites.getContent();
        model.addAttribute("list", list);

        return list;
    }

    //4. 글 수정
    @Override
    public int update(BookDomain book){    // -첨부파일 X
        bookRepository.save(book);
        return 1;
    }
    @Override
    public int update(BookDomain book      // -첨부파일 O
            , Map<String, MultipartFile> files
            , Long[] delfile){
        int result = 0;

        // update 하고자 하는 것을 일단 읽어와야 한다
        BookDomain b = bookRepository.findById((long) book.getId()).orElse(null);
        if(b != null){
            b.setName(book.getName());
            b.setCate(book.getCate());
            b.setPrice(book.getPrice());
            b.setRental_date(book.getRental_date());
            b.setRental_spot(book.getRental_spot());
            b.setRental_stat(book.getRental_stat());
            b.setBook_detail(book.getBook_detail());
            b = bookRepository.save(b);  // UPDATE

            // 첨부파일 추가
            addFiles(files, book.getId());

            // 삭제할 첨부파일들은 삭제하기
            if(delfile != null){
                for(Long fileId : delfile){
                    BookImgDomain file = bookImgRepository.findById(fileId).orElse(null);
                    if(file != null){
                        delFile(file);  // 물리적 삭제
                        bookImgRepository.delete(file);   // DB에서 삭제
                    }
                }
            }
            result = 1;
        }
        return result;
    }

    //5. 글 삭제
    @Override
    public int deleteById(int id){
        BookDomain book = bookRepository.findById((long) id).orElse(null);
        if(book!=null) {
            bookRepository.delete(book);
            return 1;
        }
        return 0;
    }

    //6. 이미지 관련 메소드
    private void setImage(List<BookImgDomain> fileList){
        // upload 실제 물리적인 경로
        String realPath = new File(uploadDir).getAbsolutePath();

        for(BookImgDomain attachment : fileList){
            BufferedImage imgData = null;
            File f = new File(realPath, attachment.getImg_src());   // 첨부파일에 대한 File 객체
            try{
                imgData = ImageIO.read(f);   // 이미지가 아니면 null 리턴
            } catch (IOException e) {
                System.out.println("파일존재안함: " + f.getAbsolutePath() + " [" + e.getMessage() + "]");
            }
            if(imgData != null) attachment.setImage(true);
        }
    }

    private void delFile(BookImgDomain file) {
        String saveDirectory = new File(uploadDir).getAbsolutePath();

        File f = new File(saveDirectory, file.getImg_src());  // 물리적으로 저장된 파일의 File 객체
        System.out.println("삭제시도---> " + f.getAbsolutePath());

        if(f.exists()){
            if (f.delete()) { // 삭제!
                System.out.println("삭제 성공");
            } else {
                System.out.println("삭제 실패");
            }
        } else {
            System.out.println("파일이 존재하지 않습니다.");
        }
    }

    private void addFiles(Map<String, MultipartFile> files, int id) {
        if(files != null){
            for(var e : files.entrySet()){

                // name="upfile##" 인 경우만 첨부파일 등록. (이유, 다른 웹에디터와 섞이지 않도록)
                if(!e.getKey().startsWith("upfile")) continue;

                // 첨부파일 정보 출력
                System.out.println("\n첨부파일 정보: " + e.getKey());  // name값
                U.printFileInfo(e.getValue());
                System.out.println();

                // 물리적인 파일 저장
                BookImgDomain file = upload(e.getValue());

                // 성공하면 DB 에도 저장
                if(file != null){
                    file.setBook(id);    // FK 설정
                    bookImgRepository.save(file);   // INSERT
                }
            }
        }
    } // end addFile()

    private BookImgDomain upload(MultipartFile multipartFile){
        BookImgDomain attachment = null;

        // 담겨 있는 파일이 없으면 pass~
        String originalFilename = multipartFile.getOriginalFilename();
        if(originalFilename == null || originalFilename.length() == 0) return null;

        // 원본파일명 ( clean )
        String sourceName = StringUtils.cleanPath(originalFilename);

        // 저장될 파일명
        String fileName = sourceName;

        // 파일이 중복되는지 확인
        File file = new File(uploadDir + File.separator + sourceName);
        if(file.exists()){  // 이미 존재하는 파일명!  중복된다면 다른 이름으로 변경하여 저장 시도
            // a.txt => a_2378142783946.txt  : time stamp 값을 활용할거다!

            int pos = fileName.lastIndexOf(".");
            if(pos > -1){  // 확장자가 있는 파일의 경우
                String name = fileName.substring(0, pos);   // 파일 '이름'
                String ext = fileName.substring(pos + 1);   // 파일 '확장자'

                // 중복방지를 위한 새로운 이름 (현재시간 ms) 를 파일명에 추가
                fileName = name + "_" + System.currentTimeMillis() + "." + ext;
            } else {   // 확장자가 없는 파일의 경우
                fileName += "_" + System.currentTimeMillis();
            }
        }
        //  저장할 파일명
        System.out.println("fileName: " + fileName);

        // java.nio.*
        Path copyOfLocation = Paths.get(new File(uploadDir + File.separator + fileName).getAbsolutePath());
        System.out.println(copyOfLocation);

        try{
            Files.copy(   // 물리적으로 저장
                    multipartFile.getInputStream(),
                    copyOfLocation,
                    StandardCopyOption.REPLACE_EXISTING    // 기존에 존재하면 덮어쓰기
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        attachment = BookImgDomain.builder()
                .img_src(sourceName)  // 원본이름
                .build();

        return attachment;
    } // end load();


}
