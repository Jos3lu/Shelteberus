package com.hiberus.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hiberus.DogsApplication;
import com.hiberus.enums.Breed;
import com.hiberus.models.Dog;
import com.hiberus.services.impl.DogsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
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

@SpringBootTest(classes = DogsApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class DogsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DogsServiceImpl dogsService;

    @BeforeEach
    public void setUp() {
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void getDogsShouldReturn200() throws Exception {
        // Given
        Dog dog = new Dog(1L, "Firulais", Breed.BEAGLE, LocalDate.now(), null);
        List<Dog> dogs = List.of(dog);

        // When
        when(dogsService.getDogs()).thenReturn(dogs);

        // Then
        MvcResult mvcResult = mockMvc.perform(get("/api/dogs"))
                .andExpect(status().isOk()).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        String expectedResponseBody = objectMapper.writeValueAsString(dogs);
        assertThat(expectedResponseBody).isEqualToIgnoringWhitespace(actualResponseBody);
    }

    @Test
    public void getUserReservedDogsShouldReturn200() throws Exception {
        // Given
        Dog dog = new Dog(1L, "Firulais", Breed.BEAGLE, LocalDate.now(), null);
        List<Dog> dogs = List.of(dog);
        Long userId = 2L;

        // When
        when(dogsService.getUserReservedDogs(anyLong())).thenReturn(dogs);

        // Then
        MvcResult mvcResult = mockMvc.perform(get("/api/dogs/reserved-dogs")
                        .param("userId", String.valueOf(userId)))
                .andExpect(status().isOk()).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        String expectedResponseBody = objectMapper.writeValueAsString(dogs);
        assertThat(expectedResponseBody).isEqualToIgnoringWhitespace(actualResponseBody);
    }

    @Test
    public void getVolunteerDogsShouldReturn200() throws Exception {
        // Given
        Dog dog = new Dog(1L, "Firulais", Breed.BEAGLE, LocalDate.now(), null);
        List<Dog> dogs = List.of(dog);
        List<Long> dogsId = List.of(1L);

        // When
        when(dogsService.getVolunteerDogs(anyList())).thenReturn(dogs);

        // Then
        MvcResult mvcResult = mockMvc.perform(get("/api/dogs/volunteer-dogs")
                        .param("dogsId", dogsId.get(0).toString()))
                .andExpect(status().isOk()).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        String expectedResponseBody = objectMapper.writeValueAsString(dogs);
        assertThat(expectedResponseBody).isEqualToIgnoringWhitespace(actualResponseBody);
    }

    @Test
    public void getDogShouldReturn200() throws Exception {
        // Given
        Dog dog = new Dog(1L, "Firulais", Breed.BEAGLE, LocalDate.now(), null);
        Long dogId = 1L;

        // When
        when(dogsService.getDog(anyLong())).thenReturn(dog);

        // Then
        MvcResult mvcResult = mockMvc.perform(get("/api/dogs/{dogId}", dogId))
                .andExpect(status().isOk()).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        String expectedResponseBody = objectMapper.writeValueAsString(dog);
        assertThat(expectedResponseBody).isEqualToIgnoringWhitespace(actualResponseBody);
    }

    @Test
    public void createDogShouldReturn201() throws Exception {
        // Given
        Dog dog = new Dog(1L, "Firulais", Breed.BEAGLE, LocalDate.now(), null);

        // When
        when(dogsService.createDog(any(Dog.class))).thenReturn(dog);

        // Then
        MvcResult mvcResult = mockMvc.perform(post("/api/dogs/")
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(dog)))
                .andExpect(status().isCreated()).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        String expectedResponseBody = objectMapper.writeValueAsString(dog);
        assertThat(expectedResponseBody).isEqualToIgnoringWhitespace(actualResponseBody);
    }

    @Test
    public void deleteDogShouldReturn204() throws Exception {
        // Given
        Long dogId = 1L;

        // When
        doNothing().when(dogsService).deleteDog(anyLong());

        // Then
        mockMvc.perform(delete("/api/dogs/{dogId}", dogId))
                .andExpect(status().isNoContent());
    }

    @Test
    public void reserveDogShouldReturn200() throws Exception {
        // Given
        Dog dog = new Dog(1L, "Firulais", Breed.BEAGLE, LocalDate.now(), 2L);
        Long dogId = 1L;
        Long userId = 2L;

        // When
        when(dogsService.reserveDog(anyLong(), anyLong())).thenReturn(dog);

        // Then
        MvcResult mvcResult = mockMvc.perform(put("/api/dogs/reserve-dog")
                        .param("dogId", String.valueOf(dogId))
                        .param("userId", String.valueOf(userId)))
                .andExpect(status().isOk()).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        String expectedResponseBody = objectMapper.writeValueAsString(dog);
        assertThat(expectedResponseBody).isEqualToIgnoringWhitespace(actualResponseBody);
    }

    @Test
    public void cancelReserveShouldReturn200() throws Exception {
        // Given
        Dog dog = new Dog(1L, "Firulais", Breed.BEAGLE, LocalDate.now(), null);
        Long dogId = 1L;

        // When
        when(dogsService.cancelReserve(anyLong())).thenReturn(dog);

        // Then
        MvcResult mvcResult = mockMvc.perform(put("/api/dogs/cancel-reserve")
                        .param("dogId", String.valueOf(dogId)))
                .andExpect(status().isOk()).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        String expectedResponseBody = objectMapper.writeValueAsString(dog);
        assertThat(expectedResponseBody).isEqualToIgnoringWhitespace(actualResponseBody);
    }

}