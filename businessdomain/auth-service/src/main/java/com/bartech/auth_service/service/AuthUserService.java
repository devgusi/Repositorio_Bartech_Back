package com.bartech.auth_service.service;

import com.bartech.auth_service.dto.AuthUserDto;
import com.bartech.auth_service.dto.TokenDto;
import com.bartech.auth_service.entity.AuthUser;
import com.bartech.auth_service.repository.AuthUserRepository;
import com.bartech.auth_service.security.JwtProvider;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthUserService {

    @Autowired
    AuthUserRepository authUserRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtProvider jwtProvider;

    public AuthUser save(AuthUserDto dto) {
        Optional<AuthUser> user = authUserRepository.findByUserName(dto.getUserName());
        if(user.isPresent()){
            return null;
        }
        String password = passwordEncoder.encode(dto.getPassword());
        AuthUser authUser = AuthUser
            .builder()
            .userName(dto.getUserName())
            .password(password)
            .name(dto.getName())
            .surname(dto.getSurname())
            .build();
        return authUserRepository.save(authUser);
    }

    public TokenDto login(AuthUserDto dto) {
        Optional<AuthUser> user = authUserRepository.findByUserName(dto.getUserName());
        if(!user.isPresent()){
            return null;
        }
        if(passwordEncoder.matches(dto.getPassword(), user.get().getPassword())) {
            return new TokenDto(jwtProvider.createToken(user.get()));
        }
        return null;
    }

    public TokenDto validate(String token) {
        if(!jwtProvider.validate(token)) {
            return null;
        }
        String username = jwtProvider.getUserNameFromToken(token);
        if (!authUserRepository.findByUserName(username).isPresent()) {
            return null;
        }
        return new TokenDto(token);
    }
}
