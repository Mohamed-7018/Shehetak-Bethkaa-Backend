package com.example.sehetak_bethakaa.annotation;


import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.sehetak_bethakaa.exception.UnauthorizedException;
import com.example.sehetak_bethakaa.service.AuthorizationService;

@Aspect
@Component
public class AuthorizeAdminAspect {

    @Autowired
    private HttpServletRequest request;

    @Before("@annotation(authorizeAdmin)")
    public void authorize(JoinPoint joinPoint, AuthorizeAdmin authorizeAdmin) throws UnauthorizedException {
        if (!AuthorizationService.authorize(request)) {
            throw new UnauthorizedException();
        }
    }
}
