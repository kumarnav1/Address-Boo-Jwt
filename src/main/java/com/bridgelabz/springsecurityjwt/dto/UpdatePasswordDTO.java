package com.bridgelabz.springsecurityjwt.dto;

import lombok.Data;

@Data
public class UpdatePasswordDTO {
    public String username;
    public String email;
}
