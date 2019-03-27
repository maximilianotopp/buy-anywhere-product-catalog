package com.buyanywhere.productcatalog.dto;

import java.util.ArrayList;
import java.util.List;

public class CategoryDto {
    private Long id;
    private String name;
    private int displayOrder;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    public boolean isValid(){
        return !(name == null || name.trim().isEmpty() || displayOrder < 0);
    }

    public String getInvalidFields(){
        List<String> invalidFields = new ArrayList<>();

        if(name == null || name.trim().isEmpty()){
            invalidFields.add("name");
        }

        if(displayOrder < 0){
            invalidFields.add("displayOrder");
        }

        return String.join(", ", invalidFields);
    }
}
