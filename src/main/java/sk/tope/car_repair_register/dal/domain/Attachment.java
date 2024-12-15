package sk.tope.car_repair_register.dal.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "attachment")
@SequenceGenerator(name = "attachment_generator", allocationSize = 1, sequenceName = "sq_attachment")
public class Attachment {

    @Id
    @Column(name="id")
    @GeneratedValue(generator = "attachment_generator")
    private Long id;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @OneToOne(mappedBy = "attachment", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private File file;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "repair_log_id", referencedColumnName = "id")
    private RepairLog repairLog;

    @NotNull
    @Column(name = "created_at", updatable = false)
    private LocalDateTime created;

    @Column(name = "modified_at")
    private LocalDateTime modified;

    @Column(name = "deleted_at")
    private LocalDateTime deleted;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Attachment that = (Attachment) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public RepairLog getRepairLog() {
        return repairLog;
    }

    public void setRepairLog(RepairLog repairLog) {
        this.repairLog = repairLog;
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
        return "Attachment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", created=" + created +
                ", modified=" + modified +
                ", deleted=" + deleted +
                '}';
    }
}
