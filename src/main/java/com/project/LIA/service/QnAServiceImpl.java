package com.project.LIA.service;

import com.project.LIA.domain.QnADomain;
import com.project.LIA.repository.QnaRepository;
import com.project.LIA.util.U;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
@Service
public class QnAServiceImpl implements QnAService {

    @Value("${app.pagination.write_pages}")
    private int WRITE_PAGES;
    @Value("${app.pagination.page_rows}")
    private int PAGE_ROWS;
    private QnaRepository qnARepository;

    @Autowired
    public void setQnARepository(QnaRepository qnARepository) {
        this.qnARepository = qnARepository;
    }

    @Override
    public List<QnADomain> getAllQnA(Integer page, Model model) {

        if(page == null) page = 1;  // 디폴트는 1 page
        if(page < 1) page = 1;

        HttpSession session = U.getSession();
        Integer writePages = (Integer)session.getAttribute("writePages");
        if(writePages == null) writePages = WRITE_PAGES;
        Integer pageRows = (Integer)session.getAttribute("pageRows");
        if(pageRows == null) pageRows = PAGE_ROWS;

        session.setAttribute("page", page);

        Page<QnADomain> pageWrites = qnARepository.findAll(PageRequest.of(page -1, pageRows, Sort.by(Sort.Order.desc("id"))));

        long cnt = pageWrites.getTotalElements();
        int totalPage = pageWrites.getTotalPages();

        if(page > totalPage) page = totalPage;

        int fromRow = (page - 1) * pageRows;

        int startPage = (((page - 1) / writePages) * writePages) + 1;
        int endPage = startPage + writePages -1;
        if(endPage >= totalPage) endPage = totalPage;

        model.addAttribute("cnt", cnt);
        model.addAttribute("page", page);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("pageRows", pageRows);

        model.addAttribute("url", U.getRequest().getRequestURI());
        model.addAttribute("writePages", writePages);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        List<QnADomain> list = pageWrites.getContent();
        model.addAttribute("list", list);

        return list;
    }

    @Override
    public List<QnADomain> listBySearch(String searchValue, Integer page, Model model) {
        if(page == null) page = 1;  // 디폴트는 1 page
        if(page < 1) page = 1;

        HttpSession session = U.getSession();
        Integer writePages = (Integer)session.getAttribute("writePages");
        if(writePages == null) writePages = WRITE_PAGES;
        Integer pageRows = (Integer)session.getAttribute("pageRows");
        if(pageRows == null) pageRows = PAGE_ROWS;

        session.setAttribute("page", page);

        Page<QnADomain> pageWrites = qnARepository.findByQuestionContaining(searchValue, PageRequest.of(page -1, pageRows, Sort.by(Sort.Order.desc("id"))));

        long cnt = pageWrites.getTotalElements();
        int totalPage = pageWrites.getTotalPages();

        if(page > totalPage) page = totalPage;

        int fromRow = (page - 1) * pageRows;

        int startPage = (((page - 1) / writePages) * writePages) + 1;
        int endPage = startPage + writePages -1;
        if(endPage >= totalPage) endPage = totalPage;

        model.addAttribute("cnt", cnt);
        model.addAttribute("page", page);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("pageRows", pageRows);

        model.addAttribute("url", U.getRequest().getRequestURI());
        model.addAttribute("writePages", writePages);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        List<QnADomain> list = pageWrites.getContent();
        model.addAttribute("list", list);

        return list;
    }


    @Override
    public int write(QnADomain qnADomain) {
        qnARepository.save(qnADomain);
        return 1;
    }

    @Override
    public int update(QnADomain qnADomain) {
        String title = qnADomain.getTitle();
        String question = qnADomain.getQuestion();

        qnADomain = qnARepository.findById(qnADomain.getId()).orElse(null);

        if(qnADomain != null){
            qnADomain.setTitle(title);
            qnADomain.setQuestion(question);
            qnARepository.save(qnADomain);
        }

        return 1;
    }

    @Override
    public int delete(QnADomain qnADomain) {
        if(qnADomain != null){
            qnARepository.deleteById(qnADomain.getId());
        }
        return 1;
    }

    @Override
    public QnADomain findById(long id) {
        return qnARepository.findById(id).orElse(null);
    }


}