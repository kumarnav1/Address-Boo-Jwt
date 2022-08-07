package com.bridgelabz.springsecurityjwt.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class AddressBookDTO {
    public long personId;
    public String username;
    public String password;
    public String role;
    @Pattern(regexp = "[a-zA-Z0-9][a-zA-Z-0-9_+]*([.][a-zA-Z0-9]+)?[@][a-zA-Z-0-9]+[.][a-z]{2,4}([.][a-zA-Z]{2,4})?", message = "Invalid Last Name")
    public String email;
    public boolean enabled;
    @Pattern(regexp = "^[A-Z]{1}[A-Za-z]{1,}$", message = "Invalid First Name")
    public String firstName;
    @Pattern(regexp = "^[A-Z]{1}[A-Za-z]{1,}$", message = "Invalid Last Name")
    public String lastName;
    @Pattern(regexp = "[0-9]{2}\\s[1-9][0-9]{9}$", message = "Invalid Phone number")
    public String phoneNumber;
    @NotEmpty(message = "address cannot be null")
    public String address;
    @NotEmpty(message = "city cannot be null")
    public String city;
    @NotEmpty(message = "state cannot be null")
    public String state;
    @NotNull(message = "zip cannot be null")
    public int zip;
}