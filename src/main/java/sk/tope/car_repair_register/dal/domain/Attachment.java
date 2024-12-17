package sk.tope.car_repair_register.dal.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.Objects;

@Entity
@Table(name = "attachment")
@SQLDelete(sql = "UPDATE attachment SET deleted_at = current_timestamp WHERE id=?")
@SQLRestriction("deleted_at IS NULL")
@SequenceGenerator(name = "attachment_generator", allocationSize = 1, sequenceName = "sq_attachment")
public class Attachment extends TechnicalAttributes {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "attachment_generator")
    private Long id;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "mime_type")
    private String mimeType;

    @NotNull
    @OneToOne(mappedBy = "attachment", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private File file;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "repair_log_id", referencedColumnName = "id")
    private RepairLog repairLog;

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

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
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

    @Override
    public String toString() {
        return "Attachment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mimeType='" + mimeType + '\'' +
                ", created=" + super.getCreated() +
                ", modified=" + super.getModified() +
                ", deleted=" + super.getDeleted() +
                ", creator=" + super.getCreator() +
                '}';
    }
}
