package com.douzone.mysite.mvc.guestbook;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.dao.GuestbookDAO;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.MvcUtil;

public class DeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// getParameter는 String형만 받기 때문에 Long no를 위해서
		// Long type 으로 형변환 필요
		Long no = Long.parseLong(request.getParameter("no"));

		String password = request.getParameter("password");

		// GuestbookDAO에서 만든 delete 메서드 수행
		new GuestbookDAO().delete(no, password);

		// response.sendRedirect("/guestbook02/gb");

		 MvcUtil.redirect("/mysite02/guestbook?a=list", request, response);

//		MvcUtil.redirect(request.getContextPath() + "/guestbook", request, response);

	}

}
