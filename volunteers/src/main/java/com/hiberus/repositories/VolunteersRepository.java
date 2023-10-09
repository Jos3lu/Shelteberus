package com.hiberus.repositories;

import com.hiberus.models.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VolunteersRepository extends JpaRepository<Volunteer, Long> {
    Boolean existsByName(String name);
}
