package sk.tope.car_repair_register.dal.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(TechnicalAttributesListener.class)
public abstract class TechnicalAttributes {
    // UUID of user from token (sub)
    @NotNull
    @Column(name = "creator", updatable = false, length = 36)
    private String creator;

    @NotNull
    @Column(name = "created_at", updatable = false)
    private LocalDateTime created;

    @Column(name = "modified_at")
    private LocalDateTime modified;

    @Column(name = "deleted_at")
    private LocalDateTime deleted;

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }

    public LocalDateTime getDeleted() {
        return deleted;
    }

    public void setDeleted(LocalDateTime deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "TechnicalAttributes{" +
                "creator='" + creator + '\'' +
                ", created=" + created +
                ", modified=" + modified +
                ", deleted=" + deleted +
                '}';
    }
}
