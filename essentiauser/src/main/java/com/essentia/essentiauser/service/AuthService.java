package com.essentia.essentiauser.service;

import com.essentia.essentiauser.dto.AuthResponseDto;
import com.essentia.essentiauser.dto.LoginDto;
import com.essentia.essentiauser.dto.RegisterDto;

public interface AuthService {
    AuthResponseDto register(RegisterDto dto);
    AuthResponseDto login(LoginDto dto);
}
