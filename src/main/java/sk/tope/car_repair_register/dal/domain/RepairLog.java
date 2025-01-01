package sk.tope.car_repair_register.dal.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "repair_log",
        uniqueConstraints = {
                @UniqueConstraint(name = "uc_repair_log_repair", columnNames = {"repair_date", "vehicle_id", "deleted_at"})
        }
)
@SQLDelete(sql = "UPDATE repair_log SET deleted_at = current_timestamp WHERE id=?")
@SQLRestriction("deleted_at IS NULL")
@SequenceGenerator(name = "repair_log_generator", allocationSize = 1, sequenceName = "sq_repair_log")
public class RepairLog extends TechnicalAttributes {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "repair_log_generator")
    private Long id;

    @NotNull
    @Column(name = "content", columnDefinition = "VARCHAR(5000) collate \"sk-SK-x-icu\"")
    private String content;

    @NotNull
    @Column(name = "repair_date")
    private LocalDate repairDate;

    @Column(name = "odometer")
    private Integer odometer;

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

    public LocalDate getRepairDate() {
        return repairDate;
    }

    public void setRepairDate(LocalDate repairDate) {
        this.repairDate = repairDate;
    }

    public Integer getOdometer() {
        return odometer;
    }

    public void setOdometer(Integer odometer) {
        this.odometer = odometer;
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

    @Override
    public String toString() {
        return "RepairLog{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", repairDate=" + repairDate +
                ", odometer=" + odometer +
                ", vehicle=" + vehicle +
                ", created=" + super.getCreated() +
                ", modified=" + super.getModified() +
                ", deleted=" + super.getDeleted() +
                ", creator=" + super.getCreator() +
                '}';
    }
}
