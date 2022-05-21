package com.example.simplechat.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Role {

    @Id
    private String name;

    @ManyToMany(mappedBy = "role")
    private List<Member> members;

    public Role(String name) {
        this.name = name;
    }
}
