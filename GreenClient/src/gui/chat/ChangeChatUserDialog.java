package gui.chat;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;

import client.Client;
import shared.GreenProtocol;
import shared.User;

public class ChangeChatUserDialog extends JDialog {// 완성함.
	private List<User> userList;
	private Font font23 = new Font("굴림", Font.PLAIN, 23);
	private Font font20 = new Font("굴림", Font.PLAIN, 20);
	private Font font25 = new Font("굴림", Font.PLAIN, 25);
	private JPanel mainPnl;
	private int subjectCode;
	private JPanel titlePnl;
	private List<User> userlist;

	public ChangeChatUserDialog(int subjectCode, List<User> userlist) {
		this.subjectCode = subjectCode;
		this.userlist = userlist;

		mainPnl = new JPanel();
		mainPnl.setLayout(new BoxLayout(mainPnl, BoxLayout.Y_AXIS));
		JScrollPane scrl = new JScrollPane(mainPnl);
		setModal(true);
		titlePnl = new JPanel();
		JLabel titleLbl = new JLabel("대화상대 목록");
		titleLbl.setFont(font23);
		titlePnl.add(titleLbl);
		mainPnl.add(titlePnl);

		for (User user : userlist) {
			JPanel userPnl = new JPanel();

			Toolkit toolkit = Toolkit.getDefaultToolkit();
			Image img = user.getPhoto();
			Image scale = img.getScaledInstance(50, 50, Image.SCALE_DEFAULT);

			JLabel userPhotoLbl = new JLabel(new ImageIcon(scale));
			userPhotoLbl.setPreferredSize(new Dimension(50, 50));
			JLabel userNameLbl = new JLabel(user.getName());
			userNameLbl.setFont(font25);

			JButton userOutBtn = new JButton("강퇴");
			userOutBtn.setMargin(new Insets(0, 0, 0, 0));
			userOutBtn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					User getoutUser = new User();
					getoutUser.setSubject(subjectCode);
					getoutUser.setId(user.getId());
					getoutUser.setName(user.getName());
					getoutUser.setProtocol(GreenProtocol.GET_OUT_USER);

					Client.service.getOutUser(getoutUser);

					dispose();
				}
			});
			userPnl.add(userPhotoLbl);
			userPnl.add(userNameLbl);
			if (!Client.user.equals(user)) {
				userPnl.add(userOutBtn);
			}

			mainPnl.add(userPnl);
		}

		add(scrl);

	}

	public void newUserList() {// 한명을 강퇴후 목록 다시 그리는 메소드.

		mainPnl.removeAll();

		mainPnl.setLayout(new BoxLayout(mainPnl, BoxLayout.Y_AXIS));
		mainPnl.add(titlePnl);
		for (User user : userlist) {
			JPanel userPnl = new JPanel();

			Toolkit toolkit = Toolkit.getDefaultToolkit();
			Image img = user.getPhoto();
			Image scale = img.getScaledInstance(50, 50, Image.SCALE_DEFAULT);

			JLabel userPhotoLbl = new JLabel(new ImageIcon(scale));
			userPhotoLbl.setPreferredSize(new Dimension(50, 50));
			JLabel userNameLbl = new JLabel(user.getName());
			userNameLbl.setFont(font25);

			JButton userOutBtn = new JButton("강퇴");
			userOutBtn.setMargin(new Insets(0, 0, 0, 0));
			userOutBtn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					User getoutUser = new User();
					getoutUser.setSubject(subjectCode);
					getoutUser.setId(user.getId());
					getoutUser.setName(user.getName());
					getoutUser.setProtocol(GreenProtocol.GET_OUT_USER);

					Client.service.getOutUser(getoutUser);

					dispose();
				}
			});

			userPnl.add(userPhotoLbl);
			userPnl.add(userNameLbl);
			userPnl.add(userOutBtn);

			mainPnl.add(userPnl);
		}

		revalidate();
		repaint();

	}

	public void showGUI() {
		setSize(350, 500);
		setLocation(450, 400);
		setVisible(true);
	}

}