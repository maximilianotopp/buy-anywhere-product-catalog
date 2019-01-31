package com.buyanywhere.productcatalog.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private int displayOrder;

    @Column(nullable = false)
    @JsonIgnore
    private boolean deleted;

    public Category(){
    }

    public Category (long id, String name, int displayOrder){
        this.id = id;
        this.name = name;
        this.displayOrder = displayOrder;
        this.deleted = false;
    }

    public long getId() {
        return id;
    }

    public void setName (String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}