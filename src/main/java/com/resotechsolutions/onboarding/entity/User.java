package com.resotechsolutions.onboarding.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.*;


import java.util.List;

@Entity
@Table(name = "user")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "password")
    private String password;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private UserDetails userDetails;

    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }
}
