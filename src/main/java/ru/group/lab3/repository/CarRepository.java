package ru.group.lab3.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.group.lab3.entity.Car;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarRepository
        extends JpaRepository<Car, Long> {

    @Query("""
        SELECT c FROM Car c
        JOIN c.owner o
        JOIN o.address a
        WHERE
        (:brand IS NULL OR
            LOWER(c.brand) LIKE LOWER(CONCAT('%', :brand, '%')))
        AND
        (:plate IS NULL OR
            LOWER(c.plateNumber) LIKE LOWER(CONCAT('%', :plate, '%')))
        AND
        (:surname IS NULL OR
            LOWER(o.surname) LIKE LOWER(CONCAT('%', :surname, '%')))
        AND
        (:city IS NULL OR
            LOWER(a.city) LIKE LOWER(CONCAT('%', :city, '%')))
    """)
    List<Car> searchCars(
            @Param("brand") String brand,
            @Param("plate") String plate,
            @Param("surname") String surname,
            @Param("city") String city
    );
}
