package com.example.simplechat.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Member {

    @Id
    @NotEmpty(message = "Login should not empty")
    private String login;

    @NotEmpty(message = "First name should not empty")
    @Size(min = 2, max = 20, message = "First name should be between 2 and 30 characters")
    private String firstName;

    @NotEmpty(message = "Last name should not empty")
    @Size(min = 2, max = 20, message = "Last name should be between 2 and 30 characters")
    private String lastName;

    @NotNull
    @NotEmpty(message = "password should not empty")
    @Size(min = 4, max = 16, message = "Password should be between 4 and 16 characters")
    private String password;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "MEMBER_ROLES", joinColumns = {
            @JoinColumn(name = "MEMBER_LOGIN", referencedColumnName = "login")}, inverseJoinColumns = {
            @JoinColumn(name = "ROLE_NAME", referencedColumnName = "name")})
    private List<Role> role;

    public Member(String login, String firstName, String lastName, String password) {
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }
}
