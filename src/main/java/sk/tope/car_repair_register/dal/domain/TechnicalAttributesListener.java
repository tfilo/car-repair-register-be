package sk.tope.car_repair_register.dal.domain;

import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sk.tope.car_repair_register.component.TokenHandler;

import java.time.LocalDateTime;

@Component
public class TechnicalAttributesListener {

    private static TokenHandler tokenHandler;

    private void checkOwner(TechnicalAttributes ta) {
        if (!ta.getCreator().equals(tokenHandler.getSubject())) {
            throw new RuntimeException("User does not have permission to read or modify this object");
        }
    }

    @Autowired
    public void setTokenHandler(TokenHandler tokenHandler) {
        this.tokenHandler = tokenHandler;
    }

    @PrePersist
    private void setCreatorAndCreated(TechnicalAttributes ta) {
        ta.setCreated(LocalDateTime.now());
        ta.setCreator(tokenHandler.getSubject());
    }

    @PreRemove
    @PostLoad
    private void checkCreator(TechnicalAttributes ta) {
        checkOwner(ta);
    }

    @PreUpdate
    private void setModifier(TechnicalAttributes ta) {
        checkOwner(ta);
        ta.setModified(LocalDateTime.now());
    }
}
