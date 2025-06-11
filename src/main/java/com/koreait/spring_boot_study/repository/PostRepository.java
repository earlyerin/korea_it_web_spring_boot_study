package com.koreait.spring_boot_study.repository;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

@Repository
public class PostRepository {
    public String getPost(){
        System.out.println("레포지토리까지 요청이 왔다감");
        return "레포지토리에서 반환된 데이터";
    }
}
