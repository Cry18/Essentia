package com.essentia.essentiauser.service.impl;

import com.essentia.essentiauser.dto.AuthResponseDto;
import com.essentia.essentiauser.dto.LoginDto;
import com.essentia.essentiauser.dto.RegisterDto;
import com.essentia.essentiauser.entity.User;
import com.essentia.essentiauser.exception.InvalidCredentialsException;
import com.essentia.essentiauser.exception.UsernameAlreadyTakenException;
import com.essentia.essentiauser.repository.UserRepository;
import com.essentia.essentiauser.security.JwtUtil;
import com.essentia.essentiauser.service.AuthService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implements both AuthService (register/login) and UserDetailsService
 * (required by Spring Security to suppress default auto-configuration).
 */
@Service
public class AuthServiceImpl implements AuthService, UserDetailsService {

    private static final Logger logger = LogManager.getLogger(AuthServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    // ─── Registration and login ──────────────────────────────────────────────

    @Override
    public AuthResponseDto register(RegisterDto dto) {
        logger.debug("Registrazione utente: {}", dto.getUsername());

        if (userRepository.findByUsername(dto.getUsername()) != null) {
            logger.warn("Username già in uso: {}", dto.getUsername());
            throw new UsernameAlreadyTakenException("Username già in uso: " + dto.getUsername());
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setRole(false); // ROLE_USER by default; promote to admin directly in DB

        userRepository.save(user);
        logger.info("Nuovo utente registrato: {} (id={})", user.getUsername(), user.getId());

        String role  = resolveRole(user);
        String token = jwtUtil.generateToken(user.getUsername(), user.getId(), role);
        return new AuthResponseDto(token, user.getId(), user.getUsername(), role);
    }

    @Override
    public AuthResponseDto login(LoginDto dto) {
        logger.debug("Tentativo di login: {}", dto.getUsername());

        User user = userRepository.findByUsername(dto.getUsername());
        if (user == null || !passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            logger.warn("Credenziali non valide per: {}", dto.getUsername());
            throw new InvalidCredentialsException("Username o password non validi");
        }

        logger.info("Login riuscito per utente: {} (id={})", user.getUsername(), user.getId());
        String role  = resolveRole(user);
        String token = jwtUtil.generateToken(user.getUsername(), user.getId(), role);
        return new AuthResponseDto(token, user.getId(), user.getUsername(), role);
    }

    // ─── UserDetailsService (required by Spring Security) ────────────────────

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Utente non trovato: " + username);
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(resolveRole(user)))
        );
    }

    // ─── Support methods ─────────────────────────────────────────────────────

    private String resolveRole(User user) {
        return user.isRole() ? "ROLE_ADMIN" : "ROLE_USER";
    }
}
