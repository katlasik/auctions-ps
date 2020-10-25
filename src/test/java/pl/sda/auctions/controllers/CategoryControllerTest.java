package pl.sda.auctions.controllers;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import pl.sda.auctions.model.Category;
import pl.sda.auctions.repository.CategoryRepository;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CategoryControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CategoryRepository categoryRepository;

	@Test
	@DisplayName("No authenticated users should not be able to see create new category page")
	void getCategoryByUnauthUser() throws Exception {
		mockMvc.perform(get("/create_category")).andDo(print()).andExpect(status().is3xxRedirection());
	}

	@Test
	@DisplayName("Users with no ADMIN privileges should be able to access create new auction page")
	@WithMockUser(username = "admin@dummy.pl", password = "pass123", authorities = {"USER"})
	void getCategoryByNonADMINUser() throws Exception {
		mockMvc.perform(get("/create_category")).andDo(print()).andExpect(status().is4xxClientError());
	}

	@Test
	@DisplayName("ADMIN users should be able to access create new auction page")
	@WithMockUser(username = "admin@dummy.pl", password = "pass123", authorities = {"ADMIN"})
	void getCategoryByADMINUser() throws Exception {
		mockMvc.perform(get("/create_category")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Dodaj kategorię")));
	}

	@Test
	@DisplayName("Happy path for create new category")
	@WithMockUser(username = "admin@dummy.pl", password = "pass123", authorities = {"ADMIN"})
	void postCategory() throws Exception {

		Category category = new Category(null,
				"Nazwa kategorii",
				"Opis kategorii"
		);

		when(categoryRepository.save(category)).thenReturn(category);

		mockMvc.perform(post("/create_category")
				.param("title", "Nazwa kategorii")
				.param("description", "Opis kategorii")
				.with(csrf())
				.contentType(MediaType.APPLICATION_FORM_URLENCODED))
				.andExpect(status().isOk());

		verify(categoryRepository, times(1)).save(category);
	}
}