package com.hiberus.models;

import com.hiberus.enums.Breed;
import com.hiberus.exceptions.DogNotValidException;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

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
    private LocalDate birth;

    @Setter
    @Column(name = "reserve_id")
    private Long reserveId;

    public void validDog() throws DogNotValidException {
        if (name == null || breed == null || birth == null)
            throw new DogNotValidException();
        if (name.isBlank())
            throw new DogNotValidException();
    }

    public boolean dogAlreadyReserved() {
        return reserveId != null;
    }
}
