package gui.chat;

import javax.swing.*;

public class ChangeChatTitleDialog extends JDialog {
	
	public ChangeChatTitleDialog(int subjectCode) {
		JPanel mainPnl = new JPanel();
		mainPnl.setLayout(new BoxLayout(mainPnl, BoxLayout.Y_AXIS));
		
		JPanel titlePnl = new JPanel();
		JLabel titleLbl = new JLabel("방제 변경");
		titlePnl.add(titleLbl);
		mainPnl.add(titlePnl);
		
		JPanel inputPnl = new JPanel();
		JTextField inputTF = new JTextField(10);
		inputTF.setText("바뀌기전 방제");
		inputPnl.add(inputTF);
		
		JButton changeBtn = new JButton("바꾸기");
		inputPnl.add(changeBtn);
		mainPnl.add(inputPnl);
		
		add(mainPnl);
		
		showGUI();
	}

	private void showGUI() {
		setSize(300, 150);
		setLocation(450, 400);
		setVisible(true);
	}

	public static void main(String[] args) {
		new ChangeChatTitleDialog(10);
	}

}
