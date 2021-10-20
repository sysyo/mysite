package com.douzone.mysite.mvc.guestbook;

import com.douzone.web.mvc.Action;
import com.douzone.web.mvc.ActionFactory;

public class GuestBookActionFactory extends ActionFactory {

	// GuestBookActionFactory는 객체가 할 수 있는 액션들을 모아 놓은 클래스로서
	// ActionFactory 를 상속 받는다.
	
	// 요청받은 Servlet 에서 액션을 if - else if - else 로 처리했던 로직이 있으며,
	// 적절한 액션을 찾아 요청하게 된다.
	
	@Override
	public Action getAction(String actionName) {
		Action action = null;

		if("add".equals(actionName)) {
			action = new WriteAction();
		} else if("deleteform".equals(actionName)) {
			action = new DeleteformAction();
		} else if("delete".equals(actionName)) {
			action = new DeleteAction();
		} else {
			action = new ListAction();
		}
		
		return action;
	}

}
