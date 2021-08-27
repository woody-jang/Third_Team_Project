package gui.chat;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import shared.User;

public class ShowUserListDialog extends JDialog {
	private List<User> userList;
	private Font font23 = new Font("굴림", Font.PLAIN, 23);
	private Font font20 = new Font("굴림", Font.PLAIN, 20);
	public ShowUserListDialog(int subjectCode) {
//		setUserList(subjectCode);
		
		JPanel mainPnl = new JPanel();
		mainPnl.setLayout(new BoxLayout(mainPnl, BoxLayout.Y_AXIS));
		JScrollPane scrl = new JScrollPane(mainPnl);
		
		JPanel titlePnl = new JPanel();
		JLabel titleLbl = new JLabel("대화상대 목록");
		titleLbl.setFont(font23);
		titlePnl.add(titleLbl);
		mainPnl.add(titlePnl);
		
		for (int i = 1; i < 16; i++) {
			JPanel userPnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
			userPnl.setPreferredSize(new Dimension(310, 30));
			
			JLabel userPhotoLbl = new JLabel("사진");
			userPhotoLbl.setPreferredSize(new Dimension(50, 25));
			
			JLabel userNameLbl = new JLabel("자바" + i);
			userNameLbl.setPreferredSize(new Dimension(250, 25));
			userNameLbl.setFont(font20);
			
			userPnl.add(userPhotoLbl);
			userPnl.add(userNameLbl);
			
			mainPnl.add(userPnl);
		}
		
//		for (User user : userList) {
//			JPanel userPnl = new JPanel();
//			
//			Toolkit toolkit = Toolkit.getDefaultToolkit();
//			Image img = toolkit.getImage(user.getPhoto().getAbsolutePath());
//			Image scale = img.getScaledInstance(50, 50, java.awt.Image.SCALE_DEFAULT);
//			
//			JLabel userPhotoLbl = new JLabel(new ImageIcon(scale));
//			userPhotoLbl.setPreferredSize(new Dimension(50, 50));
//			JLabel userNameLbl = new JLabel(user.getName());
//			userNameLbl.setFont(font25);
//		}
		
		add(scrl);
		
		showGUI();
	}

	private void showGUI() {
		setSize(350, 500);
		setLocation(450, 400);
		setVisible(true);
	}

	private void setUserList(int subjectCode) {
		Map<User, Boolean> userList = null;
//		switch (subjectCode) {
//		case 10:
//			userList = Server.getJavaMap();
//			break;
//		case 20:
//			userList = Server.getPythonMap();
//			break;
//		case 30:
//			userList = Server.getCMap();
//			break;
//		}
		for (User user : userList.keySet()) {
			this.userList.add(user);
		}
	}
	
	public static void main(String[] args) {
		new ShowUserListDialog(10);
	}
}
