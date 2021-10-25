package com.douzone.mysite.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.dao.BoardDAO;
import com.douzone.mysite.vo.BoardDTO;
import com.douzone.mysite.vo.UserVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.MvcUtil;

public class ModifyFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		// ------------------------ 접근 제어 -------------------------
		UserVo authUser = (UserVo) session.getAttribute("authUser");

		if (authUser == null) {
			MvcUtil.redirect(request.getContextPath(), request, response);
			return;
		}
		// -----------------------------------------------------------

		// 기존 글 가져오기
		Long no = Long.parseLong(request.getParameter("no"));
//		new BoardDAO().getBoard(no);
		new BoardDAO().findByNo(no);
		
		BoardDTO dto = new BoardDAO().getBoard(no);
		request.setAttribute("dto", dto);
		
		MvcUtil.forward("board/modify", request, response);

	}

}
