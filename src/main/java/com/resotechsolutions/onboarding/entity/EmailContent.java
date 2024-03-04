package com.resotechsolutions.onboarding.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "email_info")
public class EmailContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "type")
    private String type;

    @Column(name = "subject")
    private String subject;

    @Column(name = "body")
    private String body;

    @Column(name = "cc")
    private String cc;

    @Column(name = "bcc")
    private String bcc;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_on")
    private Timestamp createdOn;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_on")
    private Timestamp updatedOn;

    public EmailContent() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getBcc() {
        return bcc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Timestamp getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Timestamp updatedOn) {
        this.updatedOn = updatedOn;
    }


    @Override
    public String toString() {
        return "EmailContent{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                ", cc='" + cc + '\'' +
                ", bcc='" + bcc + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdOn=" + createdOn +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedOn=" + updatedOn +
                '}';
    }
}
