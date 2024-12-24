package sk.tope.car_repair_register.dal.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.Objects;

@Entity
@Table(name = "customer")
@SQLDelete(sql = "UPDATE customer SET deleted_at = current_timestamp WHERE id=?")
@SQLRestriction("deleted_at IS NULL")
@SequenceGenerator(name = "customer_generator", allocationSize = 1, sequenceName = "sq_customer")
public class Customer extends TechnicalAttributes {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "customer_generator")
    private Long id;

    @NotNull
    @Column(name = "name", columnDefinition = "VARCHAR(64) collate \"sk-SK-x-icu\"")
    private String name;

    @Column(name = "surname", columnDefinition = "VARCHAR(64) collate \"sk-SK-x-icu\"")
    private String surname;

    @Column(name = "mobile", length = 20)
    private String mobile;

    @Column(name = "email", length = 320)
    private String email;

/*    @OneToMany(
            mappedBy = "customer",
            fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST,
            orphanRemoval = true
    )
    @OrderBy("registrationPlate ASC")
    private List<Vehicle> vehicles;*/

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id);
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

/*    public List<Vehicle> getVehicles() {
        if (Objects.isNull(vehicles)) {
            vehicles = new ArrayList<>();
        }
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }*/

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", created=" + super.getCreated() +
                ", modified=" + super.getModified() +
                ", deleted=" + super.getDeleted() +
                ", creator=" + super.getCreator() +
                '}';
    }
}
