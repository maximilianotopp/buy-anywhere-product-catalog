package com.buyanywhere.productcatalog.models;

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
    private boolean deleted;

    private Category(String name, int displayOrder, boolean deleted){
        this.name = name;
        this.displayOrder = displayOrder;
        this.deleted = deleted;
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
}
