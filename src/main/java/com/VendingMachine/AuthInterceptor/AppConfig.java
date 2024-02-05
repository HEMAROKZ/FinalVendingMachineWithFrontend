//package com.VendingMachine.VendingMachine01.AuthInterceptor;// AppConfig.java
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class AppConfig implements WebMvcConfigurer {
//
//    @Autowired
//    private AuthInterceptor authInterceptor;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        // Register the AuthInterceptor for all paths
//        registry.addInterceptor(authInterceptor).addPathPatterns("/**").excludePathPatterns("/authenticate","/home","/login");
//    }
//}
