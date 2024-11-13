package org.example.securitydemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;


    //@Secured()
    @GetMapping("/abc")
    public String hello() {

        System.out.println(authenticationManagerBuilder);
        AuthenticationManager authenticationManager = authenticationManagerBuilder.getObject();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("admin", "admin"));
        return "hhh";
    }
}
