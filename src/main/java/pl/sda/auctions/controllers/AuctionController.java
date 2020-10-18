package pl.sda.auctions.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuctionController {

	@PostMapping("/create_auction")
	public String getPostAuction(){
		return "create_auction";
	}
}
