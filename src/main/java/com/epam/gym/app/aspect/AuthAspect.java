package com.epam.gym.app.aspect;

import com.epam.gym.app.exception.UserNotAuthenticatedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@AllArgsConstructor
public class AuthAspect {

    private final HttpServletRequest request;

    @Around("@annotation(com.epam.gym.app.annotation.Authenticated)")
    public Object checkAuthentication(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpSession session = request.getSession(false);
        boolean loggedIn = (session != null && session.getAttribute("loggedInUser") != null);

        if (!loggedIn) {
            throw new UserNotAuthenticatedException("Unauthorized access - Please log in first.");
        }
        return joinPoint.proceed();
    }
}
