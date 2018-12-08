package cardealer.domain.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cars")
public class Car extends BaseEntity {

    private String make;
    private String model;
    private Double travelledDistance;
    private List<Part> parts;
    private List<Car> cars;


    public Car() {
    }

    @Column(name = "make")
    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    @Column(name = "model")
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Column(name = "travelled_distance")
    public Double getTravelledDistance() {
        return travelledDistance;
    }

    public void setTravelledDistance(Double travelledDistance) {
        this.travelledDistance = travelledDistance;
    }

    @ManyToMany(targetEntity = Part.class, fetch = FetchType.EAGER)
    @JoinTable(name = "cars_parts",
            joinColumns = @JoinColumn(name = "car_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "part_id", referencedColumnName = "id"))
    public List<Part> getParts() {
        return parts;
    }

    public void setParts(List<Part> parts) {
        this.parts = parts;
    }

    @OneToMany(targetEntity = Sale.class, mappedBy = "car")
    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }
}
