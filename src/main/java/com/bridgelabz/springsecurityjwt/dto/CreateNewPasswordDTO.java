package com.bridgelabz.springsecurityjwt.dto;

import lombok.Data;

@Data
public class CreateNewPasswordDTO {
    String username;
    String email;
    String otp;
    String newPassword;
}
