package com.project.LIA.config;

// 파일 업로드 관련
// resource 경로설정

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfiguration {

    @Configuration
    public static class LocalMvcConfiguration implements WebMvcConfigurer{
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            System.out.println("\tWebMvcConfigurer.super.addResourceHandlers()호출");
            //  /upload/** URL 로 request 가 들어오면
            // upload/ 경로의 resource 가 동작케 함.
            // IntelliJ 의 경우 이 경로를 module 이 아닌 project 이하에 생성해야 한다.
            registry
                    .addResourceHandler("/upload/**")
                    .addResourceLocations("file:upload")
                    ;

        }
    }
}
