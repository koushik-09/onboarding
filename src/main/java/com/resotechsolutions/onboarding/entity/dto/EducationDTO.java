package com.resotechsolutions.onboarding.entity.dto;

import com.resotechsolutions.onboarding.entity.Education;

public class EducationDTO {

    private long id;

    private int type;
    private String gradDegree;
    private String gradMajor;
    private String gradInstitution;
    private float gradCgpa;
    private int gradStart;
    private int gradEnd;
    private String gradMemoUrl;
    private String secondDegree;
    private String secondMajor;
    private String secondInstitution;
    private float secondCgpa;
    private int secondStart;
    private int secondEnd;
    private String secondMemoUrl;
    private String primDegree;
    private String primMajor;
    private String primInstitution;
    private float primCgpa;
    private int primStart;
    private int primEnd;
    private String primMemoUrl;

    public EducationDTO() {
    }
    public Education getGraduation(EducationDTO educationDTO){
        Education graduation = new Education();
        graduation.setId(educationDTO.getId());
        graduation.setDegree(educationDTO.getGradDegree());
        graduation.setMajor(educationDTO.getGradMajor());
        graduation.setInstitution(educationDTO.getGradInstitution());
        graduation.setCgpa(educationDTO.getGradCgpa());
        graduation.setStartYear(educationDTO.getGradStart());
        graduation.setEndYear(educationDTO.getGradEnd());
        return graduation;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGradDegree() {
        return gradDegree;
    }

    public void setGradDegree(String gradDegree) {
        this.gradDegree = gradDegree;
    }

    public String getGradMajor() {
        return gradMajor;
    }

    public void setGradMajor(String gradMajor) {
        this.gradMajor = gradMajor;
    }

    public String getGradInstitution() {
        return gradInstitution;
    }

    public void setGradInstitution(String gradInstitution) {
        this.gradInstitution = gradInstitution;
    }

    public float getGradCgpa() {
        return gradCgpa;
    }

    public void setGradCgpa(float gradCgpa) {
        this.gradCgpa = gradCgpa;
    }

    public int getGradStart() {
        return gradStart;
    }

    public void setGradStart(int gradStart) {
        this.gradStart = gradStart;
    }

    public int getGradEnd() {
        return gradEnd;
    }

    public void setGradEnd(int gradEnd) {
        this.gradEnd = gradEnd;
    }

    public String getSecondDegree() {
        return secondDegree;
    }

    public void setSecondDegree(String secondDegree) {
        this.secondDegree = secondDegree;
    }

    public String getSecondMajor() {
        return secondMajor;
    }

    public void setSecondMajor(String secondMajor) {
        this.secondMajor = secondMajor;
    }

    public String getSecondInstitution() {
        return secondInstitution;
    }

    public void setSecondInstitution(String secondInstitution) {
        this.secondInstitution = secondInstitution;
    }

    public float getSecondCgpa() {
        return secondCgpa;
    }

    public void setSecondCgpa(float secondCgpa) {
        this.secondCgpa = secondCgpa;
    }

    public int getSecondStart() {
        return secondStart;
    }

    public void setSecondStart(int secondStart) {
        this.secondStart = secondStart;
    }

    public int getSecondEnd() {
        return secondEnd;
    }

    public void setSecondEnd(int secondEnd) {
        this.secondEnd = secondEnd;
    }

    public String getPrimDegree() {
        return primDegree;
    }

    public void setPrimDegree(String primDegree) {
        this.primDegree = primDegree;
    }

    public String getPrimMajor() {
        return primMajor;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setPrimMajor(String primMajor) {
        this.primMajor = primMajor;
    }

    public String getPrimInstitution() {
        return primInstitution;
    }

    public void setPrimInstitution(String primInstitution) {
        this.primInstitution = primInstitution;
    }

    public float getPrimCgpa() {
        return primCgpa;
    }

    public void setPrimCgpa(float primCgpa) {
        this.primCgpa = primCgpa;
    }

    public int getPrimStart() {
        return primStart;
    }

    public void setPrimStart(int primStart) {
        this.primStart = primStart;
    }

    public int getPrimEnd() {
        return primEnd;
    }

    public void setPrimEnd(int primEnd) {
        this.primEnd = primEnd;
    }

    public String getGradMemoUrl() {
        return gradMemoUrl;
    }

    public void setGradMemoUrl(String gradMemoUrl) {
        this.gradMemoUrl = gradMemoUrl;
    }

    public String getSecondMemoUrl() {
        return secondMemoUrl;
    }

    public void setSecondMemoUrl(String secondMemoUrl) {
        this.secondMemoUrl = secondMemoUrl;
    }

    public String getPrimMemoUrl() {
        return primMemoUrl;
    }

    public void setPrimMemoUrl(String primMemoUrl) {
        this.primMemoUrl = primMemoUrl;
    }

    @Override
    public String toString() {
        return "EducationDTO{" +
                "id=" + id +
                ", type=" + type +
                ", gradDegree='" + gradDegree + '\'' +
                ", gradMajor='" + gradMajor + '\'' +
                ", gradInstitution='" + gradInstitution + '\'' +
                ", gradCgpa=" + gradCgpa +
                ", gradStart=" + gradStart +
                ", gradEnd=" + gradEnd +
                ", secondDegree='" + secondDegree + '\'' +
                ", secondMajor='" + secondMajor + '\'' +
                ", secondInstitution='" + secondInstitution + '\'' +
                ", secondCgpa=" + secondCgpa +
                ", secondStart=" + secondStart +
                ", secondEnd=" + secondEnd +
                ", primDegree='" + primDegree + '\'' +
                ", primMajor='" + primMajor + '\'' +
                ", primInstitution='" + primInstitution + '\'' +
                ", primCgpa=" + primCgpa +
                ", primStart=" + primStart +
                ", primEnd=" + primEnd +
                '}';
    }
}
