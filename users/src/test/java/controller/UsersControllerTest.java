package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hiberus.UsersApplication;
import com.hiberus.dtos.UserRequestDto;
import com.hiberus.models.User;
import com.hiberus.services.impl.UsersServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = UsersApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UsersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UsersServiceImpl usersService;

    @Test
    public void getUsersShouldReturn200() throws Exception {
        // Given
        User user = new User(1L, "John", "+34 344 85 23 83");
        List<User> users = List.of(user);

        // When
        when(usersService.getUsers()).thenReturn(users);

        // Then
        MvcResult mvcResult = mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk()).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        String expectedResponseBody = objectMapper.writeValueAsString(users);
        assertThat(expectedResponseBody).isEqualToIgnoringWhitespace(actualResponseBody);
    }

    @Test
    public void getUserShouldReturn200() throws Exception {
        // Given
        User user = new User(1L, "John", "+34 344 85 23 83");
        Long userId = 1L;

        // When
        when(usersService.getUser(anyLong())).thenReturn(user);

        // Then
        MvcResult mvcResult = mockMvc.perform(get("/api/users/{userId}", userId))
                .andExpect(status().isOk()).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        String expectedResponseBody = objectMapper.writeValueAsString(user);
        assertThat(expectedResponseBody).isEqualToIgnoringWhitespace(actualResponseBody);
    }

    @Test
    public void createUserShouldReturn201() throws Exception {
        // Given
        User user = new User(1L, "John", "+34 344 85 23 83");

        // When
        when(usersService.createUser(any(User.class))).thenReturn(user);

        // Then
        MvcResult mvcResult = mockMvc.perform(post("/api/users/")
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated()).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        String expectedResponseBody = objectMapper.writeValueAsString(user);
        assertThat(expectedResponseBody).isEqualToIgnoringWhitespace(actualResponseBody);
    }

    @Test
    public void updateUserShouldReturn200() throws Exception {
        // Given
        UserRequestDto userRequestDto = new UserRequestDto("John", "+34 344 85 23 83");
        User user = new User(1L, "John", "+34 344 85 23 83");
        Long userId = 1L;

        // When
        when(usersService.updateUser(anyLong(), any(User.class))).thenReturn(user);

        // Then
        MvcResult mvcResult = mockMvc.perform(put("/api/users/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(userRequestDto)))
                .andExpect(status().isOk()).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        String expectedResponseBody = objectMapper.writeValueAsString(user);
        assertThat(expectedResponseBody).isEqualToIgnoringWhitespace(actualResponseBody);
    }

    @Test
    public void deleteUserShouldReturn204() throws Exception {
        // Given
        Long userId = 1L;

        // When
        doNothing().when(usersService).deleteUser(userId);

        // Then
        mockMvc.perform(delete("/api/users/{userId}", userId))
                .andExpect(status().isNoContent());
    }

}
