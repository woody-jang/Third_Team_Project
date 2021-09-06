package gui.chat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.security.Key;
import java.time.LocalDate;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import client.Client;
import shared.ChatRoom;
import shared.GreenProtocol;
import shared.User;

public class ChangeChatTitleDialog extends JDialog {
	public String newTitle;
	private JButton changeBtn;

	public ChangeChatTitleDialog(String title, int subjectCode) {

		JPanel mainPnl = new JPanel();
		mainPnl.setLayout(new BoxLayout(mainPnl, BoxLayout.Y_AXIS));

		JPanel titlePnl = new JPanel();
		JLabel titleLbl = new JLabel("방제 변경");
		titlePnl.add(titleLbl);
		mainPnl.add(titlePnl);

		JPanel inputPnl = new JPanel();
		JTextField inputTF = new JTextField(10);
		inputTF.setText(title.split("_")[1]);
		inputTF.selectAll();
		inputTF.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char enter = e.getKeyChar();
				if (enter == KeyEvent.VK_ENTER) {
					changeBtn.doClick();
				}
			}
		});
		inputPnl.add(inputTF);

		changeBtn = new JButton("바꾸기");
		changeBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LocalDate today = LocalDate.now();
				newTitle = title.split("_")[0] + "_" + inputTF.getText();
				Client.service.changeTitle(newTitle, subjectCode);
				try {
					Thread.sleep(700);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				dispose();
			}
		});
		inputPnl.add(changeBtn);
		mainPnl.add(inputPnl);

		add(mainPnl);

		showGUI();
	}

	private void showGUI() {
		setModal(true);
		pack();
		setLocation(450, 400);
		setVisible(true);
	}
}
