package com.example.demo.Controller;

import java.io.IOException;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Entity.User;
import com.example.demo.Entity.product;
import com.example.demo.service.adminService;
import com.example.demo.service.userService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller

@RequestMapping("/USER")
public class userController {

	@Autowired
	private adminService aservice;

	@Autowired
	private userService uservice;

	@GetMapping("/home")
	public String home(Model model, HttpSession session, Principal principal) {
		User user = aservice.findByEmail(principal.getName());
		session.setAttribute("user", user);
		if (user.getStatus() == "Unverified") {
			model.addAttribute("user", user);
			return "login";
		} else {
			model.addAttribute("user", user);
			session.setAttribute("user", user);
			model.addAttribute("categories", aservice.getCategories());
			session.setAttribute("categories", aservice.getCategories());
			return "user_home";
		}
	}

	@GetMapping("/subCategories/{id}")
	public String subCategories(@PathVariable("id") String subId, Model model, HttpSession session) {
		model.addAttribute("products", aservice.findProductFromSubCategory(subId));
		model.addAttribute("user", session.getAttribute("user"));
		model.addAttribute("categories", session.getAttribute("categories"));
		return "subCategories";
	}

	@GetMapping("/productOption/{id}")
	public String productOption(@PathVariable("id") String pid, Model model, HttpSession session) {
		model.addAttribute("user", session.getAttribute("user"));
		model.addAttribute("categories", session.getAttribute("categories"));
		model.addAttribute("color", session.getAttribute("color"));
		model.addAttribute("product", aservice.findProductById(pid));
		model.addAttribute("status", session.getAttribute("status"));
		session.removeAttribute("status");
		session.removeAttribute("color");
		return "productOption";
	}

	@PostMapping("/addToCart/{id}")
	public void addToCart(Model model, HttpSession session, HttpServletResponse response, @PathVariable("id") String id,
			@RequestParam("quantity") int quantity) throws IOException {

		// product p=aservice.findProductById(pid);
		User u = (User) session.getAttribute("user");
		if (uservice.getQuantity(id) < quantity) {
			// model.addAttribute("error", "Only" + uservice.getQuantity(id) + "items
			// available");s
			session.setAttribute("status", "Only " + uservice.getQuantity(id) + " items available !");
			model.addAttribute("user", session.getAttribute("user"));
			model.addAttribute("categories", session.getAttribute("categories"));
			session.setAttribute("color", true);
			response.sendRedirect("/USER/productOption/" + id);
		} else {
			uservice.addToCart(id, u.getEmail(), quantity);
			model.addAttribute("user", session.getAttribute("user"));
			model.addAttribute("categories", session.getAttribute("categories"));
			session.setAttribute("status", " Added To Cart Successfully !");
			session.setAttribute("color", false);
			response.sendRedirect("/USER/productOption/" + id);
		}

	}
}
