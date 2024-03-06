package com.resotechsolutions.onboarding.entity.form;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Forms {

    private List<FormData> personalDetails = new ArrayList<>();

    private List<FormData> education = new ArrayList<>();

    private List<FormData> panDetails = new ArrayList<>();

    private List<FormData> aadharDetails = new ArrayList<>();

//    private List<FormData> marksheetDetails = new ArrayList<>();

    private List<FormData> agreementDetails = new ArrayList<>();

    private List<FormData> bankDetails = new ArrayList<>();

    public Forms() {
    }

    public void setPersonalDetails(List<DynamicForm> dynamicForms){
        populateForm(dynamicForms,personalDetails);
    }

    public List<FormData> getPersonalDetails() {
        return personalDetails;
    }

    public List<FormData> getEducation() {
        return education;
    }

    public void setEducation(List<DynamicForm> dynamicForms) {
        populateForm(dynamicForms,education);
    }

    public void setPanDetails(List<DynamicForm> dynamicForms) {
        populateForm(dynamicForms,panDetails);
    }

    public void setAadharDetails(List<DynamicForm> dynamicForms) {
        populateForm(dynamicForms,aadharDetails);
    }

//    public void setMarksheetDetails(List<DynamicForm> dynamicForms) {
//        populateForm(dynamicForms,marksheetDetails);
//    }

    public void setAgreementDetails(List<DynamicForm> dynamicForms) {
        populateForm(dynamicForms,agreementDetails);
    }

    public void setBankDetails(List<DynamicForm> dynamicForms) {
        populateForm(dynamicForms,bankDetails);
    }

    public List<FormData> getAgreementDetails() {
        return agreementDetails;
    }

    public List<FormData> getBankDetails() {
        return bankDetails;
    }

    public List<FormData> getPanDetails() {
        return panDetails;
    }

    public List<FormData> getAadharDetails() {
        return aadharDetails;
    }

//    public List<FormData> getMarksheetDetails() {
//        return marksheetDetails;
//    }

    public void populateForm(List<DynamicForm> dynamicForms, List<FormData> list){
        for (DynamicForm form : dynamicForms){
            FormData helper = new FormData();
            helper.setField(form.getField());
            Map<String,Object> map = new LinkedHashMap<>();
            try{
                Field[] fields = form.getClass().getDeclaredFields();
                for(Field field : fields){
                    field.setAccessible(true);
                    if(!field.getName().equals("id") && !field.getName().equals("field") && !field.getName().equalsIgnoreCase("columnName") && !field.getName().equalsIgnoreCase("order")){
                        if(field.get(form)!=null){
                            if(field.getName().equalsIgnoreCase("options")){
                                map.put(field.getName(),convertStringToJsonArray((String) field.get(form)));
                            }
                            else{
                                map.put(field.getName(),field.get(form));
                            }
                        }
                    }
                }
                helper.setData(map);
                list.add(helper);
            }catch (IllegalAccessException ignored){
            }
        }
    }
    public List<Gender> convertStringToJsonArray(String jsonString) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Gender>>() {}.getType();
        List<Gender> genderList = gson.fromJson(jsonString, listType);

        return genderList;
    }
}