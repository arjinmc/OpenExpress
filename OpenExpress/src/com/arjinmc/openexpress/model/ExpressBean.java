package com.arjinmc.openexpress.model;

import java.io.Serializable;

/**
 * @desciption express 
 * @author eminem
 * @email eminem@hicsg.com
 * @website arjinmc.com
 */
public class ExpressBean implements Serializable{

	private String name;
	
	private String number;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
}
