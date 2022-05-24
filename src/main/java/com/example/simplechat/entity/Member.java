package com.example.simplechat.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {

    @Id
    @NotEmpty(message = "Login should not empty")
    @Size(min = 2, max = 20, message = "Login should be between 2 and 20 characters")
    private String login;

    @NotEmpty(message = "First name should not empty")
    @Size(min = 2, max = 20, message = "First name should be between 2 and 20 characters")
    private String firstName;

    @NotEmpty(message = "Last name should not empty")
    @Size(min = 2, max = 20, message = "Last name should be between 2 and 20 characters")
    private String lastName;

    @NotEmpty(message = "password should not empty")
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
