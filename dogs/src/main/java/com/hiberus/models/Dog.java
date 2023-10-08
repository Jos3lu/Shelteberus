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
    @Column(name = "reserved")
    private Boolean reserved;

    void validDog() throws DogNotValidException {
        if (name.isBlank() || breed == null || birth == null || reserved == null) {
            throw new DogNotValidException();
        }
    }
}
