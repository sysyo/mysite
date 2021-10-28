package com.douzone.mysite.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.douzone.mysite.vo.BoardDTO;
import com.douzone.mysite.vo.BoardVO;
import com.douzone.mysite.vo.PageVO;

public class BoardDAO {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	// ---------------- 게시판 목록 조회 ----------------
	public List<BoardDTO> findAll() {
		List<BoardDTO> list = new ArrayList<>();

		try {
			conn = getConnection();

			String sql = "SELECT b.no, b.title, b.contents, b.hit, "
					+ "date_format(b.reg_date,'%Y-%m-%d %h:%i:%s') AS reg_date, "
					+ "b.group_no, b.order_no, b.depth, b.user_no, b.delete_check, u.name, u.password "
					+ "FROM board b, user u WHERE b.user_no = u.no " + "ORDER BY b.group_no DESC, b.order_no ASC;";

			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				Long hit = rs.getLong(4);
				Date regDate = rs.getDate(5);
				Long groupNo = rs.getLong(6);
				Long orderNo = rs.getLong(7);
				Long depth = rs.getLong(8);
				Long userNo = rs.getLong(9);
				Boolean deleteCheck = rs.getBoolean(10);
				String userName = rs.getString(11);
				String userPassword = rs.getString(12);

				BoardDTO dto = new BoardDTO();
				dto.setNo(no);
				dto.setTitle(title);
				dto.setContents(contents);
				dto.setHit(hit);
				dto.setRegDate(regDate);
				dto.setGroupNo(groupNo);
				dto.setOrderNo(orderNo);
				dto.setDepth(depth);
				dto.setUserNo(userNo);
				dto.setDeleteCheck(deleteCheck);
				dto.setUserName(userName);
				dto.setPassword(userPassword);

				list.add(dto);
			}

		} catch (SQLException e) {
			System.out.println("BoardDAO - findAll() error:" + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return list;
	}

	// ---------------- 게시판 글쓰기 ----------------
	public boolean write(BoardVO vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();

			String sql = "INSERT INTO board VALUES(null, ?, ?, 0, now(), "
					+ "(SELECT IFNULL(MAX(group_no) + 1, 1) FROM board b), 0, 0, ?, 0)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getUserNo());

			int count = pstmt.executeUpdate();
			result = count == 1;

		} catch (SQLException e) {
			System.out.println("BoardDAO - BoardVO() error:" + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	// ---------------- 게시판 글 삭제 ----------------
	public boolean delete(Long no) {
		boolean result = false;
		try {
			conn = getConnection();
//			String sql = "DELETE FROM board WHERE no=?";
			// deleteCheck를 update로 바꾸기
			String sql = "UPDATE board SET delete_check=1 WHERE no=?";
			// "UPDATE

			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, no);

			int count = pstmt.executeUpdate();
			result = count == 1;

		} catch (SQLException e) {
			System.out.println("BoardDAO - delte() error:" + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	// ---------------- 해당 게시글 보기 (modifyForm 에서도 활용) ----------------
	public BoardDTO getBoard(Long no) {
		BoardDTO dto = new BoardDTO();

		try {
			conn = getConnection();

			String sql = "SELECT no, title, contents, user_no FROM board WHERE no=?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				dto.setNo(rs.getLong(1));
				dto.setTitle(rs.getString(2));
				dto.setContents(rs.getString(3));
				dto.setUserNo(rs.getLong(4));
			}

		} catch (SQLException e) {
			System.out.println("BoardDAO - getBoard() error:" + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return dto;

	}

	// ---------------- 게시판 글 수정 ----------------
	public boolean modify(BoardDTO dto) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();

			String sql = "UPDATE board SET title=?, contents=? WHERE no=?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, dto.getTitle());
			pstmt.setString(2, dto.getContents());
			pstmt.setLong(3, dto.getNo());

			int count = pstmt.executeUpdate();
			result = count == 1;

		} catch (SQLException e) {
			System.out.println("BoardDAO - modify() error:" + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	// ---------------- 댓글 작성 ----------------
	public boolean replyWrite(BoardDTO dto) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			// 기존 글 group_no, order_no, depth 값 찾기
			String selectSql = "SELECT group_no, order_no, depth " + "FROM board WHERE no=?";
			pstmt = conn.prepareStatement(selectSql);

			pstmt.setLong(1, dto.getNo());

			rs = pstmt.executeQuery();

			while (rs.next()) {

				// 기존 글의 댓글(자식) sql 처리 - dept 1로 셋팅
				String sql = "INSERT INTO board (no, title, contents, hit, reg_date, group_no, order_no, depth, user_no) "
						+ "VALUES(null, ?, ?, 0, now(), ?, 1, ?, ?)";
				pstmt = conn.prepareStatement(sql);

				pstmt.setString(1, dto.getTitle());
				pstmt.setString(2, dto.getContents());
				pstmt.setLong(3, rs.getLong(1)); // gruop_no
				pstmt.setLong(4, rs.getLong(3) + 1); // depth
				pstmt.setLong(5, dto.getUserNo());

				int count = pstmt.executeUpdate();
				result = count == 1;
			}
		} catch (SQLException e) {
			System.out.println("BoardDAO - replyWrite() error:" + e);
		}
		return result;

	}

	// ---------------- 댓글 작성 시 기존(부모) 글 order_no 올리기----------------
	public void updateBoard(BoardDTO dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			// 기존 글 group_no 값 찾기
			String selectSql = "SELECT group_no " + "FROM board WHERE no=?";
			pstmt = conn.prepareStatement(selectSql);

			pstmt.setLong(1, dto.getNo());

			rs = pstmt.executeQuery();

			while (rs.next()) {

				String updateSql = "UPDATE board " + "SET order_no = order_no +1 " + "WHERE group_no=? "
						+ "AND order_no>=1";
				pstmt = conn.prepareStatement(updateSql);
				pstmt.setLong(1, rs.getLong(1));

				pstmt.executeUpdate();

			}
		} catch (SQLException e) {
			System.out.println("BoardDAO - updateBoard() error:" + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	// ---------------- 조회수 ----------------
	public boolean upHit(Long no) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();

			String sql = "UPDATE board SET hit=hit+1 WHERE no=?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, no);

			int count = pstmt.executeUpdate();
			result = count == 1;

		} catch (SQLException e) {
			System.out.println("upHit() error:" + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	// ---------------- 게시판 글에 대한 user 정보 ----------------
	public BoardDTO findByNo(Long no) {
		BoardDTO dto = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			String sql = "SELECT b.no, b.title, b.contents, b.hit, "
					+ "date_format(b.reg_date,'%Y-%m-%d %h:%i:%s') AS reg_date, "
					+ "b.group_no, b.order_no, b.depth, b.user_no, u.name, u.password " + "FROM board b, user u "
					+ "WHERE b.user_no = u.no " + "AND b.no=?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, no);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new BoardDTO();

				dto.setNo(rs.getLong(1));
				dto.setTitle(rs.getString(2));
				dto.setContents(rs.getString(3));
				dto.setHit(rs.getLong(4));
				dto.setRegDate(rs.getDate(5));
				dto.setGroupNo(rs.getLong(6));
				dto.setOrderNo(rs.getLong(7));
				dto.setDepth(rs.getLong(8));
				dto.setUserNo(rs.getLong(9));
				dto.setUserName(rs.getString(10));
				dto.setPassword(rs.getString(11));
			}
		} catch (SQLException e) {
			System.out.println("BoardDAO - findByNo() error:" + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return dto;
	}

	// ---------------- 페이징 처리 ----------------
	public int totalCount() {
		int count = 0;

		try {
			Connection conn = getConnection();

			String sql = "SELECT count(*) FROM board";
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				count = rs.getInt(1);

			}

		} catch (SQLException e) {
			System.out.println("BoardDAO - totalCount() error:" + e);
		}

		return count;

	}

	private Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			String url = "jdbc:mysql://127.0.0.1:3306/webdb?characterEncoding=utf8";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		}

		return conn;
	}

}
