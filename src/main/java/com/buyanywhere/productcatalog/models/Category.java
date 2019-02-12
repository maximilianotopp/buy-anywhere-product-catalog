package com.buyanywhere.productcatalog.models;

import org.springframework.data.annotation.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

@Entity
public class Category{

    @Id
    @GeneratedValue
    private long Id;
    private String Name;
    private int DisplayOrder;
    private boolean Deleted = false;

    public Category(String name,int displayOrder, boolean deleted){

        this.setName(name);
        this.setDisplayOrder(displayOrder);
        this.setDeleted(deleted);
    }


    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getDisplayOrder() {
        return DisplayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        DisplayOrder = displayOrder;
    }

    public boolean isDeleted() {
        return Deleted;
    }

    public void setDeleted(boolean deleted) {
        Deleted = deleted;
    }
}