package sk.tope.car_repair_register.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.RequestContextFilter;


@EnableWebSecurity
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // http.cors(AbstractHttpConfigurer::disable)
        //        .csrf(AbstractHttpConfigurer::disable)
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(ahr ->
                        ahr.requestMatchers("/v3/**", "/swagger-ui/**").permitAll()
                                .requestMatchers("/actuator/**").permitAll()
                                .anyRequest().authenticated()
                );
        http.addFilterAfter(new RequestContextFilter(), CsrfFilter.class);

        http.oauth2ResourceServer((oauth2) -> oauth2
                .jwt(Customizer.withDefaults())
        );

        return http.build();
    }
}

