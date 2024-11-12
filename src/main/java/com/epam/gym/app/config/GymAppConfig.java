package com.epam.gym.app.config;

import com.epam.gym.app.filter.TransactionFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.core.Conditions;
import org.zalando.logbook.core.DefaultHttpLogFormatter;
import org.zalando.logbook.core.DefaultHttpLogWriter;
import org.zalando.logbook.core.DefaultSink;

import static com.epam.gym.app.utils.Constants.REST_URL;

@Configuration
@ComponentScan(basePackages = "com.epam.gym.app")
@EnableTransactionManagement
public class GymAppConfig {

    @Bean
    public Logbook logbook() {
        return Logbook.builder()
                .condition(Conditions.exclude(Conditions.requestTo(REST_URL),
                        Conditions.contentType("application/octet-stream"),
                        Conditions.header("X-Secret", "true")))
                .sink(new DefaultSink(new DefaultHttpLogFormatter(), new DefaultHttpLogWriter()))
                .build();
    }

    @Bean
    public FilterRegistrationBean<TransactionFilter> transactionFilter() {
        FilterRegistrationBean<TransactionFilter> registrationBean
                = new FilterRegistrationBean<>();
        registrationBean.setFilter(new TransactionFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}
