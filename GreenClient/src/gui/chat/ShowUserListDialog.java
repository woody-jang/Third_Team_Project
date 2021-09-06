package gui.chat;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.*;

import client.Client;
import shared.User;

public class ShowUserListDialog extends JDialog {// 완성함.
	private List<User> userList;
	private Font font23 = new Font("굴림", Font.PLAIN, 23);
	private Font font20 = new Font("굴림", Font.PLAIN, 20);
	private Font font25 = new Font("굴림", Font.PLAIN, 25);
	private JPanel mainPnl;
	private JPanel titlePnl;

	public ShowUserListDialog(int subjectCode) {

		mainPnl = new JPanel();
		mainPnl.setLayout(new BoxLayout(mainPnl, BoxLayout.Y_AXIS));
		JScrollPane scrl = new JScrollPane(mainPnl);
		setModal(true);
		titlePnl = new JPanel();
		JLabel titleLbl = new JLabel("대화상대 목록");
		titleLbl.setFont(font23);
		titlePnl.add(titleLbl);
		mainPnl.add(titlePnl);

		drawUserList();

		add(scrl);

	}

	public ShowUserListDialog(int subjectCode, MainChatDialog mcd) {

		mainPnl = new JPanel();
		mainPnl.setLayout(new BoxLayout(mainPnl, BoxLayout.Y_AXIS));
		JScrollPane scrl = new JScrollPane(mainPnl);
		setModal(true);
		titlePnl = new JPanel();
		JLabel titleLbl = new JLabel("대화상대 목록");
		titleLbl.setFont(font23);
		titlePnl.add(titleLbl);
		mainPnl.add(titlePnl);

		drawUserList(mcd.getUserlist());

		add(scrl);

	}

	private void drawUserList(List<User> userlist2) {
		for (User user : userlist2) {
			JPanel userPnl = new JPanel();

			Image img = user.getPhoto();
			Image scale = img.getScaledInstance(50, 50, Image.SCALE_DEFAULT);

			JLabel userPhotoLbl = new JLabel(new ImageIcon(scale));
			userPhotoLbl.setPreferredSize(new Dimension(50, 50));
			JLabel userNameLbl = new JLabel(user.getName());
			userNameLbl.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					new ProfileDialog(user);
				}

			});
			userNameLbl.setFont(font25);
			userPnl.add(userPhotoLbl);
			userPnl.add(userNameLbl);

			mainPnl.add(userPnl);
		}

	}

	public void drawUserList() {
		for (User user : Client.userList) {
			JPanel userPnl = new JPanel();

			Image img = user.getPhoto();
			Image scale = img.getScaledInstance(50, 50, Image.SCALE_DEFAULT);

			JLabel userPhotoLbl = new JLabel(new ImageIcon(scale));
			userPhotoLbl.setPreferredSize(new Dimension(50, 50));
			JLabel userNameLbl = new JLabel(user.getName());
			userNameLbl.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					new ProfileDialog(user);
				}

			});
			userNameLbl.setFont(font25);
			userPnl.add(userPhotoLbl);
			userPnl.add(userNameLbl);

			mainPnl.add(userPnl);
		}

	}

	public void showGUI() {
		setSize(350, 500);
		setLocation(450, 400);
		setVisible(true);
	}
}