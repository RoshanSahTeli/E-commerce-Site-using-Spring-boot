package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.Entity.cart;

public interface cartRepositoty extends JpaRepository<cart, Integer> {
	
	
	@Modifying
	@Query("update cart c set c.subTotal= :total where c.user.id= :id")
	public void updateSubTotal(@Param("total")double total,@Param("id") int id);
	
	@Query("select c from cart c where c.user.id= :id")
	public cart findUserCart(@Param("id")int id);

}
