
package com.nextshow;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHashGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String password = "NextShow@123";
        String hash = encoder.encode(password);

        System.out.println("PASSWORD HASH: " + hash);
    }
}