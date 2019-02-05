package com.buyanywhere.productcatalog.models;

public class Category {
	private long id;
	private String name;
	private int displayOrder;
	private boolean deleted;

	public Category() {
	}

	public Category(long id, String name, int displayOrder, boolean deleted) {
		super();
		this.id = id;
		this.name = name;
		this.displayOrder = displayOrder;
		this.deleted = deleted;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	

}