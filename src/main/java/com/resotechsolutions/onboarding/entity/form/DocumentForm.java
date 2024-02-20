package com.resotechsolutions.onboarding.entity.form;

import java.util.ArrayList;
import java.util.List;

public class DocumentForm{

    private List<FormData> pan = new ArrayList<>();
    
    private List<FormData> aadhar = new ArrayList<>();

    
    private List<FormData> marksheet = new ArrayList<>();

    public List<FormData> getPan() {
        return pan;
    }

    public void setPan(List<FormData> pan) {
        this.pan = pan;
    }

    public List<FormData> getAadhar() {
        return aadhar;
    }

    public void setAadhar(List<FormData> aadhar) {
        this.aadhar = aadhar;
    }

    public List<FormData> getMarksheet() {
        return marksheet;
    }

    public void setMarksheet(List<FormData> marksheet) {
        this.marksheet = marksheet;
    }
}
