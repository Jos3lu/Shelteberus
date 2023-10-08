package com.hiberus.repositories;

import com.hiberus.models.Dog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DogsRepository extends JpaRepository<Dog, Long> {
}
