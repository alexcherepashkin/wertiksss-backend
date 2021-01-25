package ua.alexch.wertiksss.config;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;

import ua.alexch.wertiksss.WertiksssApplication;

@Configuration
public class WebAppInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WertiksssApplication.class);
    }

}
