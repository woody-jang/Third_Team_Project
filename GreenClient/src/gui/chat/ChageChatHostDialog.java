package gui.chat;

import java.awt.Insets;

import javax.swing.*;

public class ChageChatHostDialog extends JDialog {
	public ChageChatHostDialog(int subjectCode) {
		JPanel mainPnl = new JPanel();
		mainPnl.setLayout(new BoxLayout(mainPnl, BoxLayout.Y_AXIS));
		JScrollPane scrl = new JScrollPane(mainPnl);
		
		JPanel titlePnl = new JPanel();
		JLabel titleLbl = new JLabel("호스트 위임");
		titlePnl.add(titleLbl);
		mainPnl.add(titlePnl);
		
		for (int i = 1; i < 5; i++) {
			JPanel userPnl = new JPanel();
			JLabel userPhotoLbl = new JLabel("프사");
			userPnl.add(userPhotoLbl);
			
			JLabel userNameLbl = new JLabel("선생님" + i);
			if (i == 1) {
				userNameLbl.setText("선생님" + i + " (나)");
			}
			userPnl.add(userNameLbl);
			
			if (i != 1) {
				JButton changeHostBtn = new JButton("위임");
				changeHostBtn.setMargin(new Insets(0, 0, 0, 0));
				userPnl.add(changeHostBtn);
			}
			
			mainPnl.add(userPnl);
		}
		
		
		add(scrl);
		
		showGUI();
	}

	private void showGUI() {
		setSize(350, 300);
		setLocation(450, 400);
		setVisible(true);
	}

	public static void main(String[] args) {
		new ChageChatHostDialog(10);
	}

}
