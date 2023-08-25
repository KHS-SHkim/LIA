package com.project.LIA.domain;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        boolean result = UserVofR.class.isAssignableFrom(clazz);
        return result;
    }

    @Override
    public void validate(Object target, Errors errors) {

        UserVofR userVofR = (UserVofR) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"username", "아이디는 필수입니다.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "비밀번호는 필수입니다.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nickname", "닉네임은 필수입니다.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone", "전화번호는 필수입니다.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "이메일은 필수입니다.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "post_num", "우편번호는 필수입니다.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "주소는 필수입니다.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address_detail", "상세주소는 필수입니다.");

        if(!userVofR.getPassword().equals(userVofR.getRe_password())){
            errors.rejectValue("re_password","비밀번호가 일치하지 않습니다.");
        }

        String username = userVofR.getUsername();
        String usernameRegex = "^[a-zA-Z0-9]*$";

        Pattern pattern = Pattern.compile(usernameRegex);
        Matcher matcher = pattern.matcher(username);
        boolean bool = matcher.matches();
        if(!bool){
            errors.rejectValue("username","아이디는 영문,숫자 만 사용가능합니다.");
        }


        String phone = userVofR.getPhone();
        String phoneRegex = "^[0-9]{11}$";
        Pattern pattern1 = Pattern.compile(phoneRegex);
        Matcher matcher1 = pattern1.matcher(phone);
        boolean bool1 = matcher1.matches();
        if(!bool){
            errors.rejectValue("phone","11자리 전화번호를 입력해주세요");
        }

        String email = userVofR.getEmail();
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Pattern pattern2 = Pattern.compile(emailRegex);
        Matcher matcher2 = pattern2.matcher(email);
        boolean bool2 = matcher2.matches();
        if(!bool){
            errors.rejectValue("email", "이메일 형식이 올바르지 않습니다.");
        }

    }
}
