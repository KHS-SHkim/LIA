package com.project.LIA.domain;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

public class BookValidator implements Validator {

    // 이 Validator 가 제공된 Class 의 인스턴스(clazz)를 유효성검사할 수 있는가?
    @Override
    public boolean supports(Class<?> clazz) {
        System.out.println("supports(" + clazz.getName() + ")");

        // ↓ 검증할 객체의 클래스 타입인지 확인 : Post = clazz; 가능 여부
        boolean result = BookDomain.class.isAssignableFrom(clazz);
        System.out.println(result);
        return result;
    }

    @Override
    public void validate(Object target, Errors errors) {
        BookDomain book = (BookDomain)target;
        System.out.println("validate() 호출: " + book);

        // 바인딩한 객체에 대한 검증 수행

        //BookDomain 필드 (모두 NN)

        String name = book.getName();
        String cate = book.getCate();
        String price = Integer.toString(book.getPrice());
        String rental_spot = book.getRental_spot();
        String rental_stat = book.getRental_stat();
        LocalDate rental_start = book.getRental_start();
        LocalDate rental_end = book.getRental_end();
        String book_detail = book.getBook_detail();


        if(name == null || name.trim().isEmpty()){
            errors.rejectValue("name", "글 제목은 필수입니다");
        }
        if(cate == null || cate.trim().isEmpty()){
            errors.rejectValue("cate", "카테고리 선택은 필수입니다");
        }
        if(price == null || price.trim().isEmpty()){
            errors.rejectValue("price", "가격은 필수입니다");
        }
        if(rental_spot == null || rental_spot.trim().isEmpty()){
            errors.rejectValue("rental_spot", "대여지역은 필수입니다");
        }
        if(rental_stat == null || rental_stat.trim().isEmpty()){
            errors.rejectValue("rental_stat", "대여상태는 필수입니다");
        }
        if(book_detail == null || book_detail.trim().isEmpty()){
            errors.rejectValue("book_detail", "글 내용은 필수입니다");
        }
        if (rental_start != null && rental_end != null && rental_end.isBefore(rental_start)) {
            errors.rejectValue("rental_end", "반납일은 대여일보다 이후여야 합니다");
        }
        //대여중이라면 대여일과 반납일을 쓰도록
        if ((rental_start == null || rental_end == null) && rental_stat.equals("대여중")) {
            errors.rejectValue("rental_end", "대여일과 반납일을 입력해주세요");
        }


    }
}
