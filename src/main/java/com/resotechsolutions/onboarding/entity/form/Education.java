package com.resotechsolutions.onboarding.entity.form;

import java.util.ArrayList;
import java.util.List;

public class Education{
    List<FormData> graduation = new ArrayList<>();
    List<FormData> secondary = new ArrayList<>();
    List<FormData> primary = new ArrayList<>();

    public Education() {
    }

    public List<FormData> getGraduation() {
        return graduation;
    }

    public void setGraduation(List<FormData> graduation) {
        this.graduation = graduation;
    }

    public List<FormData> getSecondary() {
        return secondary;
    }

    public void setSecondary(List<FormData> secondary) {
        this.secondary = secondary;
    }

    public List<FormData> getPrimary() {
        return primary;
    }

    public void setPrimary(List<FormData> primary) {
        this.primary = primary;
    }
}
