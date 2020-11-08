package pl.sda.auctions.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

import pl.sda.auctions.model.Auction;
import pl.sda.auctions.model.User;
import pl.sda.auctions.repository.AuctionRepository;

@Service
public class AuctionService {

	private final AuctionRepository auctionRepository;
	private final UserService userService;
	private final CategoryService categoryService;

	private final Logger logger = LoggerFactory.getLogger(AuctionService.class);

	public AuctionService(AuctionRepository auctionRepository, UserService userService, CategoryService categoryService) {
		this.auctionRepository = auctionRepository;
		this.userService = userService;
		this.categoryService = categoryService;
	}

	public void createAuction(String title, String description, String price, String ownerEmail, Long categoryId) {
		User user = userService.getUserByEmail(ownerEmail).orElseThrow(() -> new IllegalStateException("User doesn't exist"));
		var	categoryID = categoryService.findById(categoryId).orElse(null);
		var auction = new Auction(
				null,
				title,
				description,
				new BigDecimal(price),
				user,
				categoryID);
		auctionRepository.save(auction);
		logger.info("Auction was created. Auction = {}", auction);
	}

	public Optional<Auction> getAuctionById(Long id) {
		return auctionRepository.findById(id);
	}

}
