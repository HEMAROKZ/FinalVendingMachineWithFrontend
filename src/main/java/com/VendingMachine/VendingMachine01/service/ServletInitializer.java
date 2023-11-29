package com.VendingMachine.VendingMachine01.service;

import com.VendingMachine.VendingMachine01.VendingMachine01Application;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(VendingMachine01Application.class);
    }

}
