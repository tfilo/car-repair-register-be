package sk.tope.car_repair_register.dal.domain;

import org.hibernate.annotations.JdbcTypeCode;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.type.SqlTypes;

import java.util.Objects;


@Table(name = "file")
@Entity
public class File {
    @Id
    private Long id;

    @NotNull
    @Lob
    @JdbcTypeCode(SqlTypes.BINARY)
    @Column(name = "data")
    private byte[] data;

    @OneToOne
    @JoinColumn(name = "id")
    @MapsId
    private Attachment attachment;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        File file = (File) o;
        return Objects.equals(id, file.id);
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

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    @Override
    public String toString() {
        return "File{" +
                "id=" + id +
                '}';
    }
}
