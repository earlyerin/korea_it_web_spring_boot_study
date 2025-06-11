package com.koreait.spring_boot_study.service;

import com.koreait.spring_boot_study.repository.PostRepository;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    //Field
    private PostRepository postRepository;

    //Constructor
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public String getPost(){
        return postRepository.getPost();
    }

    public String getPostUser(){
        return "사용자 정보 반환";
    }
}
