package ru.group.lab3;

import ru.group.lab3.dto.OwnerCreateDTO;
import ru.group.lab3.dto.CarCreateDTO;
import ru.group.lab3.dto.SearchDTO;

import ru.group.lab3.entity.Address;
import ru.group.lab3.entity.Owner;
import ru.group.lab3.entity.Car;

import ru.group.lab3.repository.OwnerRepository;
import ru.group.lab3.repository.CarRepository;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    private final OwnerRepository ownerRepository;
    private final CarRepository carRepository;

    public MainController(
            OwnerRepository ownerRepository,
            CarRepository carRepository) {

        this.ownerRepository = ownerRepository;
        this.carRepository = carRepository;
    }


    // Вывод всех владельцев
    @GetMapping("/owners")
    public String showOwners(Model model) {

        List<Owner> owners = new ArrayList<>();

        ownerRepository.findAll().forEach(owners::add);
        model.addAttribute("owners", owners);

        return "owners";
    }

    // Открытие формы создания владельца
    @GetMapping("/owners/new")
    public String newOwner(Model model) {

        model.addAttribute("ownerDto", new OwnerCreateDTO());

        return "owner-form";
    }

    // Сохранение нового владельца
    @PostMapping("/owners/new")
    public String saveOwner(
            @Valid @ModelAttribute("ownerDto")
            OwnerCreateDTO dto,
            BindingResult result) {

        if (result.hasErrors()) {
            return "owner-form";
        }

        Address address = new Address();

        address.setPostalCode(dto.getPostalCode());
        address.setCountry(dto.getCountry());
        address.setRegion(dto.getRegion());
        address.setDistrict(dto.getDistrict());
        address.setCity(dto.getCity());
        address.setStreet(dto.getStreet());
        address.setHouse(dto.getHouse());
        address.setApartment(dto.getApartment());

        Owner owner = new Owner();

        owner.setSurname(dto.getSurname());
        owner.setName(dto.getName());
        owner.setPatronymic(dto.getPatronymic());
        owner.setPhone(dto.getPhone());
        owner.setAddress(address);

        ownerRepository.save(owner);

        return "redirect:/owners";
    }


    // Вывод всех автомобилей
    @GetMapping("/cars")
    public String showCars(Model model) {

        List<Car> cars = new ArrayList<>();

        carRepository.findAll().forEach(cars::add);

        model.addAttribute("cars", cars);
        model.addAttribute("searchDto", new SearchDTO());

        return "cars";
    }

    // Открытие формы добавления автомобиля
    @GetMapping("/cars/new")
    public String newCar(Model model) {

        model.addAttribute("carDto", new CarCreateDTO());
        model.addAttribute("owners", ownerRepository.findAll());

        return "car-form";
    }

    // Сохранение автомобиля
    @PostMapping("/cars/new")
    public String saveCar(
            @Valid @ModelAttribute("carDto")
            CarCreateDTO dto,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("owners", ownerRepository.findAll());
            return "car-form";
        }

        Owner owner = ownerRepository.findById(dto.getOwnerId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Владелец не найден")
                );

        Car car = new Car();

        car.setBrand(dto.getBrand());
        car.setPlateNumber(dto.getPlateNumber());
        car.setPassportNumber(dto.getPassportNumber());

        owner.addCar(car);

        carRepository.save(car);

        return "redirect:/cars";
    }

    // Поиск автомобилей по нескольким критериям
    @GetMapping("/cars/search")
    public String searchCars(
            @ModelAttribute SearchDTO searchDto,
            Model model) {

        List<Car> cars = carRepository.searchCars(
                emptyToNull(searchDto.getBrand()),
                emptyToNull(searchDto.getPlateNumber()),
                emptyToNull(searchDto.getSurname()),
                emptyToNull(searchDto.getCity())
        );

        model.addAttribute("cars", cars);
        model.addAttribute("searchDto", searchDto);

        return "cars";
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/owners";
    }

    // Преобразование пустой строки в null
    private String emptyToNull(String value) {

        if (value == null || value.isBlank()) {
            return null;
        }

        return value.trim();
    }
}