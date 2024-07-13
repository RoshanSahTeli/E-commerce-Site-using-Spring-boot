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
		cart c = crepo.findUserCart(urepo.findByEmail(email).getId());
		if(c==null) {
			c=new cart();
			c.setUser(urepo.findByEmail(email));
			crepo.save(c);
		}
		double total=0;
		if (cirepo.CartItem(pid, urepo.findByEmail(email).getId()) == null) {
			citems.setProduct(product);
			citems.setQuantity(quantity);
			citems.setCart(c);
			citems.setTotal((double)Integer.parseInt(product.getPrice())*quantity);
			cirepo.save(citems);
			for(CartItems ci : findCartItems(email)) {
				total=total+ci.getTotal();
			}
			c.setSubTotal(total);
		}

		else {
			CartItems citem = cirepo.findCartItemsByPid(pid);
			citem.setQuantity(citem.getQuantity()+quantity);
			citem.setTotal(citem.getTotal()+( Integer.parseInt(product.getPrice())*quantity));
			cirepo.save(citem);
			double t=0;
			for(CartItems ci : findCartItems(email)) {
				t=t+ci.getTotal();
			}
			c.setSubTotal(t);

		}

	}

	public int productCount(String id) {
		product p = prepo.findById(id).get();
		return prepo.findByPname(p.getPname()).size();
	}
	public int getQuantity(String pid) {
		return prepo.findById(pid).get().getQuantity();
	}
	
	public List<CartItems> findCartItems(String email){
		return cirepo.findCartItems(urepo.findByEmail(email).getId());
	}
	
	@Transactional
	public void removeItemFromCart(int id,String email) {
		crepo.updateSubTotal(cirepo.findById(id).get().getCart().getSubTotal()-cirepo.findById(id).get().getTotal(),cirepo.findById(id).get().getCart().getUser().getId());
		 cirepo.deleteById(id);
	}
	
	public cart findCart(String email) {
		return crepo.findById(urepo.findByEmail(email).getCart().getCart_id()).get();
	}
}
