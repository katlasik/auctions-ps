package pl.sda.auctions.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Set;

@Entity
public class Auction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private User user;

	@NotEmpty
	@NotNull
	@Column
	private String title;

	@NotEmpty
	@NotNull
	@Column
	private String description;

	@NotEmpty
	@NotNull
	@Column
	private Double price;

	public Auction(){}

	public Auction(Long id, @NotEmpty @NotNull String title, @NotEmpty @NotNull String description, @NotEmpty @NotNull Double price) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.price = price;
	}

	@Override
	public String toString() {
		return "Auction{" +
				"id=" + id +
				", title='" + title + '\'' +
				", description='" + description + '\'' +
				", price=" + price +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Auction auction = (Auction) o;
		return Objects.equals(id, auction.id) &&
				Objects.equals(title, auction.title) &&
				Objects.equals(description, auction.description) &&
				Objects.equals(price, auction.price);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, title, description, price);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}


}
