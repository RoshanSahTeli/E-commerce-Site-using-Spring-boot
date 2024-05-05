package com.example.demo.Repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.category;

public interface catRepository extends JpaRepository<category, Integer> {
	
	

}
