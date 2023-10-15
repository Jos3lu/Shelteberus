package com.hiberus.models;

import com.hiberus.exceptions.VolunteerNotValidException;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.regex.Pattern;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "volunteers")
@Entity
public class Volunteer {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Setter
    @Column(name = "name")
    private String name;

    @Setter
    @Column(name = "phone")
    private String phone;

    @Setter
    @Column(name = "dogs")
    @ElementCollection
    private List<Long> dogs;

    public void validVolunteer() throws VolunteerNotValidException {
        if (name == null || phone == null)
            throw new VolunteerNotValidException();
        // Example: +34 274 38 92 14
        Pattern validPhone = Pattern.compile("^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$");
        if (name.isBlank() || phone.isBlank() || !validPhone.matcher(phone).find())
            throw new VolunteerNotValidException();
    }

}
