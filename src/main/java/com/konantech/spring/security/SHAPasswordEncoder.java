package com.konantech.spring.security;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class SHAPasswordEncoder implements PasswordEncoder {

    private ShaPasswordEncoder shaPasswordEncoder;
    private Object salt = null;

    public SHAPasswordEncoder() {
        shaPasswordEncoder = new ShaPasswordEncoder();
    }

    public SHAPasswordEncoder(int sha) {
        shaPasswordEncoder = new ShaPasswordEncoder(sha);
    }

    public void setEncodeHashAsBase64(boolean encodeHashAsBase64) {
        shaPasswordEncoder.setEncodeHashAsBase64(encodeHashAsBase64);
    }

    public void setSalt(Object salt) {
        this.salt = salt;
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return shaPasswordEncoder.encodePassword(rawPassword.toString(), salt);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return shaPasswordEncoder.isPasswordValid(encodedPassword, rawPassword.toString(), salt);
    }


}
