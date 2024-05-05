package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.Entity.product;


public interface productRepository extends JpaRepository<product, String> {
	
	product findByPname(String pname);
	
	public product findTopByPnameOrderByAddDateDesc(String name);
	
	@Query("select s from product s where s.subCategory.subId= :sid")
	public List<product> findProductBySubId(@Param("sid")String sid);
	
	@Query("select p from product p where p.subCategory.subId= :sid order by p.addDate DESC LIMIT 1")
	
	product findProductBySubIdOrderByCreatedAt(@Param("sid")String sid);

}
