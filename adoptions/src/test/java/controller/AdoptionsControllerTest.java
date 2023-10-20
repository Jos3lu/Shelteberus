package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hiberus.AdoptionsApplication;
import com.hiberus.models.Adoption;
import com.hiberus.services.impl.AdoptionsServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = AdoptionsApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AdoptionsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AdoptionsServiceImpl adoptionsService;

    @Test
    public void getDogsShouldReturn200() throws Exception {
        // Given
        Adoption adoption = new Adoption(1L, 2L, 3L, LocalDate.now());
        List<Adoption> adoptions = List.of(adoption);

        // When
        when(adoptionsService.getAdoptions()).thenReturn(adoptions);

        // Then
        MvcResult mvcResult = mockMvc.perform(get("/api/adoptions"))
                .andExpect(status().isOk()).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        String expectedResponseBody = objectMapper.writeValueAsString(adoptions);
        assertThat(expectedResponseBody).isEqualToIgnoringWhitespace(actualResponseBody);
    }

    @Test
    public void getAdoptionsByUserShouldReturn200() throws Exception {
        // Given
        Adoption adoption = new Adoption(1L, 2L, 3L, LocalDate.now());
        List<Adoption> adoptions = List.of(adoption);
        Long userId = 2L;

        // When
        when(adoptionsService.getAdoptionsByUser(anyLong())).thenReturn(adoptions);

        // Then
        MvcResult mvcResult = mockMvc.perform(get("/api/adoptions/user/{userId}", userId))
                .andExpect(status().isOk()).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        String expectedResponseBody = objectMapper.writeValueAsString(adoptions);
        assertThat(expectedResponseBody).isEqualToIgnoringWhitespace(actualResponseBody);
    }

    @Test
    public void getDogShouldReturn200() throws Exception {
        // Given
        Adoption adoption = new Adoption(1L, 2L, 3L, LocalDate.now());
        Long adoptionId = 1L;

        // When
        when(adoptionsService.getAdoption(anyLong())).thenReturn(adoption);

        // Then
        MvcResult mvcResult = mockMvc.perform(get("/api/adoptions/{adoptionId}", adoptionId))
                .andExpect(status().isOk()).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        String expectedResponseBody = objectMapper.writeValueAsString(adoption);
        assertThat(expectedResponseBody).isEqualToIgnoringWhitespace(actualResponseBody);
    }

    @Test
    public void createAdoptionShouldReturn201() throws Exception {
        // Given
        Adoption adoption = new Adoption(1L, 2L, 3L, LocalDate.now());

        // When
        when(adoptionsService.createAdoption(any(Adoption.class))).thenReturn(adoption);

        // Then
        MvcResult mvcResult = mockMvc.perform(post("/api/adoptions/")
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(adoption)))
                .andExpect(status().isCreated()).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        String expectedResponseBody = objectMapper.writeValueAsString(adoption);
        assertThat(expectedResponseBody).isEqualToIgnoringWhitespace(actualResponseBody);
    }

}
