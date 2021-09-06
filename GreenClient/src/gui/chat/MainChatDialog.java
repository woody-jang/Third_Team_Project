package gui.chat;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;

import client.Client;
import shared.ChatMessage;
import shared.GreenProtocol;
import shared.User;

public class MainChatDialog extends JDialog {

	private JPanel pnl;

	private User user;
	private ChatMessage m;
	private JScrollBar vertical;
	private Boolean isTeacher;
	private List<String> memberList;
	private ObjectOutputStream oos;
	public static JMenu screenMenu3;
	private JComboBox<String> idcombo;
	private int subjectCode;
	public ShowUserListDialog suld;
	private ChangeChatUserDialog ccud;// 강퇴창 위해서 필요함.
	private int hostID;
	private List<User> userlist;

	public MainChatDialog(User user, ObjectOutputStream oos, String title, int subjectCode, int receiveHostID) {
		setTitle(title);// 생성될 채팅창 제목 정하기
		this.user = user;
		this.oos = oos;
		this.subjectCode = subjectCode;
		setModal(!user.isTeacher());
		this.isTeacher = user.isTeacher();
		hostID = receiveHostID;

		pnl = new JPanel();
		pnl.setLayout(new BoxLayout(pnl, BoxLayout.Y_AXIS));
		JPanel southpnl = new JPanel();// 밑에 채팅 치는 곳용 패널

		JScrollPane sp = new JScrollPane(pnl);// 중앙에 스크롤패널
		vertical = sp.getVerticalScrollBar();// 스크롤바를 조종하기 위한 변수

		// 이건 패널레이블을 만드는 메소들에서 문장의 길이를 확인하여 너무 길면 짤라서 레이블을 2줄로 만들어야 한다.
		// newPnl("엄청나게 길게 만들어진 문장은 어떻게 보이는지 확인하기 위해서 만든 패널의 레이블입니다. 과연 어떻게나올까요");

		JMenuBar mb = new JMenuBar();// 메뉴바를 하나 만든다
		mb.setBackground(Color.GREEN);

		JMenu screenMenu = new JMenu("첨부");// 메뉴를 만든다

		JMenuItem miPicture = new JMenuItem("사진");// 메뉴 안에 들어갈 아이템을 만든다
		JMenuItem miFile = new JMenuItem("파일");// 메뉴 안에 들어갈 아이템을 만든다

		screenMenu.add(miPicture);
		screenMenu.add(miFile);

		JMenu screenMenu2 = new JMenu("대화상대");// 메뉴를 만든다

		JMenuItem miUserList = new JMenuItem("목록");// 메뉴 안에 들어갈 아이템을 만든다
		miUserList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (user.isTeacher()) {
					suld = new ShowUserListDialog(subjectCode, MainChatDialog.this);
					suld.showGUI();
				} else {
					suld = new ShowUserListDialog(subjectCode);
					suld.showGUI();
				}
			}
		});
		screenMenu2.add(miUserList);

		screenMenu3 = new JMenu("호스트");
		if (user.getId() != hostID) {
			screenMenu3.setEnabled(false);
		} else {
			screenMenu3.setEnabled(true);
		}

		JMenuItem miChangeTitle = new JMenuItem("방제변경");// 메뉴 안에 들어갈 아이템을 만든다
		miChangeTitle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ChangeChatTitleDialog dialog = new ChangeChatTitleDialog(MainChatDialog.this.getTitle(), subjectCode);
			}
		});
		JMenuItem miExpulsion = new JMenuItem("강퇴");// 메뉴 안에 들어갈 아이템을 만든다
		miExpulsion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ccud = new ChangeChatUserDialog(subjectCode, userlist);
				ccud.showGUI();
			}
		});
		JMenuItem miMandate = new JMenuItem("위임");// 메뉴 안에 들어갈 아이템을 만든다
		miMandate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ChageChatHostDialog(subjectCode);
			}
		});

		screenMenu3.add(miChangeTitle);
		screenMenu3.add(miExpulsion);
		screenMenu3.add(miMandate);

		JMenu screenMenu4 = new JMenu("나가기");// 메뉴를 만든다
		JMenuItem miExit = new JMenuItem("채팅방 나가기");// 메뉴 안에 들어갈 아이템을 만든다
		miExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(MainChatDialog.this, "채팅방에서 나가시겠습니까?", "",
						JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.NO_OPTION)
					return;

				if (user.getId() == hostID) {
					Client.service.hostExitChat(subjectCode);
				} else {
					Client.service.exitChat(user.getId(), subjectCode);
				}
				dispose();
			}
		});
		screenMenu4.add(miExit);

		idcombo = new JComboBox();
		idcombo.addItem("모두에게");
		idcombo.setPreferredSize(new Dimension(80, 30));

		JTextField tf = new JTextField(15);// 채팅용 텍스트 필드 생성

		tf.addKeyListener(new KeyAdapter() {// 엔터를 치면 작동하는 리스너를 하나 만들었음
			@Override
			public void keyReleased(KeyEvent e) {// 안에 내용은 나중에 네트워크랑 전송되는 방식으로 바꿀예정
				if (isEnter(e)) {
					if (tf.getText().equals("")) { // 빈칸일때 enter불가능
						return;
					}
					// TODO
					StringBuilder sb = new StringBuilder();
					char[] ch = tf.getText().toCharArray();
					for (int i = 0; i < ch.length; i++) {
						sb.append(ch[i]);
						if (i != 0 && i % 14 == 0) {
							sb.append("\n");
						}
					}
					String target = idcombo.getSelectedItem().toString();
					sendMessage(target, sb.toString());
					tf.setText("");
					revalidate();
					repaint();
				}
			}
		});

		mb.add(screenMenu);// 메뉴바에 메뉴추가
		mb.add(screenMenu2);
		mb.add(screenMenu3);
		mb.add(screenMenu4);

		ImageIcon icon = new ImageIcon("./버튼이모티콘.png");
		JLabel imobtn = new JLabel(icon);
		imobtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new Emoticondialog(user, subjectCode, oos);
			}
		});

		southpnl.add(idcombo);// 밑에 콤보박스랑 텍스트필드 추가
		southpnl.add(tf);
		southpnl.add(imobtn);

		add(sp);// 프레임에 모든 컴포넌트 추가
		add(mb, "North");
		add(southpnl, "South");

		miPicture.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				InputFile(1);
			}
		});

		miFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				InputFile(2);
			}
		});

		// x눌리면 나가기
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				miExit.doClick();
			}
		});
	}

	public void ChangeHost(int newHostUserID) { // 호스트변경후 호스트메뉴 활성화
		hostID = newHostUserID;
		if (user.getId() == newHostUserID)
			screenMenu3.setEnabled(true);
		else
			screenMenu3.setEnabled(false);
		revalidate();
		repaint();
	}

	public void InputFile(int i) { // 첨부버튼을 눌러서 파일을 첨부하는 기능만들어보자.~~
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("D:\\"));

		if (i == 1) { // 사진버튼을 눌렀을때 사진형식 파일만 선택가능하게 하기
			chooser.setFileFilter(new FileFilter() {
				@Override
				public boolean accept(File f) {
					if (f.isDirectory()) {
						return true;
					}
					return f.getName().endsWith("png");
				}

				@Override
				public String getDescription() {
					return "그림파일(*.png) ";
				}
			});
			chooser.setFileFilter(new FileFilter() {
				@Override
				public boolean accept(File f) {
					if (f.isDirectory()) {
						return true;
					}
					return f.getName().endsWith("jpg");
				}

				@Override
				public String getDescription() {
					return "그림(*.jpg) ";
				}
			});
			chooser.setAcceptAllFileFilterUsed(false);

		} else if (i == 2) { // 파일을 눌렀을때 모든 파일형식 다가능
			chooser.setAcceptAllFileFilterUsed(true);

		}
		// chooser.addChoosableFileFilter(filter);
		// chooser.setMultiSelectionEnabled(true); // 여러개 선택 가능하게

		int result = chooser.showOpenDialog(null);
		File file = null;

		if (result == JFileChooser.APPROVE_OPTION) {
			System.out.println("사용자가 파일을 선택함");
			file = chooser.getSelectedFile();
			if (file.getName().endsWith("jpg") || file.getName().endsWith("png")) {
				try {

					ChatMessage cm = new ChatMessage(GreenProtocol.INPUT_PICTURE, file, LocalDateTime.now(),
							user.getName());

					byte[] fileContent = Files.readAllBytes(file.toPath());

					cm.setImage(fileContent);

					cm.setSubject(subjectCode); // 어느 과목에서 보낸건지 판단

					oos.reset();
					oos.writeObject(cm);
					oos.flush();

				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} else {

				try {
					ChatMessage cm = new ChatMessage(GreenProtocol.INPUT_FILE, file, LocalDateTime.now(),
							user.getName());
					byte[] fileContent = Files.readAllBytes(file.toPath());

					cm.setImage(fileContent);
					cm.setSubject(subjectCode); // 어느 과목에서 보낸건지 판단

					oos.reset();
					oos.writeObject(cm);
					oos.flush();

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}
	}

	private boolean isEnter(KeyEvent e) { // 엔터를 쳤는지 확인하기
		return e.getKeyCode() == KeyEvent.VK_ENTER;
	}

	public void myMessage(ChatMessage message) {// 내가 보낸 메세지가 서버에서 도착했을때

		JPanel newPnl0 = new JPanel();
		JPanel newPnl1 = new JPanel();
		newPnl1.setLayout(new BoxLayout(newPnl1, BoxLayout.Y_AXIS));
		newPnl0.setPreferredSize(new Dimension(280, 40));
		newPnl0.setMinimumSize(new Dimension(280, 40));
		newPnl0.setMaximumSize(new Dimension(280, 40));
		newPnl1.setPreferredSize(new Dimension(280, 40));
		newPnl1.setMinimumSize(new Dimension(280, 40));
		newPnl1.setMaximumSize(new Dimension(280, 40));
		JLabel lblName = new JLabel(message.getSendUser() + " : ");
		LocalDateTime time = message.getTime();
		String chattime = time.format(DateTimeFormatter.ofPattern("HH:mm"));
		lblName.setPreferredSize(new Dimension(280, 15));
		lblName.setMinimumSize(new Dimension(280, 15));
		lblName.setMaximumSize(new Dimension(280, 15));
		JLabel lblmessage = new JLabel(chattime + " " + message.getMessage());
		lblmessage.setPreferredSize(new Dimension(280, 25));
		lblmessage.setMinimumSize(new Dimension(280, 25));
		lblmessage.setMaximumSize(new Dimension(280, 25));
		lblmessage.setFont(new Font(lblmessage.getFont().getName(), Font.PLAIN, 15));
		lblName.setOpaque(true);
		lblmessage.setOpaque(true);

		if (message.getProtocol().equals(GreenProtocol.CHAT_ONE)
				|| message.getProtocol().equals(GreenProtocol.HOST_CHAT_ONE)) {// 내가 보낸 귓속말이 다시 나에게 왔을때
			lblName.setForeground(Color.pink);
			lblmessage.setForeground(Color.pink);
		}

		lblName.setHorizontalAlignment(JLabel.RIGHT);
		lblmessage.setHorizontalAlignment(JLabel.RIGHT);
		newPnl1.add(lblName);
		newPnl1.add(lblmessage);
		newPnl0.add(newPnl1);
		pnl.add(newPnl0);

		redraw();
		viewDown();
	}

	public void announce(String announce) { // 전체공지
		JPanel newPnl = new JPanel();
		JLabel lblmessage = new JLabel(announce);
		lblmessage.setOpaque(true);
		newPnl.setOpaque(true);
		newPnl.setPreferredSize(new Dimension(280, 25));
		newPnl.setMaximumSize(new Dimension(280, 25));
		newPnl.setMinimumSize(new Dimension(280, 25));
		lblmessage.setForeground(Color.red);
		lblmessage.setHorizontalAlignment(JLabel.CENTER);
		newPnl.add(lblmessage);
		pnl.add(newPnl);

		redraw();
		viewDown();
	}

	public void otherMessage(ChatMessage message) {// 내가보낸거말고
		JPanel newPnl0 = new JPanel();
		JPanel newPnl1 = new JPanel();
		newPnl1.setLayout(new BoxLayout(newPnl1, BoxLayout.Y_AXIS));
		newPnl0.setPreferredSize(new Dimension(280, 40));
		newPnl0.setMinimumSize(new Dimension(280, 40));
		newPnl0.setMaximumSize(new Dimension(280, 40));
		newPnl1.setPreferredSize(new Dimension(280, 40));
		newPnl1.setMinimumSize(new Dimension(280, 40));
		newPnl1.setMaximumSize(new Dimension(280, 40));
		JLabel lblName = new JLabel(message.getSendUser() + " : ");
		LocalDateTime time = message.getTime();
		String chattime = time.format(DateTimeFormatter.ofPattern("HH:mm"));
		JLabel lblmessage = new JLabel(message.getMessage() + " " + chattime);
		lblmessage.setFont(new Font(lblmessage.getFont().getName(), Font.PLAIN, 15));
		lblName.setPreferredSize(new Dimension(280, 15));
		lblName.setMinimumSize(new Dimension(280, 15));
		lblName.setMaximumSize(new Dimension(280, 15));
		lblmessage.setPreferredSize(new Dimension(280, 25));
		lblmessage.setMinimumSize(new Dimension(280, 25));
		lblmessage.setMaximumSize(new Dimension(280, 25));
		lblName.setOpaque(true);
		lblmessage.setOpaque(true);

		if (message.getProtocol().equals(GreenProtocol.HOST_CHAT_ALL)) {// 호스트가 모두에게
			lblName.setForeground(Color.BLUE);
			lblmessage.setForeground(Color.BLUE);
		} else if (message.getProtocol().equals(GreenProtocol.HOST_CHAT_ONE)) {// 호스트가 귓속말
			lblName.setForeground(Color.PINK);
			lblmessage.setForeground(Color.PINK);
		} else if (message.getProtocol().equals(GreenProtocol.CHAT_ALL)) {
			lblName.setForeground(Color.DARK_GRAY);
			lblmessage.setForeground(Color.DARK_GRAY);
		} else if (message.getProtocol().equals(GreenProtocol.CHAT_ONE)) {
			lblName.setForeground(Color.PINK);
			lblmessage.setForeground(Color.PINK);

		}

		lblName.setHorizontalAlignment(JLabel.LEFT);
		lblmessage.setHorizontalAlignment(JLabel.LEFT);
		newPnl1.add(lblName);
		newPnl1.add(lblmessage);
		newPnl0.add(newPnl1);
		pnl.add(newPnl0);

		redraw();
		viewDown();
	}

	// 필요함.사진그려주는거
	public void myPicture(ChatMessage cm) {
		JPanel newPnl1 = new JPanel();
		JPanel newPnl = new JPanel();
		newPnl.setLayout(new BoxLayout(newPnl, BoxLayout.Y_AXIS));
		newPnl.setOpaque(true);
		JLabel lblName = new JLabel(cm.getSendUser());
		lblName.setPreferredSize(new Dimension(280, 15));
		lblName.setMinimumSize(new Dimension(280, 15));
		lblName.setMaximumSize(new Dimension(280, 15));
		lblName.setOpaque(true);
		JLabel lblmessage = null;

		if (user.getName().equals(cm.getSendUser())) {
			lblName.setHorizontalAlignment(JLabel.RIGHT);

			try {
				BufferedImage image = ImageIO.read(new ByteArrayInputStream(cm.getImage()));
				ImageIcon pic = new ImageIcon(image);

				Image im = pic.getImage();
				Image changeim = im.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
				pic = new ImageIcon(changeim);

				lblmessage = new JLabel(pic);
				lblmessage.setPreferredSize(new Dimension(280, 200));
				lblmessage.setMinimumSize(new Dimension(280, 200));
				lblmessage.setMaximumSize(new Dimension(280, 200));
				lblmessage.setOpaque(true);
				lblmessage.setHorizontalAlignment(JLabel.RIGHT);

			} catch (NullPointerException e) {
				JOptionPane.showMessageDialog(null, "파일을 찾을 수 없습니다.");
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {
			lblName.setHorizontalAlignment(JLabel.LEFT);

			try {
				BufferedImage image = ImageIO.read(new ByteArrayInputStream(cm.getImage()));
				ImageIcon pic = new ImageIcon(image);

				Image im = pic.getImage();
				Image changeim = im.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
				pic = new ImageIcon(changeim);

				lblmessage = new JLabel(pic);
				lblmessage.setPreferredSize(new Dimension(280, 200));
				lblmessage.setMinimumSize(new Dimension(280, 200));
				lblmessage.setMaximumSize(new Dimension(280, 200));
				lblmessage.setOpaque(true);

				lblmessage = new JLabel(pic);
				lblmessage.setOpaque(true);
				lblmessage.setHorizontalAlignment(JLabel.LEFT);

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		newPnl.add(lblName);
		newPnl.add(lblmessage);
		newPnl1.add(newPnl);
		pnl.add(newPnl1);

		// JLabel lblProfile = new JLabel(iconlogo);

		redraw();
		viewDown();

	}

	// 필요함. 파일이름 뜨고 클릭하면 경로정해서 저장하는거.
	public void myFile(ChatMessage cm) {
		JPanel newPnl1 = new JPanel();
		JPanel newPnl = new JPanel();
		newPnl.setLayout(new BoxLayout(newPnl, BoxLayout.Y_AXIS));
		newPnl.setOpaque(true);
		JLabel lblName = new JLabel(cm.getSendUser());

		lblName.setPreferredSize(new Dimension(280, 15));
		lblName.setMinimumSize(new Dimension(280, 15));
		lblName.setMaximumSize(new Dimension(280, 15));
		lblName.setOpaque(true);

		newPnl.add(lblName);
		String str = cm.getFile().toString();
		JLabel lblmessage = new JLabel(str.substring(str.lastIndexOf("\\") + 1, str.length()));

		lblmessage.setPreferredSize(new Dimension(280, 25));
		lblmessage.setMinimumSize(new Dimension(280, 25));
		lblmessage.setMaximumSize(new Dimension(280, 25));
		lblmessage.setOpaque(true);

		if (user.getName().equals(cm.getSendUser())) {
			lblName.setHorizontalAlignment(JLabel.RIGHT);
			lblmessage.setHorizontalAlignment(JLabel.RIGHT);

		} else {
			lblName.setHorizontalAlignment(JLabel.LEFT);
			lblmessage.setHorizontalAlignment(JLabel.LEFT);
		}

		newPnl.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "다운로드 하시겠습니까?", "파일", JOptionPane.YES_NO_OPTION);
				if (result == 0) {
					JFileChooser chooser = new JFileChooser();
					chooser.setCurrentDirectory(new File("D:\\"));
					chooser.setSelectedFile(cm.getFile());
					int resultfilepath = chooser.showSaveDialog(null);
					File file = null;

					if (result == JFileChooser.APPROVE_OPTION) {
						System.out.println("사용자가 파일을 선택함");
						file = chooser.getSelectedFile();

						try {
							FileOutputStream fw = new FileOutputStream(file);
							fw.write(cm.getImage());
						} catch (IOException e1) {
							e1.printStackTrace();
						}

					}
				}
			}
		});

		lblmessage.setOpaque(true);
		newPnl.add(lblmessage);

		newPnl1.add(newPnl);
		pnl.add(newPnl1);

		redraw();
		viewDown();
	}

	public void redraw() {// 다시그리기
		revalidate();
		repaint();
	}

	public void viewDown() {// 스크롤바 아래로 내리기
		vertical.setValue(vertical.getMaximum());
	}

	public void makeMemberListForServer(List<String> serverlist) {
		this.memberList = serverlist;
	}

	private void sendMessage(String target, String text) {
		m = new ChatMessage();
		if (isTeacher) {
			if (target.equals("모두에게")) {
				m.setProtocol(GreenProtocol.HOST_CHAT_ALL);

			} else {
				m.setProtocol(GreenProtocol.HOST_CHAT_ONE);
				m.setReceiveUser(target);
			}

		} else {
			if (target.equals("모두에게")) {
				m.setProtocol(GreenProtocol.CHAT_ALL);

			} else {
				m.setProtocol(GreenProtocol.CHAT_ONE);
				m.setReceiveUser(target);
			}
		}

		m.setMessage(text);
		m.setTime(LocalDateTime.now());
		m.setSendUser(user.getName());
		m.setSubject(subjectCode);

		Client.service.sendMessage(m);
	}

	public void updateUserList() { // 귓속말 콤보 업데이트
		idcombo.removeAllItems();
		idcombo.addItem("모두에게");
		if (user.isTeacher()) {
			for (User user : userlist) {
				if (!(this.user.equals(user))) {
					idcombo.addItem(user.getName());
				}
			}
		} else {
			for (User user : Client.userList) {
				if (!(this.user.equals(user))) {
					idcombo.addItem(user.getName());
				}
			}
		}
		redraw();
	}

	public void showGUI() {// 보이기
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setSize(330, 500);
		setVisible(true);
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ChangeChatUserDialog getCcud() {
		return ccud;
	}

	public void myEmoticon(ChatMessage message) {
		JPanel newPnl1 = new JPanel();
		JPanel newPnl = new JPanel();
		newPnl.setLayout(new BoxLayout(newPnl, BoxLayout.Y_AXIS));

		JLabel lblName = new JLabel(message.getSendUser() + " : ");
		LocalDateTime time = message.getTime();
		ImageIcon img = new ImageIcon(message.getEmopath());
		Image beforeImg = img.getImage();
		Image afterImg = beforeImg.getScaledInstance(200, 200, Image.SCALE_SMOOTH);

		if (message.getEmopath().endsWith(".gif"))
			img = new ImageIcon(beforeImg);
		else
			img = new ImageIcon(afterImg);

		JLabel lblmessage = new JLabel(img);

		lblName.setPreferredSize(new Dimension(280, 15));
		lblName.setMinimumSize(new Dimension(280, 15));
		lblName.setMaximumSize(new Dimension(280, 15));
		lblmessage.setPreferredSize(new Dimension(280, 200));
		lblmessage.setMinimumSize(new Dimension(280, 200));
		lblmessage.setMaximumSize(new Dimension(280, 200));

		lblName.setOpaque(true);
		lblmessage.setOpaque(true);

		lblName.setHorizontalAlignment(JLabel.RIGHT);
		lblmessage.setHorizontalAlignment(JLabel.RIGHT);
		newPnl.add(lblName);
		newPnl.add(lblmessage);

		newPnl1.add(newPnl);
		pnl.add(newPnl1);

		redraw();
		viewDown();

	}

	public void otherEmoticon(ChatMessage message) {
		JPanel newPnl1 = new JPanel();
		JPanel newPnl = new JPanel();
		newPnl.setLayout(new BoxLayout(newPnl, BoxLayout.Y_AXIS));

		JLabel lblName = new JLabel(message.getSendUser() + " : ");
		LocalDateTime time = message.getTime();
		ImageIcon img = new ImageIcon(message.getEmopath());
		Image beforeImg = img.getImage();
		Image afterImg = beforeImg.getScaledInstance(200, 200, Image.SCALE_SMOOTH);

		if (message.getEmopath().endsWith(".gif"))
			img = new ImageIcon(beforeImg);
		else
			img = new ImageIcon(afterImg);

		JLabel lblmessage = new JLabel(img);

		lblName.setPreferredSize(new Dimension(280, 15));
		lblName.setMinimumSize(new Dimension(280, 15));
		lblName.setMaximumSize(new Dimension(280, 15));
		lblmessage.setPreferredSize(new Dimension(280, 200));
		lblmessage.setMinimumSize(new Dimension(280, 200));
		lblmessage.setMaximumSize(new Dimension(280, 200));

		lblName.setOpaque(true);
		lblmessage.setOpaque(true);

		lblName.setHorizontalAlignment(JLabel.LEFT);
		lblmessage.setHorizontalAlignment(JLabel.LEFT);
		newPnl.add(lblName);
		newPnl.add(lblmessage);

		newPnl1.add(newPnl);
		pnl.add(newPnl1);

		redraw();
		viewDown();

	}

	public List<User> getUserlist() {
		return userlist;
	}

	public void setUserlist(List<User> userlist) {
		this.userlist = userlist;
	}

}
