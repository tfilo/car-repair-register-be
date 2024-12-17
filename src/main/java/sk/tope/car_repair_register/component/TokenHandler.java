package sk.tope.car_repair_register.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class TokenHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenHandler.class);

    public String getSubject() {
        JwtAuthenticationToken token = jwtAuthenticationToken();
        if (token != null) {
            return token.getName();
        }
        throw new RuntimeException("Unauthorized");
    }

    public JwtAuthenticationToken jwtAuthenticationToken() {
        if (SecurityContextHolder.getContext().getAuthentication() instanceof JwtAuthenticationToken) {
            return (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        }
        return null;
    }

}
