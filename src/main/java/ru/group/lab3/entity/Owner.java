package ru.group.lab3.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "owners")
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Введите фамилию")
    @Size(min = 2, max = 50)
    private String surname;

    @NotBlank(message = "Введите имя")
    @Size(min = 2, max = 50)
    private String name;

    @NotBlank(message = "Введите отчество")
    @Size(min = 2, max = 50)
    private String patronymic;

    @NotBlank(message = "Введите номер телефона")
    @Pattern(
            regexp = "^\\+?[0-9()\\-\\s]{7,20}$",
            message = "Некорректный номер телефона"
    )
    private String phone;

    // Один владелец — один адрес
    @OneToOne(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    // Один владелец — несколько автомобилей
    @OneToMany(
            mappedBy = "owner",
            cascade = CascadeType.ALL
    )
    @JsonBackReference("owner-cars")
    private List<Car> cars = new ArrayList<>();

    public Owner() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    // Добавление автомобиля владельцу
    public void addCar(Car car) {
        cars.add(car);
        car.setOwner(this);
    }

    // Удаление автомобиля у владельца
    public void removeCar(Car car) {
        cars.remove(car);
    }
}