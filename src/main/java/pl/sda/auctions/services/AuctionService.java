package pl.sda.auctions.services;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

import pl.sda.auctions.model.Auction;
import pl.sda.auctions.repository.AuctionRepository;
import pl.sda.auctions.repository.UserRepository;

@Service
public class AuctionService {

	private AuctionRepository auctionRepository;
	private UserService userService;

	public AuctionService(AuctionRepository auctionRepository, UserService userService) {
		this.auctionRepository = auctionRepository;
		this.userService = userService;
	}

	public void createAuction(String title, String description, String price, String ownerEmail) {

		if (userService.getUserByEmail(ownerEmail).isPresent()) {
			var auction = new Auction(
					null,
					title,
					description,
					new BigDecimal(price),
					userService.getUserByEmail(ownerEmail).get()
			);
			auctionRepository.save(auction);
		}
	}
}
