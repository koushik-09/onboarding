package com.resotechsolutions.onboarding.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.checkerframework.checker.units.qual.C;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "education")
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonIgnore
    private long id;

    @Column(name = "type")
    @JsonIgnore
    private int type;

    @Transient
    private String name;

    @Column(name = "degree_name")
    private String degree;

    @Column(name = "major")
    private String major;

    @Column(name = "institution_name")
    private String institution;

    @Column(name = "cgpa")
    private Float cgpa;

    @Column(name = "start")
    private int startYear;

    @Column(name = "end")
    private int endYear;

    @Column(name = "url")
    private String memoUrl;

    @Column(name = "created_on")
    @JsonIgnore
    private Timestamp createdOn;
    @Column(name = "updated_on")
    @JsonIgnore
    private Timestamp updatedOn;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private UserDetails userDetails;

    public Education() {
    }

    public Education(int type, String degree, String major, String institution, Float cgpa, int startYear, int endYear) {
        this.type = type;
        this.degree = degree;
        this.major = major;
        this.institution = institution;
        this.cgpa = cgpa;
        this.startYear = startYear;
        this.endYear = endYear;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public Float getCgpa() {
        return cgpa;
    }

    public void setCgpa(Float cgpa) {
        this.cgpa = cgpa;
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public int getEndYear() {
        return endYear;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public String getMemoUrl() {
        return memoUrl;
    }

    public void setMemoUrl(String memoUrl) {
        this.memoUrl = memoUrl;
    }

    @Override
    public String toString() {
        return "Education{" +
                "id=" + id +
                ", type=" + type +
                ", degree='" + degree + '\'' +
                ", major='" + major + '\'' +
                ", institution='" + institution + '\'' +
                ", cgpa=" + cgpa +
                ", startYear=" + startYear +
                ", endYear=" + endYear +
                ", createdOn=" + createdOn +
                ", updatedOn=" + updatedOn +
                '}';
    }
}
