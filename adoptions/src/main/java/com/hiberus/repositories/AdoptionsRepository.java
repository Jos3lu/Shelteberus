package com.hiberus.repositories;

import com.hiberus.models.Adoption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdoptionsRepository extends JpaRepository<Adoption, Long> {
    List<Adoption> findByUserId(Long userId);

    Boolean existsByUserIdAndDogId(Long userId, Long dogId);
}
