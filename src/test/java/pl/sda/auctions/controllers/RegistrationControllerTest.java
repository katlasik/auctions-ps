package pl.sda.auctions.controllers;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import pl.sda.auctions.model.Role;
import pl.sda.auctions.model.User;
import pl.sda.auctions.repository.UserRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RegistrationControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserRepository userRepository;

	@Test
	@DisplayName("Anonymous users should be able to see registration page")
	void getRegistration() throws Exception {
		mockMvc.perform(get("/registration")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Rejestracja")));
	}

	@Test
	@DisplayName("Happy path for registration")
	void postRegistration() throws Exception {

		User user = new User(null,
				"user@domain.pl",
				"password",
				"user",
				true,
				 Role.USER);

		when(userRepository.save(user)).thenReturn(user);
		when(userRepository.checkIfMailExists("user@domain.pl")).thenReturn(false);
		when(userRepository.checkIfNameExists("name")).thenReturn(false);

		mockMvc.perform(post("/registration")
				.param("name", "name")
				.param("password", "password")
				.param("retypedPassword", "password")
				.param("email", "user@domain.pl")
				.with(csrf())
				.contentType(MediaType.APPLICATION_FORM_URLENCODED))
				.andExpect(status().isFound());

		verify(userRepository, times(1)).checkIfMailExists("user@domain.pl");
		verify(userRepository, times(1)).checkIfNameExists("name");
		verify(userRepository, times(1)).save(user);
	}
}