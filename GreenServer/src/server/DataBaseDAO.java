package server;

import java.time.LocalDate;
import java.util.List;

import shared.ChatRoom;

public class DataBaseDAO { // Data Access Object

//-----------------------------------------------------------------------------------------
	// 로그인 - 서윤
	public void searchLogin() {
		// 로그인 DB조회 (아이디.비번 불러오기)
	}
	
	public void searchId() {
		// ID찾기 DB조회 (이름,생년월일,전화번호 불러오기)
	}
	
	public void searchPw() {
		// Pw찾기 DB조회 (이름,생년월일,전화번호,ID 불러오기)
	}

	public void saveUser() {
		// DB에 저장 (회원가입시)
//		String query = "INSERT INTO user (id, isTeacher, pw, name, birth, phone, subjectCode) VALUES (?, ?, ?, ?, ?, ?, ?)";
//		try (Connection conn = ConnectionProvider.getConnection();
//				PreparedStatement stmt = conn.prepareStatement(query);) {
//			stmt.setInt(1, );
//			stmt.setString(2, );
//			stmt.setString(3, );
//			stmt.setInt(4, );
//			
//			return stmt.executeUpdate(); // 실행
//		} catch (SQLException e) {
//			e.printStackTrace();
//	return 0;
//		}
	}

//-----------------------------------------------------------------------------------------
	// 채팅방 만들기 - 형수
	public void creatRoom(int subjcetCode, LocalDate today) {
		// chat 테이블에 과목코드와 날짜, 방제목을 insert
	}

	public List<String> roomList() {
		return null;
		// isrunning true인 방들만 조회(title 들고오기)
	}

	public void chatLog() { // 세호 (
		// chat테이블에서 채팅로그 읽어서 String 으로 반환
	}

//-----------------------------------------------------------------------------------------
	// 채팅방 - 3명
	// 석현
	public void logUpdate() { // 영균,민주
		// 위의 메세지보내는 메소드 중에서 all메소드 안에만 심어서
		// DB의 채팅로그에 업데이트 하기
		// DB의 채팅로그에 (사진,파일) 업데이트 하기 - 영균
		// 유저 chat in out 공지 - 민주
	}

	public void logDownload() {// 데이터베이스
		// 새로운 참가자 있을때 현재까지 모은 로그 한번에 보기.
	}

	// 영균
	public void ChangeTitle() {// 데이터베이스 조작
		// DB의 채팅타이틀 변경
	}

//-----------------------------------------------------------------------------------------
	// 달력 - 세호
	public static List<ChatRoom> getChatLogList(String selectedDate) {
		// db에서 해당 날짜로 조회
		// 결과를 List<ChatRoom>으로 반환
		return null;
	}

//-----------------------------------------------------------------------------------------
	// 마이페이지 - 민석
	public void searchProfile() {
		// 유저 이름, 사진, 상메만 갖다주는 메소드(프로필 보기)
		// String query = "SELECT name, photo, myMessage from user where id = ?";
	}

	public void updateProfile() {
		// 마이프로필 수정누르고 확인 눌렀을 때 업데이트시키기
		String query = "UPDATE INTO user (pw, name, myMessage, phone, photo) VALUES (?, ?, ?, ?, ?)";
	}

}
