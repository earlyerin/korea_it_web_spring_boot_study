package com.koreait.spring_boot_study.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 Controller
 1. RestController
 2. Controller - 서버 측에서 html 파일(웹페이지)을 반환하기 때문에 백엔드 서버에서 프론트까지 커버
                 (SSR - 서버 사이드 렌더링)
                 해당 요청 경로에 따라서 웹페이지가 모두 할당되므로 비교적 느리고 서버가 무거워짐
 */
@Controller
public class MainController {
    @GetMapping("/main")
    public String getMain(){
        return "main.html"; //resources.static - html 파일 반환(정적 웹페이지)
    }

    /*
    Thymeleaf
    - 서버에서 HTML을 렌더링할 때, 자바 데이터를 끼워넣을 수 있게 해주는 템플릿 엔진
    - 동적 웹페이지 처리
    - 서버 사이드 템플릿
    - resources.templates - html 파일 저장
    ** 템플릿엔진이란, 지정된 템플릿 양식과 데이터가 합쳐져 html 문서를
                     출력하는 소프트웨어
     */
    @GetMapping("/profile")
    public String getProfile(Model model){ //html 파일로 데이터를 전달할 매개변수
        model.addAttribute("username", "<b>rin</b>");
        model.addAttribute("isAdult", true);
        model.addAttribute("age",20);
        Map<String, String> userList = new HashMap<>();
        userList.put("name1", "nameA");
        userList.put("name2", "nameB");
        userList.put("name3", "nameC");
        userList.put("name4", "nameD");
        userList.put("name5", "nameE");
        model.addAttribute("userList",userList);
        return "profile.html"; //resources.templates - html 파일 반환
    }

    @GetMapping("/search")
    public String getSearch(@RequestParam String keyword, Model model){
        //RequestParam과 model을 함께 사용
        model.addAttribute("keyword", keyword);
        return "search.html";
    }

    private List<UserDto> users = new ArrayList<>();

    @GetMapping("/signup")
    public String signup(){
        return "signup";
    }

    @PostMapping("/signup")
    public String signupSubmit(@RequestParam String name, @RequestParam int age, Model model){
        UserDto userDto = new UserDto(users.size()+1, name, age);
        users.add(userDto);
        model.addAttribute("message", name + "님, 가입을 환영합니다.");
        return "signup-result";
    }

    @GetMapping("/users")
    public String userList(Model model){
        model.addAttribute("users", users);
        return "users.html";
    }

}

@Data
@AllArgsConstructor
class UserDto{
    private int userId;
    private String userName;
    private int userAge;
}
