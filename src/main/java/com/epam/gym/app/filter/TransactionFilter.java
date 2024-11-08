package com.epam.gym.app.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.apache.logging.log4j.ThreadContext;

import java.io.IOException;
import java.util.UUID;

public class TransactionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String transactionId = UUID.randomUUID().toString();
        ThreadContext.put("transactionId", transactionId);
        try {
            chain.doFilter(request, response);
        } finally {
            ThreadContext.remove("transactionId");
        }
    }
}
