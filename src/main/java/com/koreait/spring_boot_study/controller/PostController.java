package com.koreait.spring_boot_study.controller;

import com.koreait.spring_boot_study.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@Controller
@RestController
@RequestMapping("/post") //공통적인 상위 경로
public class PostController {
    //IOC_Inversion Of control(제어의 역전)
    //객체의 주도권을 개발자가 아닌 스프린부트가 가짐
    //Springboot에서 IOC container에 객체를 담아두고 관리
    //필드와 생성자에 필요한 객체를 자동으로 넣어주기 때문에 직접 생성할 필요 없음
    //예) 빌드 -> IOC container -> PostController 객체 생성 -> 생성자 -> PostService 객체 생성 (연쇄작용, 의존성)

    //DI_Dependency Injection(의존성 주입)
    //필요한 객체(의존성)를 직접 만들지 않고, 외부(스프링부트)에서 대신 넣어주는 것
    //1. 생성자로 의존성 주입(권장하는 방식, 객체 생성 시 주입)
//Field
//    private final PostService postService; => final로 선언, 불변을 보장
//
//Constructor
//    public PostController(PostService postService){ => 주입 강제
//        this.postService = postService;
//    }

    //2. @Autowired 로 의존성 주입(지양하는 방식,컨테이너 초기화 시 주입)
    // 주입 시점 오인으로 인해 NPE 발생 가능성 존재
    @Autowired
    private PostService postService;

    @GetMapping("/get") //요청 경로
    public String getPost(){
        System.out.println("get으로 들어온 요청입니다.");
        return postService.getPost();
    }


    @GetMapping("/user")
    public String getPostUser(){
        System.out.println("get/user로 들어온 요청입니다.");
        return postService.getPostUser();
    }
}

/*
 Controller
 1. RestController - 프론트로 Json, 문자열 등 데이터 반환
                     REST API 서버(웹서버) (CSR - 클라이언트 사이드 렌더링 <=> SPA - 싱글 페이지 어플리케이션)
                     프로트렌드 쪽에서 웹페이지 로드하고 백엔드는 요청에 해당하는 데이터만 반환하므로 비교적 빠름
                     예) React(프론트엔드) - Springboot(백엔드)
 2. Controller - 서버 측에서 html 파일(웹페이지)을 반환하기 때문에 백엔드 서버에서 프론트까지 커버
                 (SSR - 서버 사이드 렌더링)
                 해당 요청 경로에 따라서 웹페이지가 모두 할당되므로 비교적 느리고 서버가 무거워짐
 */