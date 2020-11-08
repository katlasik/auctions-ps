package pl.sda.auctions.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import pl.sda.auctions.model.Auction;
import pl.sda.auctions.model.Role;
import pl.sda.auctions.model.User;
import pl.sda.auctions.repository.AuctionRepository;
import pl.sda.auctions.repository.UserRepository;
import pl.sda.auctions.services.AuctionService;

import java.math.BigDecimal;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuctionDetailsControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    AuctionRepository auctionRepository;

    @Test
    @DisplayName("All users should be able to display auction details.")
    @WithMockUser(username = "admin@dummy.pl", password = "pass123")
    void viewAuctionDetails() throws Exception {

        Auction auction = new Auction(null,
                "Tytuł aukcji",
                "Opis aukcji",
                new BigDecimal("10"),
                new User(null, "name@gmail.com", "pass123", "User1", true, Role.USER)
        );
        Mockito.when(auctionRepository.findById(auction.getId())).thenReturn(Optional.of(auction));
        mockMvc.perform(get("/auction")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Tytuł aukcji")))
                .andExpect(content().string(containsString("Opis aukcji")))
                .andExpect(content().string(containsString("10")))
                .andExpect(content().string(containsString("User1")));
    }
}