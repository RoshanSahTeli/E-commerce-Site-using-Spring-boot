package com.example.demo.Entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class cart {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int cart_id;

	@OneToOne
	@JoinColumn(name = "id")
	@JsonIgnore
	public User user;
	
	private double subTotal;
	
	
	
	public double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}

	@OneToMany(cascade = CascadeType.ALL,orphanRemoval = true,mappedBy = "cart")
	//	@JoinColumn(name = "citem_id")
	private List<CartItems> citems=new ArrayList<>();

	public int getCart_id() {
		return cart_id;
	}

	public void setCart_id(int cart_id) {
		this.cart_id = cart_id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	

	public List<CartItems> getCitems() {
		return citems;
	}

	public void setCitems(List<CartItems> citems) {
		this.citems = citems;
	}

	public cart(int cart_id, User user, List<CartItems> citems,double subTotal) {
		super();
		this.cart_id = cart_id;
		this.subTotal=subTotal;
		this.user = user;
		this.citems=citems;
		
	}

	public cart() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

//	@ManyToMany(mappedBy = "cart",cascade = CascadeType.ALL)
//	//@JsonIgnore
//	public List<product> products;

}
