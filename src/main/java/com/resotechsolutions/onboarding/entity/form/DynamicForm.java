package com.resotechsolutions.onboarding.entity.form;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.checkerframework.checker.units.qual.C;

import javax.persistence.*;

@Entity
@Table(name = "dynamic_form")
public class DynamicForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonIgnore
    private long id;

    @Column(name = "column_name")
    private String columnName;

    @Column(name = "field")
    private String field;

    @Column(name = "name")
    private String name;

    @Column(name = "label")
    private String label;

    @Column(name = "placeholder")
    private String placeHolder;

    @Column(name = "maxLength")
    private Integer maxLength;

    @Column(name = "isRequired")
    private Boolean isRequired;

    @Column(name = "badMessage")
    private String badMessage;

    @Column(name = "options")
    private String options;

    @Column(name = "keyboardType")
    private String keyboardType;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "filename")
    private String fileName;

    @Column(name = "url")
    private String url;

    @Column(name = "order")
    @JsonIgnore
    private int order;

    public DynamicForm() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPlaceHolder() {
        return placeHolder;
    }

    public void setPlaceHolder(String placeHolder) {
        this.placeHolder = placeHolder;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public Boolean getRequired() {
        return isRequired;
    }

    public void setRequired(Boolean required) {
        isRequired = required;
    }

    public String getBadMessage() {
        return badMessage;
    }

    public void setBadMessage(String badMessage) {
        this.badMessage = badMessage;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getKeyboardType() {
        return keyboardType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setKeyboardType(String keyboardType) {
        this.keyboardType = keyboardType;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
