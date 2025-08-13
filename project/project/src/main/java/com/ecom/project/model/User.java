package com.ecom.project.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotBlank
    @Size(max=20)
    private String userName;

    @NotBlank
    @Size(max = 120)
    private String Password;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    public User(String userName, String password, String email) {
        this.userName = userName;
        Password = password;
        this.email = email;
    }
}
