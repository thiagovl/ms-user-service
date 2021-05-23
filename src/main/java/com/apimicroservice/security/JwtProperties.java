package com.apimicroservice.security;

public class JwtProperties {
    public static final int EXPIRATION_TIME = 28800000; // 1 hour = 3600000 miliseconds
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SECRET = "53cr3T@";
}
