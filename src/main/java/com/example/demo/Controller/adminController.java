package com.example.demo.Controller;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Entity.User;
import com.example.demo.Entity.product;
import com.example.demo.Entity.subCategory;
import com.example.demo.service.adminService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/ADMIN")
public class adminController {
	
	@Autowired
	private adminService aservice;
	
	@GetMapping("/home")
	public String home(Model model,Principal principal,HttpSession session) {
		String email=principal.getName();
		model.addAttribute("user",aservice.findByEmail(email));
		session.setAttribute("user", aservice.findByEmail(email));
		session.setAttribute("categories", aservice.getCategories());
		model.addAttribute("categories", aservice.getCategories());
		return "admin_home";
	}
	
	@GetMapping("/add_admin_form")
	public String add_admin_form(HttpSession session,Model model) {
		model.addAttribute("user", session.getAttribute("user"));
		return "add_admin_form";	
	}
	
	@PostMapping("/register_admin")
	public void register_admin(HttpServletResponse response,@ModelAttribute User user,
			@RequestParam("image")MultipartFile file) throws IOException {
		System.out.println(file.getOriginalFilename());
			aservice.admin_register(user, file);
			response.sendRedirect("/ADMIN/add_admin_form");
	}
	
	@GetMapping("/add_product_form")
	public String add_product_form(HttpSession session,Model model) {
		model.addAttribute("user", session.getAttribute("user"));
		List<com.example.demo.Entity.category> clist=aservice.getCategories();
		model.addAttribute("clist", clist);
		return "add_product_form";
	}
	@GetMapping("/subCategories")
	@ResponseBody
	public List<subCategory> getsubCat(@RequestParam("categoryId") int cid,Model model){
		
		return  aservice.subCategories(cid);
		
	}
	
	@PostMapping("/add_product")
	public void add_product(Model model,HttpSession session,@ModelAttribute product p, @RequestParam("image") MultipartFile file,
			@RequestParam("subCategory")String sid,
			HttpServletResponse response) throws IllegalStateException, IOException {
		System.out.println(sid);
		aservice.add_product(p, file,sid);
		response.sendRedirect("/ADMIN/add_product_form");
	}
	
	@GetMapping("/category")
	public String category( Model model,HttpSession session) {
		model.addAttribute("user", session.getAttribute("user"));
		List<com.example.demo.Entity.category> list=aservice.getCategories();
		model.addAttribute("categories", list);
		return "category";
		
		
	}
	
	@PostMapping("/add_category")
	public void add_category(Model model,HttpSession session,@RequestParam("cname")String cname,HttpServletResponse response) throws IOException
	{
		aservice.add_category(cname);
		model.addAttribute("user", session.getAttribute("user"));
		response.sendRedirect("/ADMIN/category");
		
	}
	
	@GetMapping("/sub_category/{id}")
	public String sub_category(@PathVariable("id")int id,  Model model,HttpSession session) {
		model.addAttribute("user", session.getAttribute("user"));
		List<subCategory> slist=aservice.subCategories(id);
		model.addAttribute("subCategory", slist);
		session.setAttribute("cid", id);
		return "sub_category";
	}
	
	

	@PostMapping("/add_subCategory")
	public void add_subCategory(Model model,HttpSession session,HttpServletResponse response,
			@RequestParam("cname")String subName, @RequestParam("image")  MultipartFile file) throws IOException {
		if(aservice.findSubCategoryBySubName(subName)==null) {
			int cid=(int) session.getAttribute("cid");
			 aservice.add_subCategory(subName, cid,file);
			 response.sendRedirect("/ADMIN/sub_category/"+cid);
		
		}
		else {
				
			throw new FileAlreadyExistsException(subName);}
	}
	
	@GetMapping("/viewSubCategory/{id}")
	public String viewSubCategory(@PathVariable("id")String id,Model model,HttpSession session) {
		model.addAttribute("user", session.getAttribute("user"));
		return "viewSubCategory";
	}
	
	@GetMapping("/update_subCat/{id}")
	public String updateCat(@PathVariable("id")String id,Model model,HttpSession session) {
		subCategory sc=aservice.findSubCategory(id);
		model.addAttribute("sub", sc);
		model.addAttribute("user", session.getAttribute("user"));
		return "updateCatForm";
	}

	@PostMapping("/updateSub/{id}")
	public void updateSub(@PathVariable("id")String id, @RequestParam("image")MultipartFile file, @RequestParam("name") String sname, Model model,HttpSession session,HttpServletResponse response) throws IllegalStateException, IOException {
		
		
		aservice.updateSubCat(sname, id, file);
		int cid=aservice.findCatFromSubCat(id);
		model.addAttribute("user", session.getAttribute("user"));
		//model.addAttribute(", response)
		response.sendRedirect("/ADMIN/sub_category/"+cid);
		
	}
	
	@GetMapping("/delete_subCat/{id}")
	public void deleteSubCat(@PathVariable("id")String id,Model model,HttpSession session,
			HttpServletResponse response) throws IOException 
		{
		int cid=aservice.findCatFromSubCat(id);
		aservice.deleteSubById(id);
		response.sendRedirect("/ADMIN/sub_category/"+cid);
		
	}
	
	
	
	
	
	
}