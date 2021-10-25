package com.douzone.mysite.mvc.board;

import com.douzone.web.mvc.Action;
import com.douzone.web.mvc.ActionFactory;

public class BoardActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		Action action = null;
		
//		if("addform".equals(actionName)) {
//			action = new addFormAction();
//		} else if ("write".equals(actionName)) {
//			action = new addAction();
//		} else if ("view".equals(actionName)) {
//			action = new viewAction();
//		} else {
//			action = new ListAction();
//		}
		
		if("writeForm".equals(actionName)) {
			action = new WriteFormAction();
		} else if ("write".equals(actionName)) {
			action = new WriteAction();
		} else if ("delete".equals(actionName)) {
			action = new DeleteAction();
		} else if ("view".equals(actionName)) {
			action = new ViewAction();
		} else if ("modifyForm".equals(actionName)) {
			action = new ModifyFormAction();
		} else if ("modify".equals(actionName)) {
			action = new ModifyAction();
		} else if("replyForm".equals(actionName)) {
			action = new ReplyFormAction();
		} else if ("reply".equals(actionName)) {
			action = new ReplyAction();
		} else {
			action = new ListAction();
		}
		
		return action;
	}

}
