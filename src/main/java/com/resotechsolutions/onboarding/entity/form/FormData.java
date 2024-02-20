package com.resotechsolutions.onboarding.entity.form;

import javax.persistence.*;

@Entity
@Table(name = "look_up")
public class FormData{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "column_name")
    private String columnName;

    @Column(name = "value")
    private String value;

    @Column(name = "actual_value")
    private String actualValue;



    public FormData() {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getActualValue() {
        return actualValue;
    }

    public void setActualValue(String actualValue) {
        this.actualValue = actualValue;
    }
}
