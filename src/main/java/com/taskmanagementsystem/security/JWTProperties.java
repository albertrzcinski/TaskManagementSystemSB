package com.taskmanagementsystem.security;

import net.bytebuddy.utility.RandomString;

class JWTProperties {
    static final int EXPIRATION = 300000;
    static final String SECRET = RandomString.make(32);
    static final String PREFIX = "Bearer ";
    static final String HEADER = "Authorization";

}
