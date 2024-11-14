package com.avitam.bankloanapplication;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Locale;

@SpringBootApplication
@EnableMongoRepositories("com.gulbalasalamov.bankloanapplication.repository")
@EnableElasticsearchRepositories(basePackages = "com.gulbalasalamov.bankloanapplication")
@ComponentScan(
        {
                "com.gulbalasalamov.bankloanapplication.tokenGeneration",
                "com.gulbalasalamov.bankloanapplication.repository",
                "com.gulbalasalamov.bankloanapplication.service",
                "com.gulbalasalamov.bankloanapplication.config",
                "com.gulbalasalamov.bankloanapplication.web.controllers",
                "com.gulbalasalamov.bankloanapplication.core",
                "com.gulbalasalamov.bankloanapplication.exception",
                "com.gulbalasalamov.bankloanapplication.qa",
                "com.gulbalasalamov.bankloanapplication.validation",
                "com.gulbalasalamov.bankloanapplication.mail",
                "com.gulbalasalamov.bankloanapplication.listener",
                "com.gulbalasalamov.bankloanapplication.model"
        }
)
public class BankLoanApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankLoanApplication.class, args);
    }

   /* @Bean
    public  ResourceBundleMessageSource messageSource() {
        var source = new ResourceBundleMessageSource();
        source.setBasename("message");
        source.setDefaultEncoding("UTF-8");
        source.setUseCodeAsDefaultMessage(true);
        return source;
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
        sessionLocaleResolver.setDefaultLocale(Locale.ENGLISH);
        return sessionLocaleResolver;
    }*/

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
        mapper.getConfiguration().setSkipNullEnabled(true);
        return mapper;
    }

    @Bean
    JavaMailSenderImpl mailSender() {
        return new JavaMailSenderImpl();
    }

    @Bean
    public Locale locale() {
        return Locale.ENGLISH;
    }
}
