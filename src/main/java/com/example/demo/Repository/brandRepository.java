package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.Entity.Brand;

public interface brandRepository extends JpaRepository<Brand, String> {
	@Query("select b from Brand b where b.subCategory.subId= :subId")
	public List<Brand> getBrands(@Param("subId")String subID);

}
