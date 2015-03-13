package com.arjinmc.openexpress.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable (tableName="records")
public class ExpressRecordBean {
	
	@DatabaseField (index = true,generatedId = true)
	private int id;
	
	@DatabaseField (canBeNull = false)
	private String companyCode;
	
	@DatabaseField (canBeNull = false)
	private String billCode;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getBillCode() {
		return billCode;
	}

	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}

}
