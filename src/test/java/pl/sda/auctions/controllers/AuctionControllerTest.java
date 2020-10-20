package pl.sda.auctions.controllers;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import pl.sda.auctions.model.Auction;
import pl.sda.auctions.model.Role;
import pl.sda.auctions.model.User;
import pl.sda.auctions.repository.AuctionRepository;
import pl.sda.auctions.repository.UserRepository;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuctionControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AuctionRepository auctionRepository;

	@Test
	@DisplayName("Anonymous users should not be able to see create new auction page")
	void getAuctionByUnauthUser() throws Exception {
		mockMvc.perform(get("/create_auction")).andDo(print()).andExpect(status().is3xxRedirection());
	}

	@Test
	@DisplayName("Authenticated users should be able to access create new auction page")
	@WithMockUser(username = "name@gmail.com", password = "pass123")
	void getAuctionByAuthUser() throws Exception {
		mockMvc.perform(get("/create_auction")).andDo(print()).andExpect(status().isOk())
		.andExpect(content().string(containsString("Wystaw przedmiot")));
	}

	@Test
	@DisplayName("Happy path for create new auction")
	@WithMockUser(username = "name@gmail.com", password = "pass123")
	void postAuction() throws Exception {

		Auction auction = new Auction(null,
				"Tytuł aukcji",
				"Opis minimum 10 znaków",
				new BigDecimal(2.99),
				new User(null, "a@a.pl", "pass", "Tester", true, Role.USER)
		);

		when(auctionRepository.save(auction)).thenReturn(auction);

		mockMvc.perform(post("/create_auction")
				.param("title", "Tytuł aukcji")
				.param("description", "Opis minimum 10 znaków")
				.param("price", "2.99")
				.param("owner", "a@a.pl")
				.with(csrf())
				.contentType(MediaType.APPLICATION_FORM_URLENCODED))
				.andExpect(status().isFound());

		verify(auctionRepository, times(1)).save(auction);
	}

}