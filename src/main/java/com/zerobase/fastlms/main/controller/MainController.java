package com.zerobase.fastlms.main.controller;

// mainpage 클래스를 만든 목적 : 매핑하기 위해
// 주소(논리적 주소, 인터넷 주소)와 물리적 파일(프로그래밍을 해야 하니까)

// http://www.naver.com/new/list.do
// 하나의 주소에 대해서
// 어디서 매핑? 누가 매핑?
// 후보군? 클래스, 속성, 메소드
// http://localhost:8080/ 이 주소에 대해 메소드가 맵핑해준다

import com.zerobase.fastlms.component.MailComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//@RestController //주소를 매핑하는 특정한 클래스
@RequiredArgsConstructor
@Controller
public class MainController {
    private final MailComponent mailComponent;
    @RequestMapping("/")
    public String index() {
        String email="thdefn@naver.com";
        String subject="안녕하세요~~";
        String text = "<p>안녕하세요.</p><p>반갑습니다</p>";
        // mailComponent.sendMail(email, subject, text);
        return "index";
    }

    //mvc 패턴으로 만들어진 프레임워크를 통해 모델을 가지고 데이터를 viewing 하는 부분은 분리하자
    //tyhmeleaf라는 템플릿 엔진을 통해 랜더링한다

    // 스프링 : MVC (mvc 중 view -> 템플릿 엔진을 통해 화면에 내용물 출력 by html)
    // .NET : MVC (view -> 출력)
    // python django : MTV (template -> 화면 출력)
    // V -> HTML로 바인딩 ==> 웹 페이지가 된다
    // V -> json으로 바인딩 ==> API

    //request : web -> server
    //response : server -> web
    //1. response 객체를 써서 웹 페이지 발행 > 템플릿 엔진은 이걸 자동화한 것
    @RequestMapping("/hello")
    public void hello(HttpServletRequest request,
                        HttpServletResponse response) throws IOException { // 컨트롤러 메서드에 spring이 자동으로 인젝션 주입을 해준다
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = response.getWriter();

        //텍스트를 찍으면 웹 브라우저에 웹 문서 형태로 전달됨
        String msg = "<html>" +
                "<head>" +
                "<meta charset=\"UTF-8\">"+
                "</head>" +
                "<body>" +
                "<p>hello</p> <p>fastlms website</p>" +
                "<p>안녕하세요@</p>"+
                "</body>" +
                "</html>";

        writer.write(msg);
        writer.close();
    }

    @RequestMapping("/error/denied")
    public String errorDenied() {
        return "error/denied";
    }

}
