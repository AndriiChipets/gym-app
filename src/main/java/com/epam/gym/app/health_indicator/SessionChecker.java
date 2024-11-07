package com.epam.gym.app.health_indicator;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
@AllArgsConstructor
@Getter
public class SessionChecker {

    private final HttpSession httpSession;
}
