package com.example.demo.Entity;


import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class product {
	
	@Id
	private String pid;
	
	private String pname;
	
	private String description;
	
	private String	brand;
	
	private String imgPath;
	
	private String price;
	
	private String category;
	
	private LocalDate addDate;
	
	
	
	
	@ManyToOne
	@JoinColumn(name="subId")
	private subCategory subCategory;
	
	@ManyToOne
	@JoinColumn(name = "uid")
	private User u;
	
	

	

	public product(String pid, String pname, String description, String brand, String imgPath, String price,
			String category, LocalDate addDate, com.example.demo.Entity.subCategory subCategory, User u) {
		super();
		this.pid = pid;
		this.pname = pname;
		this.description = description;
		this.brand = brand;
		this.imgPath = imgPath;
		this.price = price;
		this.category = category;
		this.addDate = addDate;
		this.subCategory = subCategory;
		this.u = u;
	}





	public String getPid() {
		return pid;
	}





	public void setPid(String pid) {
		this.pid = pid;
	}





	public String getPname() {
		return pname;
	}





	public void setPname(String pname) {
		this.pname = pname;
	}





	public String getDescription() {
		return description;
	}





	public void setDescription(String description) {
		this.description = description;
	}





	public String getBrand() {
		return brand;
	}





	public void setBrand(String brand) {
		this.brand = brand;
	}





	public String getImgPath() {
		return imgPath;
	}





	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}





	public String getPrice() {
		return price;
	}





	public void setPrice(String price) {
		this.price = price;
	}





	public String getCategory() {
		return category;
	}





	public void setCategory(String category) {
		this.category = category;
	}





	public LocalDate getAddDate() {
		return addDate;
	}





	public void setAddDate(LocalDate addDate) {
		this.addDate = addDate;
	}





	public subCategory getSubCategory() {
		return subCategory;
	}





	public void setSubCategory(subCategory subCategory) {
		this.subCategory = subCategory;
	}





	public User getU() {
		return u;
	}





	public void setU(User u) {
		this.u = u;
	}





	public product() {
		super();
		// TODO Auto-generated constructor stub
	}
	


	
}
