package com.douzone.mysite.mvc.user;

import com.douzone.mysite.mvc.main.MainAction;
import com.douzone.web.mvc.Action;
import com.douzone.web.mvc.ActionFactory;

public class UserActionFactory extends ActionFactory {

	// USerActionFactory는 user 객체가 할 수 있는 액션들을 모아놓은 클래스로서
	// ActionFactory 를 상속 받는다.
	
	// 요청받은 Servlet 에서 액션을 if - else if - else 로 처리했던 로직이 있으며,
	// 적절한 액션을 찾아 요청하게 된다.
	
	@Override
	public Action getAction(String actionName) {
		Action action = null;
		
		if("joinform".equals(actionName)) {
			action = new JoinFormAction();
		} else if("join".equals(actionName)) {
			action = new JoinAction();
		} else if("joinsuccess".equals(actionName)) {
			action = new JoinSuccessAction();
		} else if("logout".equals(actionName)) {
			action = new LogoutAction();
		} else if("login".equals(actionName)) {
			action = new LoginAction();
		} else if("loginform".equals(actionName)) {
			action = new LoginFormAction();
		} else if("updateform".equals(actionName)) {
			action = new UpdateFormAction();
		} else if("update".equals(actionName)) {
			action = new UpdateAction();
		} else {
			action = new MainAction();
		}
		
		return action;
	}

}