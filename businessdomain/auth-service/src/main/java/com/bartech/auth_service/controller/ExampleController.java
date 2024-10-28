package com.bartech.auth_service.controller;

import com.bartech.auth_service.dto.AuthUserDto;
import com.bartech.auth_service.dto.TokenDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/example/V1")
public class ExampleController {


    @GetMapping("/get")
    public ResponseEntity<String> get() {
        return ResponseEntity.ok("hola");
    }
}
