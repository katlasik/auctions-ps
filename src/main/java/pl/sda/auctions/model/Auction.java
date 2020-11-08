package pl.sda.auctions.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Auction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private User owner;

	@NotEmpty
	@NotNull
	@Column
	@Size(min = 10, max = 200)
	private String title;

	@NotEmpty
	@NotNull
	@Column
	@Size(min = 10, max = 2000)
	private String description;

	@NotNull
	@Column
	private BigDecimal price;

	@OneToOne
	private Category category;

	protected Auction(){}

	public Auction(Long id,
				   @NotEmpty @NotNull String title,
				   @NotEmpty @NotNull String description,
				   @NotNull BigDecimal price,
				   User owner,
				   Category category) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.price = price;
		this.owner = owner;
		this.category = category;
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
		return owner;
	}

	public void setUser(User owner) {
		this.owner = owner;
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

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}


}
