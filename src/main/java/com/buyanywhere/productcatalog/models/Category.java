package com.buyanywhere.productcatalog.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Category {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private int displayOrder;
    private boolean deleted;

	public Category() {
	}

	public Category(String name, int displayOrder, boolean deleted) {
		this.name = name;
		this.displayOrder = displayOrder;
		this.deleted = deleted;
	}

    public long getId() {
        return id;
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

	public boolean isDeleted() {
		return deleted;
	}

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String showInformation() {
        return "Name: " + this.name + ", Order: " + Integer.toString(this.displayOrder);
    }

    public boolean isValid() {
        if ((this.name == "") || (this.displayOrder < 0)) {
            return false;
        }

        return true;
    }

    public void delete(){
	    this.deleted = true;
    }
}