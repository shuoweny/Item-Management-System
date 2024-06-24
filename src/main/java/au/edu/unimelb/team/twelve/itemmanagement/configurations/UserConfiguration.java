package au.edu.unimelb.team.twelve.itemmanagement.configurations;

import au.edu.unimelb.team.twelve.itemmanagement.controllers.UserController;
import au.edu.unimelb.team.twelve.itemmanagement.repositories.UserRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class UserConfiguration implements WebMvcConfigurer {

    private final UserRepository users;

    public UserConfiguration(UserRepository users) {
        this.users = users;
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix("/user", c -> c == UserController.class);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TokenInjector(users));
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new TokenReceiver());
    }
}
