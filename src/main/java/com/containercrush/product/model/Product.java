package com.containercrush.product.model;

import java.io.Serializable;
import java.lang.Integer;
import java.lang.String;
//import javax.persistence.*;

/**
 * Entity implementation class for Entity: Product
 *
 */
//@Entity

public class Product implements Serializable {

	   
	//@Id
	private Integer ItemNumber;
	private String Description;
	private String LongDescription;
	private String CatalogueName;
	private static final long serialVersionUID = 1L;

	public Product() {
		super();
	}   
	public Integer getItemNumber() {
		return this.ItemNumber;
	}

	public void setItemNumber(Integer ItemNumber) {
		this.ItemNumber = ItemNumber;
	}   
	public String getDescription() {
		return this.Description;
	}

	public void setDescription(String Description) {
		this.Description = Description;
	}   
	public String getLongDescription() {
		return this.LongDescription;
	}

	public void setLongDescription(String LongDescription) {
		this.LongDescription = LongDescription;
	}   
	public String getCatalogueName() {
		return this.CatalogueName;
	}

	public void setCatalogueName(String CatalogueName) {
		this.CatalogueName = CatalogueName;
	}
   
}
