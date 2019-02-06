package com.buyanywhere.productcatalog.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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

    public void Delete(){
        deleted = true;
    }

    @JsonIgnore
    public boolean isValid(){
        return !(name.trim().isEmpty() || displayOrder < 0);
    }
}