package com.arjinmc.openexpress.model;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="deliver")
public class DeliverBean implements Serializable{
	
	@DatabaseField (index = true,generatedId = true)
	private int id;
	@DatabaseField(canBeNull = true)
	private String name;
	@DatabaseField(canBeNull = true)
	private String phone;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
}
