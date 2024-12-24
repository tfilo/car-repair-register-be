package sk.tope.car_repair_register.dal.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.Objects;


@Entity
@Table(name = "vehicle",
        uniqueConstraints = {
                @UniqueConstraint(name = "uc_vehicle_registration_plate", columnNames = {"customer_id", "registration_plate", "deleted_at"})
        }
)
@SQLDelete(sql = "UPDATE vehicle SET deleted_at = current_timestamp WHERE id=?")
@SQLRestriction("deleted_at IS NULL")
@SequenceGenerator(name = "vehicle_generator", allocationSize = 1, sequenceName = "sq_vehicle")
public class Vehicle extends TechnicalAttributes {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "vehicle_generator")
    private Long id;

    @NotNull
    @Column(name = "registration_plate", length = 20, unique = true)
    private String registrationPlate;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @Column(name = "vin", length = 20)
    private String vin;

    @Column(name = "engine_code", length = 20)
    private String engineCode;

    @Column(name = "fuel_type", length = 20)
    private String fuelType;

    @Column(name = "engine_power")
    private Integer enginePower;

    @Column(name = "engine_volume")
    private Integer engineVolume;

    @Column(name = "battery_capacity")
    private Integer batteryCapacity;

    @Column(name = "brand", columnDefinition = "VARCHAR(64) collate \"sk-SK-x-icu\"")
    private String brand;

    @Column(name = "model", columnDefinition = "VARCHAR(64) collate \"sk-SK-x-icu\"")
    private String model;

    @Column(name = "year_of_manufacture")
    private Integer yearOfManufacture;

/*    @OneToMany(
            mappedBy = "vehicle",
            fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST,
            orphanRemoval = true
    )
    @OrderBy("repairDate DESC")
    private List<RepairLog> repairs;*/

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(id, vehicle.id);
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

    public String getRegistrationPlate() {
        return registrationPlate;
    }

    public void setRegistrationPlate(String registrationPlate) {
        this.registrationPlate = registrationPlate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getEngineCode() {
        return engineCode;
    }

    public void setEngineCode(String engineCode) {
        this.engineCode = engineCode;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public Integer getEnginePower() {
        return enginePower;
    }

    public void setEnginePower(Integer enginePower) {
        this.enginePower = enginePower;
    }

    public Integer getEngineVolume() {
        return engineVolume;
    }

    public void setEngineVolume(Integer engineVolume) {
        this.engineVolume = engineVolume;
    }

    public Integer getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(Integer batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getYearOfManufacture() {
        return yearOfManufacture;
    }

    public void setYearOfManufacture(Integer yearOfManufacture) {
        this.yearOfManufacture = yearOfManufacture;
    }

/*    public List<RepairLog> getRepairs() {
        if (Objects.isNull(repairs)) {
            repairs = new ArrayList<>();
        }
        return repairs;
    }

    public void setRepairs(List<RepairLog> repairs) {
        this.repairs = repairs;
    }*/

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", customer=" + customer +
                ", fuelType='" + fuelType + '\'' +
                ", enginePower=" + enginePower +
                ", engineVolume=" + engineVolume +
                ", batteryCapacity=" + batteryCapacity +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", yearOfManufacture=" + yearOfManufacture +
                ", engineCode='" + engineCode + '\'' +
                ", created=" + super.getCreated() +
                ", modified=" + super.getModified() +
                ", deleted=" + super.getDeleted() +
                ", creator=" + super.getCreator() +
                '}';
    }
}
