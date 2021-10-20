package com.douzone.web.mvc;

public abstract class ActionFactory {
	public abstract Action getAction(String actionName);
	
	// ActionFactory에는 Action 타입을 반환하는 getAction() 메서드가 있다.
	
}
