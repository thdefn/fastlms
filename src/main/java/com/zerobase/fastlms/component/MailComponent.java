package com.zerobase.fastlms.component;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

@RequiredArgsConstructor
@Component
public class MailComponent {

    private final JavaMailSender javaMailSender;

    public void sendMailTest(){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("thdefn@naver.com");
        message.setSubject("안녕하세요. 제로베이스입니다. ");
        message.setText("안녕 반가워요ㅋ.ㅋ");
        javaMailSender.send(message);
    }

    public boolean sendMail(String mail, String subject, String text){
        boolean result = false;

        MimeMessagePreparator msg = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                mimeMessageHelper.setTo(mail);
                mimeMessageHelper.setSubject(subject);
                mimeMessageHelper.setText(text, true);
            }
        };
        try {
            javaMailSender.send(msg);
            result = true;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return result;
    }
}
