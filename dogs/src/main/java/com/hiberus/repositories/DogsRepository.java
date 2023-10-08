package com.hiberus.repositories;

import com.hiberus.models.Dog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DogsRepository extends JpaRepository<Dog, Long> {
    List<Dog> findByReserveId(Long reserveId);
}
