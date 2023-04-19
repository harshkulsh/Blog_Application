package com.example.blog.blog.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private Integer id;
    @NotEmpty
    @Size(min=4 , message = "username must be pf minimum 4 characters")
    private String name;
    @Email(message = "Email must be valid")
    private String email;
    @NotEmpty
    @Size(min=8, max=20, message = "password must be between 8 and 20 characters")
    private String password;
    @NotEmpty
    private String about;
}
