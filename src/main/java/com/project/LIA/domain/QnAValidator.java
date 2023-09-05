package com.project.LIA.domain;


import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class QnAValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        boolean result = QnADomain.class.isAssignableFrom(clazz);
        return result;
    }

    @Override
    public void validate(Object target, Errors errors) {

        QnADomain qnADomain = (QnADomain) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"title","제목은 필수입니다.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"question","내용은 필수입니다.");

    }
}