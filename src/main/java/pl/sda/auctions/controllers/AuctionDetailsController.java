package pl.sda.auctions.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.server.ResponseStatusException;
import pl.sda.auctions.model.Auction;
import pl.sda.auctions.services.AuctionService;
import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
public class AuctionDetailsController {

    private AuctionService auctionService;

    public AuctionDetailsController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @GetMapping("/auction/{id}")
    public String getAuctionDetails( Model model, @PathVariable("id") Long id) {

        Auction auction = auctionService.getAuctionById(id).orElseThrow(() ->
                new ResponseStatusException(NOT_FOUND, "Couldn't find auction with id=" + id + "."));
        String title = auction.getTitle();
        String description = auction.getDescription();
        BigDecimal price = auction.getPrice();
        String owner = auction.getUser().getName();
        String category = auction.getCategory() != null ? auction.getCategory().getName() : "Brak Kategorii";
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("price", price);
        model.addAttribute("owner", owner);
        model.addAttribute("category", category);
        return "auction";
        }
    }


