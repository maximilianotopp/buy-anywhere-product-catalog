package com.buyanywhere.productcatalog.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Category {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private int displayOrder;
    private boolean deleted;

    public Category(String name, int displayOrder, boolean deleted) {
        this.name = name;
        this.displayOrder = displayOrder;
        this.deleted = deleted;
    }

    public Category() {
    }

    public int getId() {
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
}