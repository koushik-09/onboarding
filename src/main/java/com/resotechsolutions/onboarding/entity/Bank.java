package com.resotechsolutions.onboarding.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.checkerframework.checker.units.qual.C;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "bank_details")
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonIgnore
    private long id;

    @Column(name = "account_number")
    private long  accountNumber;

    @Column(name = "ifsc")
    private String ifscCode;

    @Column(name = "bank_name")
    private String bankName;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private UserDetails userDetails;

    @Column(name = "created_on")
    @JsonIgnore
    private Timestamp createdOn;

    @Column(name = "updated_on")
    @JsonIgnore
    private Timestamp updatedOn;

    public Bank() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }
}
