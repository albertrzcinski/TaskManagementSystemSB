package com.taskmanagementsystem.security;

import net.bytebuddy.utility.RandomString;

public class JWTProperties {
    static final int EXPIRATION = 3000000;
    public static final String SECRET = RandomString.make(32);
    static final String PREFIX = "Bearer ";
    static final String HEADER = "Authorization";

}
