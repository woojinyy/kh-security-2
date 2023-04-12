package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.User;

//@RestController//String으로 빠진다
@Controller
public class HomeController {
	Logger logger = LogManager.getLogger(HomeController.class);
	@GetMapping("/")
	public  String index(HttpServletRequest req) {
		logger.info("index");
		logger.info("admin호출"+req.isUserInRole("ROLE_ADMIN"));
		logger.info("user호출"+req.isUserInRole("ROLE_USER"));
		logger.info("manager호출"+req.isUserInRole("ROLE_MANAGER"));
		if(req.isUserInRole("ROLE_ADMIN")) {
			return "forward:admin-index.jsp";
		}
		else if(req.isUserInRole("ROLE_USER")) {
			return "forward:user-index.jsp";
		}
		else {
			return "forward:index.jsp";
		}
	}
	@GetMapping("/loginForm")
	public  String loginForm() {
		logger.info("loginForm");
		return "redirect:/auth/loginForm.jsp";
	}
	//화면을 부른는 액션
	@GetMapping("/joinForm")
	public  String joinForm() {
		logger.info("joinForm");
		return "redirect:/auth/joinForm.jsp";
	}
	//회원가입
	@PostMapping("/join")
	public  String join(User user) {
		user.setRole("ROLE_USER");
		return "redirect:/auth/loginForm.jsp";
	}
	@GetMapping("/user")
	public @ResponseBody String user() {
		logger.info("user");
		return "user";
	}
	
	@GetMapping("/admin")
	public @ResponseBody String admin() {
		logger.info("admin");
		return "admin";
	}
	@GetMapping("/manager")
	public @ResponseBody String manager() {
		logger.info("manager");
		return "manager";
	}
	@GetMapping("/auth")
	public @ResponseBody Authentication auth() {
		return SecurityContextHolder.getContext().getAuthentication();//sessionscope 저장소
	}
	
	/*내부적 로그인 처리(/login)를  security가 intercept해서 대신 로그인을 진행해주는 컨셉을 위해 주석처리
	 * @GetMapping("/login") public @ResponseBody String login() {
	 * logger.info("login"); 
	 * return "로그인 된 페이지"; }
	 */
}


