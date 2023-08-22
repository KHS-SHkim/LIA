package com.project.LIA.service;

public interface EmailService {
    String sendEmail(String email) throws Exception;

    String findPasswordBySendEmail(String email) throws Exception;
}
