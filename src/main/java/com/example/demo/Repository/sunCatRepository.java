package com.example.demo.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.Entity.subCategory;

public interface sunCatRepository extends JpaRepository<subCategory, String> {
	
	@Query("SELECT s FROM subCategory s WHERE s.category.cid = :categoryId ORDER BY s.createdAt DESC LIMIT 1")
	Optional<subCategory> findLatestSubCategoryByCategoryId(@Param("categoryId") int categoryId);
	subCategory findBySubName(String sname);
	
	@Query("select s from subCategory s where s.subName= :sname order by s.createdAt DESC LIMIT 1")
	subCategory findLatestSubCategoryBySubName(@Param("sname")String sname);

}
