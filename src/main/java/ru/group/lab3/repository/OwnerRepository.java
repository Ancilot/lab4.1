package ru.group.lab3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.group.lab3.entity.Owner;
import org.springframework.data.repository.CrudRepository;

public interface OwnerRepository
        extends JpaRepository<Owner, Long> {
}
