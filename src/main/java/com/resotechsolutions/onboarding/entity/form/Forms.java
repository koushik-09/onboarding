package com.resotechsolutions.onboarding.entity.form;




import java.util.ArrayList;
import java.util.List;

public class Forms {
    private List<FormData> primaryDetails = new ArrayList<>();
    private Education education;

    private DocumentForm documents;

    public Forms() {
        education = new Education();
    }
    public List<FormData> getPrimaryDetails() {
        return primaryDetails;
    }

    public void setPrimaryDetails(List<FormData> primaryDetails) {
        this.primaryDetails = primaryDetails;
    }

    public Education getEducation() {
        return education;
    }
    public void setEducation(Education education) {
        this.education = education;
    }

    public DocumentForm getDocuments() {
        return documents;
    }

    public void setDocuments(DocumentForm documents) {
        this.documents = documents;
    }
}