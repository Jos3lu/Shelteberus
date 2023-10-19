package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hiberus.VolunteersApplication;
import com.hiberus.dtos.VolunteerRequestDto;
import com.hiberus.models.Volunteer;
import com.hiberus.services.DogsService;
import com.hiberus.services.impl.VolunteersServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = VolunteersApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class VolunteersController {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private DogsService dogsService;

    @MockBean
    private VolunteersServiceImpl volunteersService;

    @Test
    public void getVolunteersShouldReturn200() throws Exception {
        // Given
        Volunteer volunteer = new Volunteer(1L, "Sam", "+36 474 95 39 27", List.of(2L, 3L, 4L, 5L));
        List<Volunteer> volunteers = List.of(volunteer);

        // When
        when(volunteersService.getVolunteers()).thenReturn(volunteers);

        // Then
        MvcResult mvcResult = mockMvc.perform(get("/api/volunteers"))
                .andExpect(status().isOk()).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        String expectedResponseBody = objectMapper.writeValueAsString(volunteers);
        assertThat(expectedResponseBody).isEqualToIgnoringWhitespace(actualResponseBody);
    }

    @Test
    public void getVolunteerShouldReturn200() throws Exception {
        // Given
        Volunteer volunteer = new Volunteer(1L, "Sam", "+36 474 95 39 27", Collections.emptyList());
        Long volunteerId = 1L;

        // When
        when(volunteersService.getVolunteer(anyLong())).thenReturn(volunteer);
        when(dogsService.getVolunteerDogs(anyList())).thenReturn(Collections.emptyList());

        // Then
        MvcResult mvcResult = mockMvc.perform(get("/api/volunteers/{volunteerId}", volunteerId))
                .andExpect(status().isOk()).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        String expectedResponseBody = objectMapper.writeValueAsString(volunteer);
        assertThat(expectedResponseBody).isEqualToIgnoringWhitespace(actualResponseBody);
    }

    @Test
    public void createVolunteerShouldReturn201() throws Exception {
        // Given
        Volunteer volunteer = new Volunteer(1L, "Sam", "+36 474 95 39 27", Collections.emptyList());
        Long volunteerId = 1L;

        // When
        when(volunteersService.createVolunteer(any(Volunteer.class))).thenReturn(volunteer);

        // Then
        MvcResult mvcResult = mockMvc.perform(post("/api/volunteers/")
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(volunteer)))
                .andExpect(status().isCreated()).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        String expectedResponseBody = objectMapper.writeValueAsString(volunteer);
        assertThat(expectedResponseBody).isEqualToIgnoringWhitespace(actualResponseBody);
    }

    @Test
    public void updateVolunteerShouldReturn200() throws Exception {
        // Given
        VolunteerRequestDto volunteerRequestDto = new VolunteerRequestDto("Sam", "+36 474 95 39 27");
        Volunteer volunteer = new Volunteer(1L, "Sam", "+36 474 95 39 27", Collections.emptyList());
        Long volunteerId = 1L;

        // When
        when(volunteersService.updateVolunteer(anyLong(), any(Volunteer.class))).thenReturn(volunteer);

        // Then
        MvcResult mvcResult = mockMvc.perform(put("/api/volunteers/{volunteerId}", volunteerId)
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(volunteerRequestDto)))
                .andExpect(status().isOk()).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        String expectedResponseBody = objectMapper.writeValueAsString(volunteer);
        assertThat(expectedResponseBody).isEqualToIgnoringWhitespace(actualResponseBody);
    }

    @Test
    public void deleteVolunteerShouldReturn204() throws Exception {
        // Given
        Long volunteerId = 1L;

        // When
        doNothing().when(volunteersService).deleteVolunteer(anyLong());

        // Then
        mockMvc.perform(delete("/api/volunteers/{volunteerId}", volunteerId))
                .andExpect(status().isNoContent());
    }

    @Test
    public void addDogToVolunteerShouldReturn200() throws Exception {
        // Given
        Volunteer volunteer = new Volunteer(1L, "Sam", "+36 474 95 39 27", List.of(2L));
        Long volunteerId = 1L;
        Long dogId = 2L;

        // When
        when(volunteersService.addDogToVolunteer(anyLong(), anyLong())).thenReturn(volunteer);

        // Then
        MvcResult mvcResult = mockMvc.perform(put("/api/volunteers/add-dog")
                        .param("volunteerId", String.valueOf(volunteerId))
                        .param("dogId", String.valueOf(dogId)))
                .andExpect(status().isOk()).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        String expectedResponseBody = objectMapper.writeValueAsString(volunteer);
        assertThat(expectedResponseBody).isEqualToIgnoringWhitespace(actualResponseBody);
    }

    @Test
    public void deleteDogFromVolunteerShouldReturn200() throws Exception {
        // Given
        Volunteer volunteer = new Volunteer(1L, "Sam", "+36 474 95 39 27", Collections.emptyList());
        Long volunteerId = 1L;
        Long dogId = 2L;

        // When
        when(volunteersService.deleteDogFromVolunteer(anyLong(), anyLong())).thenReturn(volunteer);

        // Then
        MvcResult mvcResult = mockMvc.perform(put("/api/volunteers/delete-dog")
                        .param("volunteerId", String.valueOf(volunteerId))
                        .param("dogId", String.valueOf(dogId)))
                .andExpect(status().isOk()).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        String expectedResponseBody = objectMapper.writeValueAsString(volunteer);
        assertThat(expectedResponseBody).isEqualToIgnoringWhitespace(actualResponseBody);
    }

}
