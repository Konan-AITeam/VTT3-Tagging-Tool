package com.konantech.spring.info;

import com.konantech.spring.security.SHAPasswordEncoder;
import org.junit.Test;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

public class PasswordTest {

    private MessageDigestPasswordEncoder sha256PasswordEncoder;



    @Test
    public void passwordTest() {
        String str = "konan1";
        String str2 = "5963a7392f463055e95c92c5ed67c1004cb9bd969b23a2012fc8134a942006a16a0818a735f084fd9e4e6ddee520ac748b58b0b6b8989d6fcbc81e62a035b4c0";
        PasswordEncoder passwordEncoder1 = new BCryptPasswordEncoder();
        PasswordEncoder passwordEncoder2 = new StandardPasswordEncoder();
        SHAPasswordEncoder passwordEncoder3 = new SHAPasswordEncoder(512);

        System.out.println(passwordEncoder1.encode(str));
        System.out.println(passwordEncoder2.encode(str));
        System.out.println(passwordEncoder3.encode(str));
        System.out.println(passwordEncoder3.matches(str,str2));
    }
}
