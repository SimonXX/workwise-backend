package com.workwise.workwisebackend.entities.actors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.workwise.workwisebackend.entities.Application;
import com.workwise.workwisebackend.entities.Credential;
import com.workwise.workwisebackend.entities.Role;
import com.workwise.workwisebackend.support.auth.CredentialHolder;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class User implements CredentialHolder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "credentials_id", referencedColumnName = "id")
    @JsonIgnore
    private Credential credentials;

    private String phone;
    private String address;
    private LocalDate dateOfBirth;

    @Column(name="cv", columnDefinition = "TEXT")
    private  String cv;
    private LocalDate createdDate;

//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(name = "user_roles",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id"))
//    private Set<Role> roles;

    //se non metto referencedColumnName, in automatico uso come foreign l'id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_user", referencedColumnName="name")
    private Role role;


    //cascadeType.All propaga tutte le operazioni di persistenza
    //orphan Removal dice che quando elimino un utente, si eliminano anche le applicazioni associate
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Application> applications;
}