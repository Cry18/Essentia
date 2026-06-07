package com.essentia.essentiauser.controller;

import com.essentia.essentiauser.dto.AuthResponseDto;
import com.essentia.essentiauser.dto.LoginDto;
import com.essentia.essentiauser.dto.RegisterDto;
import com.essentia.essentiauser.service.impl.AuthServiceImpl;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Public endpoints for user registration and login.
 * Returns a JWT to use as Bearer token in subsequent requests.
 *
 * POST /api/auth/register  →  201 Created + AuthResponseDto
 * POST /api/auth/login     →  200 OK       + AuthResponseDto
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LogManager.getLogger(AuthController.class);

    @Autowired
    private AuthServiceImpl authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponseDto register(@Valid @RequestBody RegisterDto dto) {
        logger.debug("POST /api/auth/register - username: {}", dto.getUsername());
        return authService.register(dto);
    }

    @PostMapping("/login")
    public AuthResponseDto login(@Valid @RequestBody LoginDto dto) {
        logger.debug("POST /api/auth/login - username: {}", dto.getUsername());
        return authService.login(dto);
    }
}
