package com.douzone.mysite.mvc.guestbook;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.dao.GuestbookDAO;
import com.douzone.mysite.vo.GuestbookVO;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.MvcUtil;

public class WriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String message = request.getParameter("message");
		
		GuestbookVO vo = new GuestbookVO();
		vo.setName(name);
		vo.setPassword(password);
		vo.setMessage(message);
		
		new GuestbookDAO().insert(vo);
		
		// 둘 다 됨
		MvcUtil.redirect(request.getContextPath() + "/guestbook", request, response);
//	    MvcUtil.redirect("/mysite02/guestbook?a=list", request, response);

	}

}