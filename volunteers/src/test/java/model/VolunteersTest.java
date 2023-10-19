package model;

import com.hiberus.exceptions.VolunteerNotValidException;
import com.hiberus.models.Volunteer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class VolunteersTest {

    private static final Long ID = 1L;
    private static final String NAME = "Sam";
    private static final String PHONE = "+48 754 48 59 36";
    private static final List<Long> DOGS = List.of(2L, 3L, 4L, 5L);

    @Test
    public void nameShouldNotBeNull() {
        // When
        Volunteer volunteer = new Volunteer(ID, null, PHONE, DOGS);

        // Then
        assertThrows(VolunteerNotValidException.class, volunteer::validVolunteer);
    }

    @Test
    public void nameShouldNotBeEmpty() {
        // When
        Volunteer volunteer = new Volunteer(ID, "", PHONE, DOGS);

        // Then
        assertThrows(VolunteerNotValidException.class, volunteer::validVolunteer);
    }

    @Test
    public void phoneShouldNotBeNull() {
        // When
        Volunteer volunteer = new Volunteer(ID, NAME, null, DOGS);

        // Then
        assertThrows(VolunteerNotValidException.class, volunteer::validVolunteer);
    }

    @Test
    public void phoneShouldNotBeEmpty() {
        // When
        Volunteer volunteer = new Volunteer(ID, NAME, "", DOGS);

        // Then
        assertThrows(VolunteerNotValidException.class, volunteer::validVolunteer);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            // Given
            "3838 3838 3939", "22 383 383 394", "2837", "1238492724889893", "233 489 389"
    })
    public void phoneShouldFollowPattern(String phone) {
        // When
        Volunteer volunteer = new Volunteer(ID, NAME, phone, DOGS);

        // Then
        assertThrows(VolunteerNotValidException.class, volunteer::validVolunteer);
    }

}
