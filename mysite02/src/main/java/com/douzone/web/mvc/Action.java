package com.douzone.web.mvc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Action {
	public void execute(
			HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException;
	// excute() 는 액션을 실행하는 추상 메서드
	// 모든 Action 클래스는 Action interface를 구현해야 한다.
}
