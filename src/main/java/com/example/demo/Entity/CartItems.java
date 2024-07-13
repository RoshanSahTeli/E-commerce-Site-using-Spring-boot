package com.example.demo.Entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class CartItems {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int citem_id;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH })
	@JoinColumn(name = "cart_id")
	private cart cart;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH })
	@JoinColumn(name = "pid")
	private product product;
	
	private int quantity;
	
	private double total;
	
	

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public int getCitem_id() {
		return citem_id;
	}

	public void setCitem_id(int citem_id) {
		this.citem_id = citem_id;
	}

	public cart getCart() {
		return cart;
	}

	public void setCart(cart cart) {
		this.cart = cart;
	}

	public product getProduct() {
		return product;
	}

	public void setProduct(product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public CartItems(int citem_id, com.example.demo.Entity.cart cart, com.example.demo.Entity.product product,
			int quantity,double total) {
		super();
		this.citem_id = citem_id;
		this.total=total;
		this.cart = cart;
		this.product = product;
		this.quantity = quantity;
	}

	public CartItems() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
