package model;

import com.hiberus.exceptions.UserNotValidException;
import com.hiberus.models.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class UsersTest {

    private static final Long ID = 1L;
    private static final String NAME = "John";
    private static final String PHONE = "+34 374 38 47 35";

    @Test
    public void nameShouldNotBeNull() {
        // When
        User user = new User(ID, null, PHONE);

        // Then
        assertThrows(UserNotValidException.class, user::validUser);
    }

    @Test
    public void nameShouldNotBeEmpty() {
        // When
        User user = new User(ID, "", PHONE);

        // Then
        assertThrows(UserNotValidException.class, user::validUser);
    }

    @Test
    public void phoneShouldNotBeNull() {
        // When
        User user = new User(ID, NAME, null);

        // Then
        assertThrows(UserNotValidException.class, user::validUser);
    }

    @Test
    public void phoneShouldNotBeEmpty() {
        // When
        User user = new User(ID, NAME, "");

        // Then
        assertThrows(UserNotValidException.class, user::validUser);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            // Given
            "3838 3838 3939", "22 383 383 394", "2837", "1238492724889893", "233 489 389"
    })
    public void phoneShouldFollowPattern(String phone) {
        // When
        User user = new User(ID, NAME, phone);

        // Then
        assertThrows(UserNotValidException.class, user::validUser);
    }

}
