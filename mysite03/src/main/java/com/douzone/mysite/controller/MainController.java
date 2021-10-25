package com.douzone.mysite.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	
	@RequestMapping({"", "/main"})
	public String index() {
		return "main/index";
	}
	
	
	@RequestMapping("/hello")
	public void message(HttpServletResponse resp) throws Exception {
		resp.setContentType("application/json; charset=UTF-8");
		resp.getWriter().print("{\"message\":\"Hello World\"}");
	}
}