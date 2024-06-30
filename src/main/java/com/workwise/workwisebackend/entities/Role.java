package com.workwise.workwisebackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.workwise.workwisebackend.entities.actors.Company;
import com.workwise.workwisebackend.entities.actors.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;


    private String name;

//    @JsonIgnore
//    @ManyToMany(mappedBy = "roles")
//    private Set<User> users;

    @JsonIgnore
    @OneToMany(mappedBy = "role")
    private List<User> user;

    @JsonIgnore
    @OneToMany(mappedBy = "role")
    private List<Company> company;
}

