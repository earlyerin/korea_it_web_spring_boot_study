package com.koreait.spring_boot_study.controller;

import com.koreait.spring_boot_study.dto.SigninReqDto;
import com.koreait.spring_boot_study.dto.SigninRespDto;
import com.koreait.spring_boot_study.dto.SignupReqDto;
import com.koreait.spring_boot_study.dto.SignupRespDto;
import com.koreait.spring_boot_study.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class Authcontroller {
    //@RequestParam : @GetMapping 시 클라이언트가 URL 쿼리스트링으로 넘긴 값을 메소드 파라미터로 전달
    /*
    사용 주의 사항
    1. 파라미터가 없으면 500Error
    2. 타입, 명칭 불일치
    3. 민감한 정보 사용 금지
     */
    @Autowired
    private AuthService authService;

    @GetMapping("/get")
    public String getUser(@RequestParam String userId){
        return "RequestParam으로 들어온 값 : " + userId;
    }//localhost:8080/auth/get?userId=1024 => ?userId=1024 파라미터로 전달

    @GetMapping("/get/name")
    public String getUserName(@RequestParam(value="name", defaultValue = "홍길동") String userName,
                              @RequestParam(value = "age", required = false) Integer userAge){
        //value = "키" -> 쿼리스트링의 키 값과 변수명이 다를 경우 사용
        //defaultValue = "값" -> 기본값 설정 가능
        //required = false -> 값 전달 필수X, null 포함 가능한 타입(Integer or String)으로 받기
        return userName + userAge;
    }//localhost:8080/auth/get/name?name=rin&age=21 => ?name=rin&age=21 파라미터로 전달

    @GetMapping("get/names")
    public String getUserNames(@RequestParam List<String> names){
        return names.toString();
    } //localhost:8080/auth/get/names?names=rin,min,sin,tin => ?names=rin,min,sin,tin 파라미터로 전달

    @GetMapping("/search")
    public String getSearch(@RequestParam(required = false) String name,
                              @RequestParam(defaultValue = "no-email") String email){
        return "검색조건 - 이름:" + name + ", 이메일:" + email;
    }

    //@RequestBody : HTTP 요청의 <Body>에 들어있는 JSON 데이터를 자바 객체(DTO)로 변환해서 주입
    //클라이언트가 JSON 형식으로 데이터를 보냄 -> 백엔드 서버는 JSON을 DTO로 자동 매핑
    //일반적으로 POST, PUT, PATCH 등에서 사용

    //DTO(Data Transfer Object)
    //데이터를 전달하기 위한 객체, 클라이언트 간에 데이터를 주고 받을 때 사용하는 중간 객체
    //Controller -> DTO -> Entity -> DB
//    @PostMapping("/signup") //회원가입 요청
//    public String signup(@RequestBody SignupReqDto signupReqDto){
//        System.out.println(signupReqDto);
//        return signupReqDto.getUsername() + "님의 회원가입이 완료되었습니다.";
//    }

//    @PostMapping("/signin") //로그인 요청
//    public String signin(@RequestBody SigninReqDto signinReqDto){
//        System.out.println(signinReqDto);
//        return "로그인 완료 : " + signinReqDto.getEmail() + "님 반갑습니다.";
//    }

    //ResponseEntity
    //HTTP 응답 전체를 커스터마이징해서 보낼 수 있는 스프링 클래스
    //HTTP 상태코드, 응답바디, 응답헤더까지 모두 포함
    @PostMapping("signin")
    public ResponseEntity<SigninRespDto> signin(@RequestBody SigninReqDto signinReqDto){
        if(signinReqDto.getEmail() == null || signinReqDto.getEmail().trim().isEmpty()){
            SigninRespDto signinRespDto = new SigninRespDto("failed", "이메일을 다시 입력해주세요.");
            return ResponseEntity.badRequest().body(signinRespDto); //상태코드 : 400 Bad Request
        } else if (signinReqDto.getPassword() == null || signinReqDto.getPassword().trim().isEmpty()) {
            SigninRespDto signinRespDto = new SigninRespDto("failed", "비밀번호를 다시 입력해주세요.");
            return ResponseEntity.badRequest().body(signinRespDto); //상태코드 : 400 Bad Request
        }
        SigninRespDto signinRespDto = new SigninRespDto("success", "로그인 성공");
//        return ResponseEntity.ok(signinRespDto); //상태코드 : 200 OK
        return  ResponseEntity.status(HttpStatus.OK).body(signinRespDto);
    }

    @PostMapping("signup")
    public ResponseEntity<SignupRespDto> signup(@RequestBody SignupReqDto signupReqDto){
        return ResponseEntity.ok().body(authService.signup(signupReqDto));
    }
    //중복 체크와 같은 API는 대부분 200 OK로 응답
    //응답 본문(JSON)에 "중복 여부"를 표시
    //중복 체크는 정상적인 요청에 대한 정상적인 응답이므로 에러코드를 전달하지 않는다.
}
