package gui.chat;

import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;

import client.Client;
import shared.User;

public class ChageChatHostDialog extends JDialog {
	public ChageChatHostDialog(int subjectCode) {
		JPanel mainPnl = new JPanel();
		mainPnl.setLayout(new BoxLayout(mainPnl, BoxLayout.Y_AXIS));
		JScrollPane scrl = new JScrollPane(mainPnl);

		JPanel titlePnl = new JPanel();
		JLabel titleLbl = new JLabel("호스트 위임");
		titlePnl.add(titleLbl);
		mainPnl.add(titlePnl);

		Client.service.findChatRoomUser(subjectCode);
		try {
			Thread.sleep(700);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		List<User> userList = Client.userList;
		for (User user : userList) {
			if(!user.isTeacher()) {
				continue;
			}
			JPanel userPnl = new JPanel();
			Image img = user.getPhoto();
			Image scale = img.getScaledInstance(50, 50, Image.SCALE_DEFAULT);

			JLabel userPhotoLbl = new JLabel(new ImageIcon(scale));
			userPnl.add(userPhotoLbl);

			JLabel userNameLbl = new JLabel(user.getName());
			if (Client.user.equals(user)) { // 본인
				userNameLbl.setText(user.getName() + " (나)");
				userPnl.add(userNameLbl);
			} else {
				userPnl.add(userNameLbl);
				JButton changeHostBtn = new JButton("위임");
				changeHostBtn.setMargin(new Insets(0, 0, 0, 0));
				userPnl.add(changeHostBtn);
				changeHostBtn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						int result = JOptionPane.showConfirmDialog(ChageChatHostDialog.this,
								userNameLbl.getText() + " 님께 위임하시겠습니까?", "", JOptionPane.YES_NO_OPTION);
						if (result == JOptionPane.YES_OPTION) {
							Client.service.changeHost(user, subjectCode);
							
							// 리프레쉬 (호스트 받은사람이 호스트메뉴 사용,준사람 자격박탈)
//							MainChatDialog.screenMenu3.setEnabled(false);
							dispose();
						}
					}
				});
			}

			mainPnl.add(userPnl);
		}

		add(scrl);

		showGUI();
	}

	private void showGUI() {
		setModal(true);
		setSize(350, 300);
		setLocation(450, 400);
		setVisible(true);
	}
}
