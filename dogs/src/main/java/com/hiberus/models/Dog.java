package com.hiberus.models;

import com.hiberus.enums.Breed;
import com.hiberus.exceptions.DogNotValidException;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "dogs")
@Entity
public class Dog {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Setter
    @Column(name = "name")
    private String name;

    @Setter
    @Column(name = "breed")
    private Breed breed;

    @Setter
    @Column(name = "birth")
    private Date birth;

    @Setter
    @Column(name = "reserve_dni")
    private String reserveDNI;

    void validDog() throws DogNotValidException {
        if (name.isBlank() || breed == null || birth == null || reserveDNI == null) {
            throw new DogNotValidException();
        }
    }
}
