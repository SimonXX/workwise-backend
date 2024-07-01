package com.workwise.workwisebackend.entities.actors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.workwise.workwisebackend.entities.Credential;
import com.workwise.workwisebackend.entities.JobOffer;
import com.workwise.workwisebackend.entities.Role;
import com.workwise.workwisebackend.support.auth.CredentialHolder;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "companies")
public class Company implements CredentialHolder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "credentials_id", referencedColumnName = "id")
    @JsonIgnore
    private Credential credentials;

    private String phone;
    private String address;
    private String website;
    private String description;
    private LocalDateTime createddate;

//    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinTable(name = "company_roles",
//            joinColumns = {
//                    @JoinColumn(name = "company_id")
//            },
//            inverseJoinColumns = {
//                    @JoinColumn(name = "role_id") })
//    private Set<Role> roles = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_role", referencedColumnName="name")
    private Role role;

    @JsonIgnore
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JobOffer> jobOffers;
}
