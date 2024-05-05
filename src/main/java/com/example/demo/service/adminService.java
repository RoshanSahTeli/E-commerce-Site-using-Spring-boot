package com.example.demo.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Entity.User;
import com.example.demo.Entity.category;
import com.example.demo.Entity.product;
import com.example.demo.Entity.subCategory;
import com.example.demo.Repository.catRepository;
import com.example.demo.Repository.productRepository;
import com.example.demo.Repository.sunCatRepository;
import com.example.demo.Repository.userRepository;

@Component
public class adminService {
	
	@Autowired
	private userRepository urepo;
	
	@Autowired
	private catRepository crepo;
	
	@Autowired
	private productRepository prepo;
	
	@Autowired
	private sunCatRepository srepo;
	
	public User findByEmail(String email) {
		return urepo.findByEmail(email);
	}
	
	public void admin_register(User u,MultipartFile file) throws IllegalStateException, IOException {
		String folderPath="D:\\myspring\\ECOM\\src\\main\\resources\\static\\image\\";
		String cpath=folderPath+file.getOriginalFilename();
		String search="image\\";
		int i=cpath.indexOf(search);
		User user=new User();
		user.setImgPath(cpath.substring(i+search.length()));
		file.transferTo(new File(cpath));
		user.setAddress(u.getAddress());
		user.setEmail(u.getEmail());
		user.setPassword(new BCryptPasswordEncoder().encode(u.getPassword()));
		user.setPhone(u.getPhone());
		user.setName(u.getName());
		user.setRole("ADMIN");
		user.setGender(u.getGender());
		urepo.save(user);
		
	}
	
	public void add_category(String cname) {
		category c=new category();
		c.setCname(cname);
		crepo.save(c);
	}
	
	public List<category> getCategories(){
		return crepo.findAll();
	}
	
	public void add_product(product product,MultipartFile file,String sid) throws IllegalStateException, IOException {
		String folderPath="D:\\myspring\\ECOM\\src\\main\\resources\\static\\image\\";
		String nPath=folderPath+file.getOriginalFilename();
		String search="image\\";
		int i=nPath.indexOf(search);
		
		product p=new product();
		subCategory sc= srepo.findById(sid).get();
		List<product> lp=prepo.findProductBySubId(sid);
		
		//Generating ID
		//sproduct pro=prepo.findByPname(product.getPname());
		if(lp.isEmpty()) {
			p.setPid(sc.getSubName().replaceAll("\\s", "")+1);
			file.transferTo(new File(nPath));
		}
		else {
			product pp=prepo.findProductBySubIdOrderByCreatedAt(sid);
			String pid=pp.getPid();
			String new_name=sc.getSubName().replace(" ","");
			int l=new_name.length();
			int num=Integer.parseInt(pid.substring(l))+1;
			String ppid=new_name+num;
			p.setPid(ppid);
			file.transferTo(new File(nPath));
			
		}
		p.setSubCategory(sc);
		p.setImgPath(nPath.substring(i+search.length()));
		p.setPname(product.getPname());
		p.setBrand(product.getBrand());
		p.setCategory(product.getCategory());
		p.setDescription(product.getDescription());
		p.setPrice(product.getPrice());
		p.setAddDate(LocalDate.now());
		prepo.save(p);
		
		
	}
		public List<subCategory> subCategories(int cid){
			Optional<category> cop=crepo.findById(cid);
			category c=cop.get();
			return c.getSubCategory();
		}
		
		public void add_subCategory(String subName,int cid,MultipartFile file) throws IllegalStateException, IOException {
			String folderPath="D:\\myspring\\ECOM\\src\\main\\resources\\static\\image\\";
			String npath=folderPath+file.getOriginalFilename();
			String search="image\\";
			int i=npath.indexOf(search);
			subCategory s=new subCategory();
			category c=crepo.findById(cid).get();
			if(subCategories(cid).isEmpty()) {
				String new_name=c.getCname().replace(" ", "");
				String Id=new_name+1;
				s.setSubId(Id);
			}
			else {
				
				Optional<subCategory>os=srepo.findLatestSubCategoryByCategoryId(cid);
				String subID=os.get().getSubId();
				int l=c.getCname().length();
				int num=Integer.parseInt(subID.substring(l))+1;
				s.setSubId(c.getCname()+num);
			}
			file.transferTo(new File(npath));
			s.setImgPath(npath.substring(i+search.length()));
			s.setCreatedAt(LocalDateTime.now());
			s.setSubName(subName);
			s.setCategory(c);
			srepo.save(s);
		}
}
