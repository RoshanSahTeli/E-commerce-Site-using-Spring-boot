package com.example.demo.Entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Brand {
	
	@Id
	private String bid;
	
	private String bname;
	
	private LocalDateTime createdAt;
	
	@ManyToOne
	@JoinColumn(name = "subId",referencedColumnName = "subId")
	@JsonIgnore
	public subCategory subCategory;

	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	public String getBname() {
		return bname;
	}

	public void setBname(String bname) {
		this.bname = bname;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public subCategory getCategory() {
		return subCategory;
	}

	public void setCategory(subCategory category) {
		this.subCategory = category;
	}

	public Brand(String bid, String bname, LocalDateTime createdAt, subCategory category) {
		super();
		this.bid = bid;
		this.bname = bname;
		this.createdAt = createdAt;
		this.subCategory = category;
	}

	public Brand() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
