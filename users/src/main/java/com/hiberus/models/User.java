package com.hiberus.models;

import com.hiberus.exceptions.UserNotValidException;
import lombok.*;

import javax.persistence.*;
import java.util.regex.Pattern;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
@Entity
public class User {

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

    public void validUser() throws UserNotValidException {
        if (name == null || phone == null)
            throw new UserNotValidException();
        // Example: +34 274 38 92 14
        Pattern validPhone = Pattern.compile("^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$");
        if (name.isBlank() || phone.isBlank() || !validPhone.matcher(phone).find())
            throw new UserNotValidException();
    }
}
