package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.Entity.CartItems;

public interface cartItemsRepository extends JpaRepository<CartItems, Integer>{
	
	@Query("select c from CartItems c where c.product.pid= :pid and c.cart.user.id= :uid")
	public CartItems CartItem(@Param("pid")String pid,@Param("uid")int uid);

	@Query("select c from CartItems c where c.product.pid= :pid")
	public CartItems findCartItemsByPid(@Param("pid")String pid);
	
	@Modifying
	@Query("update CartItems c set c.quantity= :nq where c.product.pid= :pid")
	public void UpdateQuantity(@Param("nq")int quantity,@Param("pid")String pid);
}
