package com.example.spring.springbootsecurityinky0.controller;

import com.example.spring.springbootsecurityinky0.dto.JoinResponseDTO;
import com.example.spring.springbootsecurityinky0.dto.SecurityUserDto;
import com.example.spring.springbootsecurityinky0.dto.StatusResponseDto;
import com.example.spring.springbootsecurityinky0.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
@RequiredArgsConstructor
public class SignApiController {

    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity<JoinResponseDTO> join(@RequestBody SecurityUserDto securityUserDto) {
        try{
            memberService.join(securityUserDto.toMember());
            return ResponseEntity.ok(
                    JoinResponseDTO.builder()
                            .url("/login")
                            .build()
            );
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    JoinResponseDTO.builder()
                            .url("/")
                            .build()
            );
        }
    }
}
