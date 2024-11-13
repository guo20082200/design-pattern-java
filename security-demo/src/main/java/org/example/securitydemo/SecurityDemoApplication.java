package org.example.securitydemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

@SpringBootApplication
public class SecurityDemoApplication {




    public static void main(String[] args) {
        SpringApplication.run(SecurityDemoApplication.class, args);


    }

}
