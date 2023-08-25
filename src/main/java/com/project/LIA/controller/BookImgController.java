package com.project.LIA.controller;

import com.project.LIA.domain.BookImgDomain;
import com.project.LIA.service.BookImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class BookImgController {
    @Value("${app.upload.path}")
    private String uploadDir;

    private BookImgService bookImgService;

    @Autowired
    public void setAttachmentService(BookImgService bookImgService) {
        this.bookImgService = bookImgService;
    }

    public BookImgController(){
        System.out.println(getClass().getName() + "() 생성");
    }

    // 파일 다운로드
    // id: 첨부파일의 id
    @RequestMapping("/book/download")
    public ResponseEntity<Object> download(Long id){

        if(id == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);  // 400

        BookImgDomain file = bookImgService.findById(id);
        if(file == null) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);  // 404

        String sourceName = file.getImg_src();  // 원본 이름

        String path = new File(uploadDir + File.separator + sourceName).getAbsolutePath();

        try{
            // 파일 유형 (Mimetype)추출
            String mimeType = Files.probeContentType(Paths.get(path));

            // 유형이 지정되지 않는 경우
            if(mimeType == null){
                mimeType = "application/octet-stream";   // 일련의 8bit 스트림 타입.  유형이 알려지지 않은 파일에 대한 형식 지정
            }

            Path filePath = Paths.get(path);
            Resource resource = new InputStreamResource(Files.newInputStream(filePath));

            // response header  세팅
            HttpHeaders headers = new HttpHeaders();
            // ↓ 원본 파일 이름(sourceName) 으로 다운로드 하게 하기위한 세팅
            headers.setContentDisposition(ContentDisposition.builder("attachemnt").filename(URLEncoder.encode(sourceName, "utf-8")).build());
            headers.setCacheControl("no-cache");
            headers.setContentType(MediaType.parseMediaType(mimeType));

            // ResponseEntity 리턴 (body, header, status)
            return new ResponseEntity<>(resource, headers, HttpStatus.OK);   // 200
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);  // 400
        }
    }
}
