package ru.group.lab3.repository;

import ru.group.lab3.entity.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository
        extends CrudRepository<Address, Long> {
}
