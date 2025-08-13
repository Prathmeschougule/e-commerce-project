package com.ecom.project.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CollectionId;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "users",
        uniqueConstraints = {
                 @UniqueConstraint(columnNames = "userName"),
                @UniqueConstraint(columnNames = "email")
        })
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotBlank
    @Size(max=20)
    @Column(name = "userName")
    private String userName;

    @NotBlank
    @Size(max = 120)
    private String Password;

    @NotBlank
    @Size(max = 50)
    @Email
    @Column(name = "email")
    private String email;

    public User(String userName, String password, String email) {
        this.userName = userName;
        Password = password;
        this.email = email;
    }

    @Getter
    @Setter
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> role = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE},
    orphanRemoval = true)
    private Set<Product> product = new HashSet<>();
}
