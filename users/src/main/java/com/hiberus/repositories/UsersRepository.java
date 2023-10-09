package com.hiberus.repositories;

import com.hiberus.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
    Boolean existsByName(String name);
}
