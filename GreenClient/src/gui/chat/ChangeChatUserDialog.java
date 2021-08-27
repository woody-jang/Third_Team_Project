package gui.chat;

import java.awt.Insets;
import java.util.List;

import javax.swing.*;

import shared.User;

public class ChangeChatUserDialog extends JDialog {
	private List<User> userList;
	
	public ChangeChatUserDialog(int subjectCode) {
		JPanel mainPnl = new JPanel();
		mainPnl.setLayout(new BoxLayout(mainPnl, BoxLayout.Y_AXIS));
		JScrollPane scrl = new JScrollPane(mainPnl);
		
		JPanel titlePnl = new JPanel();
		JLabel titleLbl = new JLabel("대화상대 수정");
		titlePnl.add(titleLbl);
		mainPnl.add(titlePnl);
		
		for (int i = 1; i < 16; i++) {
			JPanel userPnl = new JPanel();
			JLabel userPhotoLbl = new JLabel("프사");
			userPnl.add(userPhotoLbl);
			
			JLabel userNameLbl = new JLabel("자바" + i);
			if (i == 1) {
				userNameLbl.setText("자바" + i + " (나)");
			}
			userPnl.add(userNameLbl);
			
			if (i != 1) {
				JButton userOutBtn = new JButton("강퇴");
				userOutBtn.setMargin(new Insets(0, 0, 0, 0));
				userPnl.add(userOutBtn);
			}
			mainPnl.add(userPnl);
		}
		
		
		add(scrl);
		
		showGUI();
	}

	private void showGUI() {
		setSize(350, 500);
		setLocation(450, 400);
		setVisible(true);
	}

	public static void main(String[] args) {
		new ChangeChatUserDialog(10);
	}

}
