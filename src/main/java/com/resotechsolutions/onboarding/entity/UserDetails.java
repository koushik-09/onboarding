package com.resotechsolutions.onboarding.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "user_details")
public class UserDetails {

    @Id
    @Column(name = "user_id")
    private long user_id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private Long phoneNumber;

    @Column(name = "gender")
    private Character gender;

    @Column(name = "dob")
    private LocalDate dateOfBirth;

    @Column(name = "isActive")
    private boolean isActive;

    @Column(name = "isAdmin")
    private boolean isAdmin;

    @Column(name = "isPasswordUpdated")
    private boolean isPasswordUpdated;

    @Column(name = "created_on")
    private Timestamp createdOn;

    @Column(name = "updated_on")
    private Timestamp updatedOn;

    @Transient
    private String userToken;

    @OneToOne(mappedBy = "userDetails",cascade = CascadeType.ALL)
    @JsonManagedReference
    private User user;

    @OneToOne(mappedBy = "userDetails",cascade = CascadeType.ALL)
    @JsonManagedReference
    private Address address;

    @OneToOne(mappedBy = "userDetails",cascade = CascadeType.ALL)
    @JsonManagedReference
    private Token token;

    @OneToMany(mappedBy = "userDetails", cascade = CascadeType.ALL)
    private List<Documents> documents;


    public UserDetails() {
    }

    public UserDetails(long user_id,String userName, String firstName, String lastName, String email, long phoneNumber, char gender, LocalDate dateOfBirth, boolean isActive, boolean isAdmin, Timestamp createdOn, Timestamp updatedOn) {
        this.user_id = user_id;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.isActive = isActive;
        this.isAdmin = isAdmin;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
    }

    public UserDetails(long user_id,String userName, String firstName, String lastName, String email){
        this.user_id = user_id;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    public Timestamp getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Timestamp updatedOn) {
        this.updatedOn = updatedOn;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public List<Documents> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Documents> documents) {
        this.documents = documents;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public boolean isPasswordUpdated() {
        return isPasswordUpdated;
    }

    public void setPasswordUpdated(boolean passwordUpdated) {
        isPasswordUpdated = passwordUpdated;
    }
////    @Override
////    public String toString() {
////        return "UserDetails{" +
////                "user_id=" + user_id +
////                ", userName='" + userName + '\'' +
////                ", firstName='" + firstName + '\'' +
////                ", lastName='" + lastName + '\'' +
////                ", email='" + email + '\'' +
////                ", phoneNumber=" + phoneNumber +
////                ", gender=" + gender +
////                ", dateOfBirth=" + dateOfBirth +
////                ", isActive=" + isActive +
////                ", isAdmin=" + isAdmin +
////                ", createdOn=" + createdOn +
////                ", updatedOn=" + updatedOn +
////                '}';
//    }


    @Override
    public String toString() {
        return "UserDetails{" +
                "user_id=" + user_id +
                ", userName='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", gender=" + gender +
                ", dateOfBirth=" + dateOfBirth +
                ", isActive=" + isActive +
                ", isAdmin=" + isAdmin +
                ", isPasswordUpdated=" + isPasswordUpdated +
                ", createdOn=" + createdOn +
                ", updatedOn=" + updatedOn +
                ", userToken='" + userToken + '\'' +
                '}';
    }
}
