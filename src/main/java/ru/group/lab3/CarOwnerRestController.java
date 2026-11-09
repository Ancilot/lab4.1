
package ru.group.lab3;

import ru.group.lab3.dto.OwnerCreateDTO;
import ru.group.lab3.dto.CarCreateDTO;
import ru.group.lab3.entity.Owner;
import ru.group.lab3.entity.Car;
import ru.group.lab3.entity.Address;
import ru.group.lab3.repository.OwnerRepository;
import ru.group.lab3.repository.CarRepository;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api")
public class CarOwnerRestController {

    private final OwnerRepository ownerRepository;
    private final CarRepository carRepository;

    public CarOwnerRestController(
            OwnerRepository ownerRepository,
            CarRepository carRepository) {

        this.ownerRepository = ownerRepository;
        this.carRepository = carRepository;
    }


    // Получить всех владельцев
    @GetMapping("/owners")
    public List<Owner> getOwners() {
        return ownerRepository.findAll();
    }

    // Получить владельца по ID
    @GetMapping("/owners/{id}")
    public Owner getOwner(@PathVariable Long id) {
        return findOwner(id);
    }

    // Добавить владельца
    @PostMapping("/owners")
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public Owner createOwner(
            @Valid @RequestBody OwnerCreateDTO dto) {

        Owner owner = new Owner();

        owner.setSurname(dto.getSurname());
        owner.setName(dto.getName());
        owner.setPatronymic(dto.getPatronymic());
        owner.setPhone(dto.getPhone());

        Address address = createAddress(dto);
        owner.setAddress(address);

        return ownerRepository.save(owner);
    }

    // Изменить владельца
    @PutMapping("/owners/{id}")
    @Transactional
    public Owner updateOwner(
            @PathVariable Long id,
            @Valid @RequestBody OwnerCreateDTO dto) {

        Owner owner = findOwner(id);

        owner.setSurname(dto.getSurname());
        owner.setName(dto.getName());
        owner.setPatronymic(dto.getPatronymic());
        owner.setPhone(dto.getPhone());

        Address address = owner.getAddress();

        address.setPostalCode(dto.getPostalCode());
        address.setCountry(dto.getCountry());
        address.setRegion(dto.getRegion());
        address.setDistrict(dto.getDistrict());
        address.setCity(dto.getCity());
        address.setStreet(dto.getStreet());
        address.setHouse(dto.getHouse());
        address.setApartment(dto.getApartment());

        return ownerRepository.save(owner);
    }

    // Удалить владельца
    @DeleteMapping("/owners/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void deleteOwner(@PathVariable Long id) {

        Owner owner = findOwner(id);
        ownerRepository.delete(owner);
    }


    // Получить все автомобили
    @GetMapping("/cars")
    public List<Car> getCars() {
        return carRepository.findAll();
    }

    // Получить автомобиль по ID
    @GetMapping("/cars/{id}")
    public Car getCar(@PathVariable Long id) {
        return findCar(id);
    }

    // Добавить автомобиль
    @PostMapping("/cars")
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public Car createCar(
            @Valid @RequestBody CarCreateDTO dto) {

        Owner owner = findOwner(dto.getOwnerId());

        Car car = new Car();

        car.setBrand(dto.getBrand());
        car.setPlateNumber(dto.getPlateNumber());
        car.setPassportNumber(dto.getPassportNumber());

        owner.addCar(car);

        return carRepository.save(car);
    }

    // Изменить автомобиль
    @PutMapping("/cars/{id}")
    @Transactional
    public Car updateCar(
            @PathVariable Long id,
            @Valid @RequestBody CarCreateDTO dto) {

        Car car = findCar(id);
        Owner newOwner = findOwner(dto.getOwnerId());

        car.setBrand(dto.getBrand());
        car.setPlateNumber(dto.getPlateNumber());
        car.setPassportNumber(dto.getPassportNumber());

        if (!car.getOwner().getId().equals(newOwner.getId())) {

            Owner oldOwner = car.getOwner();

            oldOwner.removeCar(car);
            newOwner.addCar(car);
        }

        return carRepository.save(car);
    }

    // Удалить автомобиль
    @DeleteMapping("/cars/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void deleteCar(@PathVariable Long id) {

        Car car = findCar(id);

        Owner owner = car.getOwner();
        owner.removeCar(car);

        carRepository.delete(car);
    }



    // Поиск автомобилей по нескольким критериям
    @GetMapping("/cars/search")
    public List<Car> searchCars(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String plateNumber,
            @RequestParam(required = false) String surname,
            @RequestParam(required = false) String city) {

        return carRepository.searchCars(
                emptyToNull(brand),
                emptyToNull(plateNumber),
                emptyToNull(surname),
                emptyToNull(city)
        );
    }

    // Передать автомобиль другому владельцу
    @PutMapping("/cars/{carId}/owner/{ownerId}")
    @Transactional
    public Car transferCar(
            @PathVariable Long carId,
            @PathVariable Long ownerId) {

        Car car = findCar(carId);
        Owner newOwner = findOwner(ownerId);

        Owner oldOwner = car.getOwner();

        if (oldOwner.getId().equals(newOwner.getId())) {
            return car;
        }

        oldOwner.removeCar(car);
        newOwner.addCar(car);

        return carRepository.save(car);
    }

    // Статистика
    @GetMapping("/statistics")
    public Map<String, Object> getStatistics() {

        List<Car> cars = carRepository.findAll();

        Map<String, Integer> brands = new HashMap<>();

        for (Car car : cars) {
            brands.merge(car.getBrand(), 1, Integer::sum);
        }

        Map<String, Object> statistics = new HashMap<>();

        statistics.put("ownersCount", ownerRepository.count());
        statistics.put("carsCount", carRepository.count());
        statistics.put("brandsCount", brands.size());
        statistics.put("carsByBrand", brands);

        return statistics;
    }


    // Найти владельца или вернуть ошибку 404
    private Owner findOwner(Long id) {

        return ownerRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Владелец с ID " + id + " не найден"
                        )
                );
    }

    // Найти автомобиль или вернуть ошибку 404
    private Car findCar(Long id) {

        return carRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Автомобиль с ID " + id + " не найден"
                        )
                );
    }

    // Создать адрес из DTO владельца
    private Address createAddress(OwnerCreateDTO dto) {

        Address address = new Address();

        address.setPostalCode(dto.getPostalCode());
        address.setCountry(dto.getCountry());
        address.setRegion(dto.getRegion());
        address.setDistrict(dto.getDistrict());
        address.setCity(dto.getCity());
        address.setStreet(dto.getStreet());
        address.setHouse(dto.getHouse());
        address.setApartment(dto.getApartment());

        return address;
    }

    // Преобразовать пустую строку в null
    private String emptyToNull(String value) {

        if (value == null || value.isBlank()) {
            return null;
        }

        return value.trim();
    }
}
