package com.hiberus.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "adoptions")
@Entity
public class Adoption {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Setter
    @Column(name = "user_id")
    private Long userId;

    @Setter
    @Column(name = "dog_id")
    private Long dogId;

    @Setter
    @Column(name = "adoption_day")
    private LocalDate adoptionDay;

}
