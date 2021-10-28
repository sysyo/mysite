package com.douzone.mysite.mvc.board;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.dao.BoardDAO;
import com.douzone.mysite.vo.BoardDTO;
import com.douzone.mysite.vo.PageVO;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.MvcUtil;

public class ListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<BoardDTO> list = new BoardDAO().findAll();

		request.setAttribute("list", list);

		// -------------------------------------------------

		// 페이징 처리
		
		int page = 0; // 현재 페이지
		
//		if()
		
		
		// 1. 페이징을 위한 기본 데이터 계산
		int totalCount = new BoardDAO().totalCount();
		
		
		
		
		
		
		
//		// 페이징 처리를 위한 변수 선언
//		int page = 1; // 현재 페이지 번호를 저장할 변수
//		int limit = 5; // 한 페이지에 표시할 게시물 갯수를 저장하는 변수
//
//		ArrayList<PageVO> articleList = new BoardDAO().getArticleList(page, limit);
//
//		// 파라미터 중 "page" 파라미터가 null이 아닐 경우 (전달될 경우)
//		// int 타입 변수 page에 해당 파라미터 값 저장
//		if (request.getParameter("page") != null) {
//			page = Integer.parseInt(request.getParameter("page"));
//		}

		// 계산된 페이지 정보를 PageInfo 객체에 저장
//		PageVO pageVO = new PageVO(pageLimit, listCount, maxPage, startPage, endPage);

		MvcUtil.forward("board/list", request, response);
	}

}
