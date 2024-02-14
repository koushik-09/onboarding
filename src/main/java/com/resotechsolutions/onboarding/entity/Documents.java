package com.resotechsolutions.onboarding.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "documents")
public class Documents {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "type")
    private int type;

    @Column(name = "url")
    private String url;

    @Column(name = "added_on")
    private Timestamp addedOn;

    @Column(name = "updated_on")
    private Timestamp updatedOn;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private UserDetails userDetails;

    public Documents() {
    }


}

