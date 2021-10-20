package com.douzone.mysite.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.mvc.guestbook.GuestBookActionFactory;
import com.douzone.web.mvc.Action;
import com.douzone.web.mvc.ActionFactory;

public class GuestbookController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");

		String actionName = request.getParameter("a");
		
		// Servlet에서는 자신의 ActionFactory인 GuestBookActionFactory 객체를 생성하여
		// 적절한 Action 타입의 객체를 반환한다.
		ActionFactory af = new GuestBookActionFactory();
		Action action = af.getAction(actionName);
		
		/* 반환된 객체에서 수행해야 할 로직을 실행하는 execute() 메서드만 호출하면
		*  Servlet 클래스 내에서 할 일은 끝난 것
		*  - excute() 메서드를 호출하면 getAction()을 통해 반환된 그 객체에서 수행할 
		*    로직이 담겨있다.
		*  - 즉, 요청을 골라내는 작업은 ActionFactory에서 수행하고,
		*    실제로 수행하는 로직은 각 Action에서 구현하도록 하는 구조이다.
		*/
		action.execute(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
