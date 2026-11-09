package ru.group.lab3.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class OwnerCreateDTO {

    @NotBlank(message = "Введите фамилию")
    @Size(min = 2, max = 50)
    private String surname;

    @NotBlank(message = "Введите имя")
    @Size(min = 2, max = 50)
    private String name;

    @NotBlank(message = "Введите отчество")
    @Size(min = 2, max = 50)
    private String patronymic;

    @NotBlank(message = "Введите телефон")
    @Pattern(
            regexp = "^\\+?[0-9()\\-\\s]{7,20}$",
            message = "Неверный формат телефона"
    )
    private String phone;

    @NotBlank(message = "Введите индекс")
    private String postalCode;

    @NotBlank(message = "Введите страну")
    private String country;

    @NotBlank(message = "Введите область")
    private String region;

    @NotBlank(message = "Введите район")
    private String district;

    @NotBlank(message = "Введите город")
    private String city;

    @NotBlank(message = "Введите улицу")
    private String street;

    @NotBlank(message = "Введите дом")
    private String house;

    private String apartment;


    public OwnerCreateDTO() {
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

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }
}