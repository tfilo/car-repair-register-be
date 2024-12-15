package sk.tope.car_repair_register.dal.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "repair_log",
        uniqueConstraints = {
                @UniqueConstraint(name = "uc_repair_log_repair", columnNames = {"repair_date", "vehicle_id", "deleted_at"})
        }
)
@SequenceGenerator(name = "repair_log_generator", allocationSize = 1, sequenceName = "sq_repair_log")
public class RepairLog {

    @Id
    @Column(name="id")
    @GeneratedValue(generator = "repair_log_generator")
    private Long id;

    @NotNull
    @Lob
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    @Column(name = "content", columnDefinition = "TEXT collate \"sk-SK-x-icu\"")
    private String content;

    @NotNull
    @Column(name = "repair_date")
    private LocalDateTime repairDate;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id")
    private Vehicle vehicle;

    @OneToMany(
            mappedBy = "repairLog",
            fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST,
            orphanRemoval = true
    )
    @OrderBy("name ASC")
    private List<Attachment> attachments;

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
        RepairLog repairLog = (RepairLog) o;
        return Objects.equals(id, repairLog.id);
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getRepairDate() {
        return repairDate;
    }

    public void setRepairDate(LocalDateTime repairDate) {
        this.repairDate = repairDate;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public List<Attachment> getAttachments() {
        if (Objects.isNull(attachments)) {
            attachments = new ArrayList<>();
        }
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
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
        return "RepairLog{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", repairDate=" + repairDate +
                ", vehicle=" + vehicle +
                ", created=" + created +
                ", modified=" + modified +
                ", deleted=" + deleted +
                '}';
    }
}
