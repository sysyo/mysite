package com.douzone.mysite.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douzone.mysite.repository.SiteRepository;
import com.douzone.mysite.service.SiteService;
import com.douzone.mysite.vo.SiteVo;
import com.douzone.mysite.vo.UserVo;

@Controller
public class MainController {
	@Autowired
	private SiteService siteService;
	@RequestMapping({"", "/main"})
	public String index(Model model) {
		SiteVo siteVo = siteService.getSite();
		model.addAttribute("site", siteVo);
		
		return "main/index";
	}	
	
	@ResponseBody
	@RequestMapping("/msg01")
	public String message01() {
		return "안녕";
	}
	
	@ResponseBody
	@RequestMapping("/msg02")
	public Object message02(/*HttpServletResponse resp*/) throws Exception {
		//resp.setContentType("application/json; charset=UTF-8");
		//resp.getWriter().print("{\"message\":\"Hello World\"}");
		
		Map<String, Object> map = new HashMap<>();
		map.put("message", "Hello World");
		
		return map;
	}
	@ResponseBody
	@RequestMapping("/json")
	public UserVo m() {
		System.out.println("asdasdasdasdas");
		return new UserVo();
	}
	@ResponseBody
	@RequestMapping("/string")
	public String m1() {
		return "<h1>hello</h1>";
	}
}
