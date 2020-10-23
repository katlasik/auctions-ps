package pl.sda.auctions.dto;

import pl.sda.auctions.model.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserDto {

    private Long id;

    @Email
    @NotEmpty
    @NotNull
    private String email;

    @NotEmpty
    @NotNull
    private String password;

    @NotEmpty
    @NotNull
    private String name;

    private boolean active;
    @NotNull
    private Role role;

    public UserDto() {
    }
}
