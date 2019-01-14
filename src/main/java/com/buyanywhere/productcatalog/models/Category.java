package com.buyanywhere.productcatalog.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private String name;
    private int displayOrder;
    private boolean deleted;

    public Category(){
    }

    public Category(int id, String name, int displayOrder){
        this.id = id;
        this.name = name;
        this.displayOrder= displayOrder;
        this.deleted = false;
    }

    public int getId(){
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName (String name){
        this.name = name;
    }

    public int getDisplayOrder() {
        return this.displayOrder;
    }

    public void setDisplayOrder (int displayOrder) {
        this.displayOrder = displayOrder;
    }

    public boolean getDeleted(){
        return this.deleted;
    }

    public void setDeleted (boolean deleted){
        this.deleted = deleted;
    }

}