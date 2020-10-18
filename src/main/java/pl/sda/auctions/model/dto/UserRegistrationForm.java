package pl.sda.auctions.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Objects;

public class UserRegistrationForm {

	@Size(min = 3, max = 20, message = "{registration.errorMsg.name}")
	private String name;

	@Email(regexp = ".+@.+\\..+", message = "{registration.errorMsg.email}")
	private String email;

	@Size(min = 6, max = 100, message = "{registration.errorMsg.password}")
	private String password;
	@Size(min = 6, max = 100, message = "{registration.errorMsg.password}")
	private String retypedPassword;

	public UserRegistrationForm(String name, String email, String password, String retypedPassword) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.retypedPassword = retypedPassword;
	}

	public String getRetypedPassword() {
		return retypedPassword;
	}

	public void setRetypedPassword(String retypedPassword) {
		this.retypedPassword = retypedPassword;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		UserRegistrationForm that = (UserRegistrationForm) o;
		return Objects.equals(name, that.name) &&
				Objects.equals(email, that.email) &&
				Objects.equals(password, that.password) &&
				Objects.equals(retypedPassword, that.retypedPassword);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, email, password, retypedPassword);
	}

}
