package pl.sda.auctions.services;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import pl.sda.auctions.model.Auction;
import pl.sda.auctions.repository.AuctionRepository;
import pl.sda.auctions.repository.UserRepository;

@Service
public class AuctionService {

	private AuctionRepository auctionRepository;
	private UserRepository userRepository;

	public AuctionService(AuctionRepository auctionRepository, UserRepository userRepository) {
		this.auctionRepository = auctionRepository;
		this.userRepository = userRepository;
	}

	public void createAuction(String title, String description, String price, String ownerEmail) {

		var auction = new Auction(
				null,
				title,
				description,
				new BigDecimal(price),
				userRepository.findByEmail(ownerEmail).get()
		);
		auctionRepository.save(auction);
	}
}
