package com.jerorodriguez.springbootjwt.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponse {
    private String username;
    private String firstName;
    private String lastName;
    private String country;
    private String role;
}
