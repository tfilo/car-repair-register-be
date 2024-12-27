package sk.tope.car_repair_register.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import sk.tope.car_repair_register.component.CustomPageableResolver;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final CustomPageableResolver customPageableResolver;

    public WebConfig(CustomPageableResolver customPageableResolver) {
        this.customPageableResolver = customPageableResolver;
    }

    @Override
    public void addArgumentResolvers(
            List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(customPageableResolver);
    }
}

