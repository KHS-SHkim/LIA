package com.project.LIA.service;

import com.project.LIA.repository.UserRepository;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;


@Service
public class EmailServiceImpl implements EmailService{

//    @Autowired
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

        sendMsg += "<div style='margin:20px; text-align:center;'>";
        sendMsg += "<h1> 안녕하세요 LIA 입니다. </h1>";
        sendMsg += "<h3> 저희 사이트를 이용해주셔서 감사합니다.</h3>";
        sendMsg += "<br>";
        sendMsg += "<p>하단의 코드를 입력해주세요.</p>";
        sendMsg += "<br>";
        sendMsg += "<div style='border:1px solid black;'>";
        sendMsg += "<p style='color:blue; font-size:30px;'>";
        sendMsg += createCode + "</p></div>";

        mimeMessage.setFrom(setFrom);

        mimeMessage.setText(sendMsg, "utf-8", "html");

        return mimeMessage;
    }
    public static String createKey() {
        StringBuffer key = new StringBuffer();
        Random rndNum = new Random();

        for (int i = 0; i < 6; i++) { // 인증코드 6자리
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

    @Override
    public String sendEmail(String email) throws Exception {

        // 메일전송 정보 설정
        MimeMessage mimeMessage = createMessage(email);
        // 메일 전송
        javaMailSender.send(mimeMessage);

        // 인증코드 반환
        return createCode;
    }

    @Override
    public String findPasswordBySendEmail(String email) throws Exception {

        return null;
    }



}
