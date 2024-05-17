package com.example.demo.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Entity.Brand;
import com.example.demo.Entity.User;
import com.example.demo.Entity.category;
import com.example.demo.Entity.product;
import com.example.demo.Entity.subCategory;
import com.example.demo.Repository.brandRepository;
import com.example.demo.Repository.catRepository;
import com.example.demo.Repository.productRepository;
import com.example.demo.Repository.sunCatRepository;
import com.example.demo.Repository.userRepository;

import jakarta.transaction.Transactional;

@Component
public class adminService {
	@Autowired
	private JavaMailSender jms;
	
	@Autowired
	private userRepository urepo;
	
	@Autowired
	private catRepository crepo;
	
	@Autowired
	private productRepository prepo;
	
	@Autowired
	private sunCatRepository srepo;
	
	@Autowired 
	private brandRepository brepo;
	
	public User findByEmail(String email) {
		return urepo.findByEmail(email);
	}
	
	public void admin_register(User u,MultipartFile file,String role) throws IllegalStateException, IOException {
		User user=new User();
		if(file.isEmpty()) {
			user.setImgPath("profile.jpg");
		}
		else {
		String folderPath="C:\\Users\\rosha\\Desktop\\pro\\ECOM\\src\\main\\resources\\static\\image\\";
		String cpath=folderPath+file.getOriginalFilename();
		String search="image\\";
		int i=cpath.indexOf(search);
		user.setImgPath(cpath.substring(i+search.length()));
		file.transferTo(new File(cpath));}
		user.setAddress(u.getAddress());
		user.setEmail(u.getEmail());
		user.setPassword(new BCryptPasswordEncoder().encode(u.getPassword()));
		user.setPhone(u.getPhone());
		user.setName(u.getName());
		user.setRole(role);
		user.setStatus("Verified");
		urepo.save(user);
		
	}
	
	public void add_category(String cname,MultipartFile file) throws IllegalStateException, IOException {
		String folderPath="C:\\Users\\rosha\\Desktop\\pro\\ECOM\\src\\main\\resources\\static\\image\\";
		category c=new category();
		if(file.isEmpty()) {
			c.setImgPath("");
		}
		else {
		String npath=folderPath+file.getOriginalFilename();
		String search="image\\";
		int i=npath.indexOf(search);
		file.transferTo(new File(npath));
		c.setImgPath(npath.substring(i+search.length()));}
		
		c.setCname(cname);
		crepo.save(c);
	}
	
	
	@Transactional
	public void updateCategory(MultipartFile file,String cname,int cid) throws IllegalStateException, IOException {
		String folderPath="C:\\Users\\rosha\\Desktop\\pro\\ECOM\\src\\main\\resources\\static\\image\\";
		
		if(file.isEmpty()) {
			crepo.updateCategory(cname, "", cid);
		}
		else {
			String npath=folderPath+file.getOriginalFilename();
			String search="image\\";
			int i=npath.indexOf(search);
			file.transferTo(new File(npath));
			crepo.updateCategory(cname, npath.substring(i+search.length()), cid);
		}
		
	}
	
	
	@Transactional
	public void deleteCategory(int cid) {
		srepo.deleteByCid(cid);
		crepo.deleteById(cid);
	}
	
	public List<category> getCategories(){
		return crepo.findAll();
	}
	
	public void add_product(product product,MultipartFile file,String sid) throws IllegalStateException, IOException {
		String folderPath="C:\\Users\\rosha\\Desktop\\pro\\ECOM\\src\\main\\resources\\static\\image\\";
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
			subCategory s=new subCategory();
			category c=crepo.findById(cid).get();
			
			if(file.isEmpty()) {
				s.setImgPath("");
			}
			else {
				String folderPath="C:\\Users\\rosha\\Desktop\\pro\\ECOM\\src\\main\\resources\\static\\image\\";
				String npath=folderPath+file.getOriginalFilename();
				String search="image\\";
				int i=npath.indexOf(search);
				file.transferTo(new File(npath));
				s.setImgPath(npath.substring(i+search.length()));
			}
			
			
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
			s.setCreatedAt(LocalDateTime.now());
			s.setSubName(subName);
			s.setCategory(c);
			srepo.save(s);
		}
			
		
		public subCategory findSubCategory(String sid) {
			return srepo.findById(sid).get();
		}
		
		@Transactional
		public void updateSubCat(String sname,String id,MultipartFile file) throws IllegalStateException, IOException {
			if(file.isEmpty()) {
				srepo.updateSubCategory(sname, "", id);
			}
			else {
				String folderPath="C:\\Users\\rosha\\Desktop\\pro\\ECOM\\src\\main\\resources\\static\\image\\";
				String npath=folderPath+file.getOriginalFilename();
				String search="image\\";
				int i=npath.indexOf(search);
				file.transferTo(new File(npath));
			srepo.updateSubCategory(sname, (String)npath.substring(i+search.length()),id );}
			
		}
		
		public List<Brand> getBrands(String subId){
			return brepo.getBrands(subId);
		}
		
		public int findCatFromSubCat(String sid) {
			subCategory s=srepo.findById(sid).get();
			return s.getCategory().getCid();
		}
		
		public void deleteSubById(String subId) {
			srepo.deleteById(subId);
		}
		
		public subCategory findSubCategoryBySubName(String sname) {
			return srepo.findSubCategoryBySubName(sname);
		}
		
		public category findCategoryById(int id) {
			return crepo.findById(id).get();
		}
		
		public List<User> findByStatus(String status) {
			return urepo.findByStatus(status);
		}
		
		public void mail(String to,String body,String subject) {
			SimpleMailMessage message=new SimpleMailMessage();
			message.setFrom("roshanshah920@gmail.com");
			message.setTo(to);
			message.setText(body);
			message.setSubject(subject);
			jms.send(message);
			
		}
		
		
		@Transactional
		public void verify(String status,int id) {
			urepo.verify(status, id);
		}
		
		public User findById(int id) {
			return urepo.findById(id).get();
		}
		public void reject(int id) {
			urepo.deleteById(id);
		}
		
		public List<User> getAllUsers(String role){
			return urepo.findByRole(role);
		}
		public User findUserById(int id) {
			return urepo.findById(id).get();
		}
		@Transactional
		public void updateUser(String name,String address,String email,String phone,int id) {
			urepo.updateUser(name, address, email, phone, id);
		}
		
		public void deleteUser(int id) {
			urepo.deleteById(id);
		}
}
