package ru.group.lab3;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGen {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String pass1 = "pass1";
        System.out.printf("Пароль для %s - %s%n",
                pass1,
                encoder.encode(pass1));

        String pass2 = "pass2";
        System.out.printf("Пароль для %s - %s%n",
                pass2,
                encoder.encode(pass2));
    }
}
