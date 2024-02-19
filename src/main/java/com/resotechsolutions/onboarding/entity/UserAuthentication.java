package com.resotechsolutions.onboarding.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "user_auth")
public class UserAuthentication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "auth_token")
    private String token;

    @Column(name = "created_on")
    private Timestamp createdOn;

    public UserAuthentication(){

    }

    public UserAuthentication(long userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }
}
