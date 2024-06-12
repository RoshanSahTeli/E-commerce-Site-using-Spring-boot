package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.Entity.CartItems;
import com.example.demo.Entity.cart;
import com.example.demo.Entity.product;
import com.example.demo.Repository.cartItemsRepository;
import com.example.demo.Repository.cartRepositoty;
import com.example.demo.Repository.productRepository;
import com.example.demo.Repository.userRepository;

import jakarta.transaction.Transactional;

@Component
public class userService {

	@Autowired
	private adminService aservice;

	@Autowired
	private productRepository prepo;

	@Autowired
	private userRepository urepo;

	@Autowired
	private cartRepositoty crepo;

	@Autowired
	private cartItemsRepository cirepo;

	@Transactional
	public void addToCart(String pid, String email, int quantity) {
		CartItems citems = new CartItems();
		product product = prepo.findById(pid).get();
		cart c = new cart();
		if (cirepo.CartItem(pid, urepo.findByEmail(email).getId()) == null) {
			citems.setProduct(product);
			citems.setQuantity(quantity);
			citems.setCart(c);
			c.setUser(urepo.findByEmail(email));
			crepo.save(c);
			cirepo.save(citems);
		}

		else {
			CartItems citem = cirepo.findCartItemsByPid(pid);
			cirepo.UpdateQuantity(citem.getQuantity()+quantity, pid);
		}

	}

	public int productCount(String id) {
		product p = prepo.findById(id).get();
		return prepo.findByPname(p.getPname()).size();
	}
	public int getQuantity(String pid) {
		return prepo.findById(pid).get().getQuantity();
	}

}
