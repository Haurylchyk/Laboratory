package com.epam.esm.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;

import java.util.Locale;

@Configuration
@ComponentScan(basePackages = "com.epam.esm")
@EnableTransactionManagement
public class ControllerConfig implements WebMvcConfigurer {

    private static final String ERROR_MESSAGES_BUNDLE_CLASSPATH = "classpath:local";
    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final String DEFAULT_LOCALE = "en";


    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(ERROR_MESSAGES_BUNDLE_CLASSPATH);
        messageSource.setDefaultEncoding(DEFAULT_ENCODING);
        return messageSource;
    }

    @Bean
    public LocaleResolver getLocaleResolver() {
        FixedLocaleResolver localeResolver = new FixedLocaleResolver();
        localeResolver.setDefaultLocale(new Locale(DEFAULT_LOCALE));
        return localeResolver;
    }

}


