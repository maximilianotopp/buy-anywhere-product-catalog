package com.buyanywhere.productcatalog.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private int displayOrder;
    @JsonIgnore
    private boolean deleted;

    public Category(String name, int displayOrder){
        this.name = name;
        this.displayOrder = displayOrder;
        this.deleted = false;
    }

    public Category(){
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void delete(){
        deleted = true;
    }

    public boolean isValid(){
        return !(name.trim().isEmpty() || displayOrder < 0);
    }

    public String invalidValue(){
        List<String> attributes = new ArrayList<>();
        if (name.trim().isEmpty()){
            attributes.add("name");
        }

        if (displayOrder < 0){
            attributes.add("displayOrder");
        }

        return String.join(", ", attributes);
    }
}