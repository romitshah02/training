package com.sunbird.training.entity;

import com.sunbird.training.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "username",unique = true)
    @NotNull(message = "Please enter a username")
    private String username;

    @Column(name = "password")
    @NotNull(message = "Please enter a password")
    private String password;

    public User(@NotNull(message = "role is required") Role role,
            @NotNull(message = "Please enter a username") String username,
            @NotNull(message = "Please enter a password") String password) {
        this.role = role;
        this.username = username;
        this.password = password;
    }

}
