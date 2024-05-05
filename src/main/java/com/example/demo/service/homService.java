package com.example.demo.service;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Entity.User;
import com.example.demo.Repository.userRepository;

@Component
public class homService {
	
	@Autowired
	private userRepository urepo;
	
	public void register(User u,MultipartFile file) throws IllegalStateException, IOException {
		String folderPath="D:\\myspring\\ECOM\\src\\main\\resources\\static\\image\\";
		String nPath=folderPath+file.getOriginalFilename();
		String search="image\\";
		int i=nPath.indexOf(search);
		file.transferTo(new File(nPath));
		User user=new User();
		user.setImgPath(nPath.substring(i+search.length()));
		user.setName(u.getName());
		user.setAddress(u.getAddress());
		user.setEmail(u.getEmail());
		user.setPassword(new BCryptPasswordEncoder().encode(u.getPassword()));
		user.setPhone(u.getPhone());
		user.setRole("USER");
		urepo.save(user)
;	}

}