package pl.sda.auctions.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import pl.sda.auctions.model.Role;
import pl.sda.auctions.model.User;
import pl.sda.auctions.model.dto.ChangePasswordForm;
import pl.sda.auctions.repository.UserRepository;
import pl.sda.auctions.services.UserService;

import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ChangePasswordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean UserService userService;


    @Test
    @DisplayName("Anonymous users should not be able to see change password page")
    void getChangePasswordByAnonymous() throws Exception {
        mockMvc.perform(get("/change-password")).andDo(print()).andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("Logged users should be able to access change password page")
    @WithMockUser(username = "name@gmail.com", password = "pass123")
    void getChangePasswordPage() throws Exception {
        mockMvc.perform(get("/change_password")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Zmień hasło")));
    }

    @Test
    @DisplayName("Happy path for password change")
    @WithMockUser(username = "name@gmail.com", password = "pass123")
    void postChangePassword() throws Exception {

        ChangePasswordForm changePasswordForm = new ChangePasswordForm("pass123", "newpass1234", "newpass1234");
        when(userService.changePassword("name@gmail.com","pass123", "newpass1234"))
                .thenReturn(true);

        mockMvc.perform(post("/change_password")
                .param("oldPassword", "pass123")
                .param("newPassword", "newpass1234")
                .param("retypedNewPassword", "newpass1234")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isFound());

    }

}
