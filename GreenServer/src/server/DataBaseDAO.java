package server;

import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import shared.ChatMessage;
import shared.ChatRoom;
import shared.GreenProtocol;
import shared.User;

public class DataBaseDAO { // Data Access Object
	private int id;
	private Connection conn;

	public DataBaseDAO() {
		try {
			conn = ConnectionProvider.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void closeConn() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

//-----------------------------------------------------------------------------------------
	// 로그인 - 서윤
	public User searchLogin(User user) {
		// 로그인 DB조회 (아이디.비번 불러오기)
		User okuser = new User();
		String query = "SELECT * FROM user WHERE id = ? AND pw = ?";
		try (PreparedStatement stmt = conn.prepareStatement(query);) {
			stmt.setInt(1, user.getId());
			stmt.setString(2, user.getPassword());

			ResultSet rs = stmt.executeQuery(); // 조회된 값들을 행으로 가져옴

			// 로그인 성공하면
			if (rs.next()) { // next() 컬럼 다음 행을 불러온다.(값)
				if (user.getId() == 1000000 && user.getPassword().equals("admin#")) {
					okuser.setProtocol(GreenProtocol.LOGIN_ADMIN);
					okuser.setId(rs.getInt("id"));
					okuser.setPassword(rs.getString("pw"));
					okuser.setName(rs.getString("name"));
					okuser.setBirth(rs.getString("birth"));
					okuser.setPhone(rs.getString("phone"));
					okuser.setMyMessage(rs.getString("myMessage"));
					okuser.setSubject(rs.getInt("subjectCode"));
					Blob blob = rs.getBlob("photo");
					byte[] photo = blob.getBytes(1, (int) blob.length());
					okuser.setPhoto(photo);
					okuser.setTeacher(rs.getBoolean("isTeacher"));
					return okuser;
				} else {
					okuser.setProtocol(GreenProtocol.LOGIN_OK);
					okuser.setId(rs.getInt("id"));
					okuser.setPassword(rs.getString("pw"));
					okuser.setName(rs.getString("name"));
					okuser.setBirth(rs.getString("birth"));
					okuser.setPhone(rs.getString("phone"));
					okuser.setMyMessage(rs.getString("myMessage"));
					okuser.setSubject(rs.getInt("subjectCode"));
					Blob blob = rs.getBlob("photo");
					byte[] photo = blob.getBytes(1, (int) blob.length());
					okuser.setPhoto(photo);
					okuser.setTeacher(rs.getBoolean("isTeacher"));
					return okuser;
				}
			} else {
				okuser.setProtocol(GreenProtocol.LOGIN_NO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return okuser;
	}

	public List<User> searchTeacher() {
		List<User> list = new ArrayList<>();
		String query = "SELECT id, name, subjectCode from user WHERE isTeacher = 1";
		try (PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				User user = new User();
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int subjectCode = rs.getInt("subjectCode");
				user.setId(id);
				user.setName(name);
				user.setSubject(subjectCode);

				list.add(user);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public int deleteTeacher(User user) {
		String query = "DELETE FROM user WHERE id = ?";
		try (PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setInt(1, user.getId());

			int result = stmt.executeUpdate();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int searchId(String name, String birth, String phone) {
		int wantId = 0;
		ResultSet rs = null;
		String query = "SELECT id FROM user WHERE name = ? AND birth = ? AND phone = ?";

		try (PreparedStatement stmt = conn.prepareStatement(query);) {
			stmt.setString(1, name);
			stmt.setString(2, birth);
			stmt.setString(3, phone);
			rs = stmt.executeQuery();
			while (rs.next()) {
				wantId = rs.getInt("id");
			}
			return wantId;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public String searchPw(String name, String birth, String phone, int id) {
		String wantPw = "1"; // 리턴될 값
		// Pw찾기 DB조회 (이름,생년월일,전화번호,ID 불러오기)
		ResultSet rs = null;
		String query = "SELECT pw FROM user WHERE name = ? AND birth = ? AND phone = ? AND id = ?";
		try (PreparedStatement stmt = conn.prepareStatement(query);) {
			stmt.setString(1, name);
			stmt.setString(2, birth);
			stmt.setString(3, phone);
			stmt.setInt(4, id);
			rs = stmt.executeQuery();
			while (rs.next()) { // 조회가 되면 리턴될 값이 바뀐다.
				wantPw = rs.getString("pw");
			}
			return wantPw;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int saveUser(String pw, String name, String birth, String phone, int subjectCode) {
		// DB에 저장 (회원가입시)
		// 학생 아이디 형식 년도(21) + 과목(10,20,30) + 세자리수 번호(001)
		// 2110001 -> 21(String) +( 10(과목코드)*1000 + su(001))
		String query = "SELECT count(*) AS su FROM user WHERE subjectcode = ?";
		try (PreparedStatement stmt = conn.prepareStatement(query);) {
			stmt.setInt(1, subjectCode);

			ResultSet result = stmt.executeQuery();// 쿼리 실행하면 행단위로 불러와서 저장
			result.next(); // 다음 행이 있니?

			// 아이디 서식
			String year = String.valueOf(LocalDate.now().getYear()).substring(2, 4);
			int code = subjectCode;
			int su = result.getInt("su");
			id = Integer.parseInt(year + (code * 1000 + su + 1));
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		String query2 = "INSERT INTO user (id, pw, name, birth, phone, subjectCode, photo) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement stmt = conn.prepareStatement(query2);) {
			stmt.setInt(1, id);
			stmt.setString(2, pw);
			stmt.setString(3, name);
			stmt.setString(4, birth);
			stmt.setString(5, phone);
			stmt.setInt(6, subjectCode);
			stmt.setBlob(7, new FileInputStream("./profile/기본프로필.jpg"));
			int result = -1;
			result = stmt.executeUpdate();
			if (result == 1) {
				return id;
			}
		} catch (SQLException | FileNotFoundException e) {
			e.printStackTrace();
		}
		return 0;
	}

	// 선생님 회원가입
	public int saveTeacher(User user) {
		// 아이디 앞에 11 + 00 + 사람 수
		int id = 0;
		String query = "SELECT count(*) AS su FROM user WHERE subjectcode = 0";
		try (PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
			rs.next();
			int count = rs.getInt("su");
			id = Integer.valueOf(("1") + (100000 + count));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String query2 = "INSERT INTO user (id, isTeacher, pw, name, birth, phone, subjectCode, photo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement stmt = conn.prepareStatement(query2)) {
			stmt.setInt(1, id);
			stmt.setBoolean(2, true);
			stmt.setString(3, user.getPassword());
			stmt.setString(4, user.getName());
			stmt.setString(5, user.getBirth());
			stmt.setString(6, user.getPhone());
			stmt.setInt(7, Integer.valueOf(user.getSubject()));
			stmt.setBlob(8, new FileInputStream("./profile/기본프로필.jpg"));

			int result = -1;
			stmt.executeUpdate();
			result = id;
			return result;
		} catch (SQLException | FileNotFoundException e) {
			e.printStackTrace();
		}
		return 0;
	}

//-----------------------------------------------------------------------------------------
	// 채팅방 만들기 - 형수
	public void creatRoom(ChatRoom cr) {
		String query = "insert into chat (subjectcode, chatdate, chattitle, chatLog, isrunning) values (?,?,?,?,true)";
		int subjectcode = cr.getSubjectCode();
		String chatDate = LocalDate.now().toString();
		String chatTitle = cr.getTitle();
		try (PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setInt(1, subjectcode);
			stmt.setString(2, chatDate);
			stmt.setString(3, chatTitle);
			stmt.setString(4, "");

			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<String> roomList() {
		return null;
		// isrunning true인 방들만 조회(title 들고오기)
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

	// 민주
	public int ChangeTitle(String title, int subjectCode) {// 데이터베이스 조작
		// DB의 채팅타이틀 변경
		String query = "update greenchat.chat set chatTitle = ?" + "where isrunning = 1 and subjectCode = ?";
		try (PreparedStatement stmt = conn.prepareStatement(query);) {
			stmt.setString(1, title);
			stmt.setInt(2, subjectCode);

			return stmt.executeUpdate(); // 실행
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int removeChatRoom(int subjectCode) { // isrunning = 0
		String query = "UPDATE greenchat.chat SET isrunning = false WHERE subjectCode = ?";
		try (PreparedStatement stmt = conn.prepareStatement(query);) {
			stmt.setInt(1, subjectCode);

			return stmt.executeUpdate(); // 실행
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

//-----------------------------------------------------------------------------------------
	// 달력 - 세호
	public List<ChatRoom> getChatLogList(String selectedDate, int receiveSubjectCode) {
		// db에서 해당 날짜로 채팅로그 조회
		List<ChatRoom> chatRoomList = new ArrayList<ChatRoom>();
		if (receiveSubjectCode == 0) {
			try (PreparedStatement stmt = conn
					.prepareStatement("SELECT * FROM greenchat.chat WHERE chatDate = ? and isrunning = ?")) {
				stmt.setString(1, selectedDate);
				stmt.setBoolean(2, false);

				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					String title = rs.getString("chatTitle");
					int subjectCode = rs.getInt("subjectCode");
					String chatLog = rs.getString("chatLog");
					ChatRoom chatRoom = new ChatRoom(title, subjectCode, chatLog, new User());
					chatRoomList.add(chatRoom);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			try (PreparedStatement stmt = conn.prepareStatement(
					"SELECT * FROM greenchat.chat WHERE chatDate = ? and subjectCode = ? and isrunning = ?")) {
				stmt.setString(1, selectedDate);
				stmt.setInt(2, receiveSubjectCode);
				stmt.setBoolean(3, false);

				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					String title = rs.getString("chatTitle");
					int subjectCode = rs.getInt("subjectCode");
					String chatLog = rs.getString("chatLog");
					ChatRoom chatRoom = new ChatRoom(title, subjectCode, chatLog, new User());
					chatRoomList.add(chatRoom);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// 결과를 List<ChatRoom>으로 반환
		return chatRoomList;
	}

//-----------------------------------------------------------------------------------------
	// 마이페이지 - 민석
	public void searchProfile() {
		// 유저 이름, 사진, 상메만 갖다주는 메소드(프로필 보기)
		String query = "SELECT name, photo, myMessage from user where id = ?";
	}

	public int updateProfileWithPhoto(User u) throws FileNotFoundException {
		// 마이프로필 수정누르고 확인 눌렀을 때 업데이트시키기
		String query = "UPDATE user SET pw = ?, name = ?, myMessage = ?, phone = ?, photo = ? where id = ?";
		try (PreparedStatement stmt = conn.prepareStatement(query);) {
			stmt.setString(1, u.getPassword());
			stmt.setString(2, u.getName());
			stmt.setString(3, u.getMyMessage());
			stmt.setString(4, u.getPhone());
			try {
				File lOutFile = new File(".\\" + u.getName() + ".jpg");
				FileOutputStream lFileOutputStream = new FileOutputStream(lOutFile);
				lFileOutputStream.write(u.getPhotoAtByte());
				lFileOutputStream.flush();
				stmt.setBlob(5, new FileInputStream(lOutFile));
				lFileOutputStream.close();

				if (lOutFile.exists()) {
					lOutFile.delete();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			stmt.setInt(6, u.getId());

			return stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int updateProfile(User u) throws FileNotFoundException {
		// 마이프로필 수정누르고 확인 눌렀을 때 업데이트시키기
		String query = "UPDATE user SET pw = ?, name = ?, myMessage = ?, phone = ? where id = ?";
		try (PreparedStatement stmt = conn.prepareStatement(query);) {
			stmt.setString(1, u.getPassword());
			stmt.setString(2, u.getName());
			stmt.setString(3, u.getMyMessage());
			stmt.setString(4, u.getPhone());
			stmt.setInt(5, u.getId());

			return stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public User getMyProfile(User user) {
		User repaintMyProfileUser = new User();
		String query = "SELECT * FROM user WHERE id = ?";
		try (PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setInt(1, user.getId());

			ResultSet rs = stmt.executeQuery();
			rs.next();
			repaintMyProfileUser.setProtocol(GreenProtocol.REPAINT_MYPROFILE_OK);
			repaintMyProfileUser.setId(rs.getInt("id"));
			repaintMyProfileUser.setPassword(rs.getString("pw"));
			repaintMyProfileUser.setName(rs.getString("name"));
			repaintMyProfileUser.setBirth(rs.getString("birth"));
			repaintMyProfileUser.setPhone(rs.getString("phone"));
			repaintMyProfileUser.setMyMessage(rs.getString("myMessage"));
			repaintMyProfileUser.setSubject(rs.getInt("subjectCode"));
			Blob blob = rs.getBlob("photo");
			byte[] photo = blob.getBytes(1, (int) blob.length());
			repaintMyProfileUser.setPhoto(photo);

			return repaintMyProfileUser;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return repaintMyProfileUser;
	}

	public User repaintMyProfile(User user) {
		User repaintMyProfileUser = new User();
		String query = "SELECT * FROM user WHERE id = ?";
		try (PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setInt(1, user.getId());

			ResultSet rs = stmt.executeQuery();
			rs.next();
			repaintMyProfileUser.setId(rs.getInt("id"));
			repaintMyProfileUser.setPassword(rs.getString("pw"));
			repaintMyProfileUser.setName(rs.getString("name"));
			repaintMyProfileUser.setBirth(rs.getString("birth"));
			repaintMyProfileUser.setPhone(rs.getString("phone"));
			repaintMyProfileUser.setMyMessage(rs.getString("myMessage"));
			repaintMyProfileUser.setSubject(rs.getInt("subjectCode"));
			Blob blob = rs.getBlob("photo");
			byte[] photo = blob.getBytes(1, (int) blob.length());
			repaintMyProfileUser.setPhoto(photo);

			return repaintMyProfileUser;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return repaintMyProfileUser;
	}

	public void updateLog(ChatMessage cm) {

		String query = "update chat set chatLog = concat(chatlog, ?)  where subjectCode = ? and chatDate = ? and isrunning = 1";
		try (PreparedStatement stmt = conn.prepareStatement(query);) {

			LocalDateTime cmtime = cm.getTime();
			String time = cmtime.format(DateTimeFormatter.ofPattern("HH:mm"));
			String date = cmtime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			stmt.setString(1, cm.getSendUser() + "," + cm.getMessage() + "," + time + ".");
			stmt.setInt(2, cm.getSubject());
			stmt.setString(3, date);

			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
