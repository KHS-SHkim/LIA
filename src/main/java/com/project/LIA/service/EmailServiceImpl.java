package com.project.LIA.service;

import com.project.LIA.repository.UserRepository;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;


@Service
public class EmailServiceImpl implements EmailService{

    @Autowired
    JavaMailSender javaMailSender;

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static final String createCode = createKey();

    private MimeMessage createMessage(String email)throws Exception{

        String setFrom = "fnrrlfnrrl3@gamil.com";
        String toEmail = email;
        String title = "LIA 인증번호";

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        mimeMessage.addRecipients(MimeMessage.RecipientType.TO, toEmail);
        mimeMessage.setSubject(title);

        String sendMsg="";

        sendMsg += "<div style='margin:20px;'";
        sendMsg += "<h1> 안녕하세요 LIA 입니다. </h1>";
        sendMsg += "<br>";
        sendMsg += "<p>아래 코드를 입력해주세요.</p>";
        sendMsg += "<br>";
        sendMsg += "<div align='center' style='border:1px solid black;'>";
        sendMsg += "<h3 style='color:blue;'>인증 코드 입니다.</h3>";
        sendMsg += "<div style='font-size-130%'>";
        sendMsg += "CODE : <strong>";
        sendMsg += createCode + "</string></div><br>";
        sendMsg += "</div>";

        mimeMessage.setFrom(setFrom);

        mimeMessage.setText(sendMsg, "utf-8", "html");

        return mimeMessage;
    }

    @Override
    public String sendEmail(String email) throws Exception {

        MimeMessage mimeMessage = createMessage(email);

        javaMailSender.send(mimeMessage);

        return createCode;
    }

    @Override
    public String findPasswordBySendEmail(String email) throws Exception {

        return null;
    }



    public static String createKey() {
        StringBuffer key = new StringBuffer();
        Random rndNum = new Random();

        for (int i = 0; i < 8; i++) { // 인증코드 8자리
            int index = rndNum.nextInt(3); // 0~2 까지 랜덤

            switch (index) {
                case 0:
                    key.append((char) ((int) (rndNum.nextInt(26)) + 97));
                    //  a~z  (ex. 1+97=98 => (char)98 = 'b')
                    break;
                case 1:
                    key.append((char) ((int) (rndNum.nextInt(26)) + 65));
                    //  A~Z
                    break;
                case 2:
                    key.append((rndNum.nextInt(10)));
                    // 0~9
                    break;
            }
        }
        return key.toString();
    }
}
