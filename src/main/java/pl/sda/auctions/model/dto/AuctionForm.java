package pl.sda.auctions.model.dto;

import javax.validation.constraints.*;

public class AuctionForm {

	@NotEmpty
	@Size(min = 10, max = 200, message = "{auction.errorMsg.title}")
	private String title;

	@NotEmpty
	@Size(min = 10, max = 1000, message = "{auction.errorMsg.description}")
	private String description;

	@NotEmpty
	@Pattern(regexp = "^\\d+(\\.\\d{1,2})?$", message = "{auction.errorMsg.price}")
	private String price;

	public AuctionForm(@Size(min = 10, max = 200, message = "{auction.errorMsg.title}") String title,
					   @Size(min = 10, max = 2000, message = "{auction.errorMsg.description}") String description,
					   @Pattern(regexp = "^\\d+(\\.\\d{1,2})?$", message = "{auction.errorMsg.price}") String price,
					   String owner) {
		this.title = title;
		this.description = description;
		this.price = price;
		this.owner = owner;
	}

	private String owner;


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}
}
