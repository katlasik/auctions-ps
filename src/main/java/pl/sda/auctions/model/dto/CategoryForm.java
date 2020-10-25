package pl.sda.auctions.model.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CategoryForm {

	@NotNull
	@Size(min = 3, max = 100, message = "{category.errorMsg.name}")
	private String name;

	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
