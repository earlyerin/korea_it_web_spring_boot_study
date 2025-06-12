package com.koreait.spring_boot_study.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignupReqDto { //Request DTO : 클라이언트가 서버로 전달하는 데이터
    private String username;
    private String password;
    private String email;
}
