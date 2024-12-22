package sk.tope.car_repair_register.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.server.ResponseStatusException;
import sk.tope.car_repair_register.bundle.ErrorBundle;

@Component
@RequestScope
public class TokenHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenHandler.class);

    public String getSubject() {
        JwtAuthenticationToken token = jwtAuthenticationToken();
        if (token != null) {
            LOGGER.debug("TokenHandler - getSubject()={}", token.getName());
            return token.getName();
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ErrorBundle.UNAUTHORIZED.name());
    }

    public JwtAuthenticationToken jwtAuthenticationToken() {
        if (SecurityContextHolder.getContext().getAuthentication() instanceof JwtAuthenticationToken) {
            return (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        }
        return null;
    }

}
