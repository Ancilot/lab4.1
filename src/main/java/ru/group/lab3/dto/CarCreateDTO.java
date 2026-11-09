package ru.group.lab3.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CarCreateDTO {

    @NotBlank(message = "Введите марку автомобиля")
    @Size(min = 2, max = 50)
    private String brand;

    @NotBlank(message = "Введите номер автомобиля")
    @Size(min = 5, max = 12)
    private String plateNumber;

    @NotBlank(message = "Введите номер техпаспорта")
    @Size(min = 5, max = 30)
    private String passportNumber;

    @NotNull(message = "Выберите владельца")
    private Long ownerId;

    public CarCreateDTO() {
    }


    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
}