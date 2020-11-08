package pl.sda.auctions.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

import pl.sda.auctions.model.Auction;
import pl.sda.auctions.model.User;
import pl.sda.auctions.repository.AuctionRepository;
import pl.sda.auctions.repository.UserRepository;

@Service
public class AuctionService {

	private final AuctionRepository auctionRepository;
	private final UserService userService;

	private final Logger logger = LoggerFactory.getLogger(AuctionService.class);

	public AuctionService(AuctionRepository auctionRepository, UserService userService) {
		this.auctionRepository = auctionRepository;
		this.userService = userService;
	}

	public void createAuction(String title, String description, String price, String ownerEmail) {

		User user = userService.getUserByEmail(ownerEmail).orElseThrow(() -> new IllegalStateException("User doesn't exist"));
		var auction = new Auction(
					null,
					title,
					description,
					new BigDecimal(price),
					user
			);
			auctionRepository.save(auction);
			logger.info("Auction was created. Auction = {}", auction);
	}

	public Optional<Auction> getAuctionById(Long id) {
		return auctionRepository.findById(id);
	}

}
