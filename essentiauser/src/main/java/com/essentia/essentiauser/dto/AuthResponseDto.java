package com.essentia.essentiauser.dto;

public class AuthResponseDto {

    private String token;
    private int userId;
    private String username;
    private String role;

    public AuthResponseDto(String token, int userId, String username, String role) {
        this.token    = token;
        this.userId   = userId;
        this.username = username;
        this.role     = role;
    }

    public String getToken()    { return token; }
    public int getUserId()      { return userId; }
    public String getUsername() { return username; }
    public String getRole()     { return role; }
}
