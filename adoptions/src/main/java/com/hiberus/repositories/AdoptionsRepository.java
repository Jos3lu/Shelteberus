package com.hiberus.repositories;

import com.hiberus.models.Adoption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdoptionsRepository extends JpaRepository<Adoption, Long> {
}
