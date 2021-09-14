package gui.admin;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import client.Client;
import gui.login.FirstFrame;
import shared.GreenProtocol;
import shared.User;

public class AdminFrame extends JFrame {
	public String name = "";
	public int id = 0;
	public String pw = "";
	
	private JPanel pnlBig = new JPanel();

	public AdminFrame(User user, List<User> Teacherlist, ObjectOutputStream oos) {

		JPanel pnlBtn = new JPanel();
		JButton btnAdd = new JButton("추가");
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new AddTeacher();
				Client.service.wantTeacherList();
				try {
					Thread.sleep(700);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				JOptionPane.showMessageDialog(AdminFrame.this, name + " 선생님의 ID는 " + id + ", PW는 " + pw + " 입니다.");
				makeTeacherList(Client.TeacherList);
				revalidate();
				repaint();
				name = "";
				id = 0;
				pw = "";
			}
		});
		JButton btnLogout = new JButton("로그아웃");
		btnLogout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Client.setff(new FirstFrame(oos)); // 9/6수정
				dispose();
			}
		});
		pnlBtn.add(btnAdd);
		pnlBtn.add(btnLogout);

		makeTeacherList(Teacherlist);

		add(pnlBtn, "North");
		add(pnlBig);

		showGUI();

		addWindowListener(new WindowAdapter() {
			@Override // 9/6수정
			public void windowClosing(WindowEvent e) {
				int result = JOptionPane.showConfirmDialog(AdminFrame.this, "정말로 종료 하시겠습니까?", "",
						JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					Client.service.ExitGUI();
					System.exit(0);
				}
			}
		});
	}

	private void showGUI() {
		setSize(400, 500);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setVisible(true);
	}

	public void makeTeacherList(List<User> TeacherList) {
		pnlBig.removeAll();
		List<User> Teacherlist = new ArrayList<>();
		Teacherlist = TeacherList;
		pnlBig.setLayout(new GridLayout(0, 1));
		for (int i = 0; i < Teacherlist.size(); i++) {
			User teacher = Teacherlist.get(i);
			JPanel pnlTeacher = new JPanel();
			JLabel lblId = new JLabel();
			lblId.setText(String.valueOf(teacher.getId()));
			JLabel lblName = new JLabel();
			lblName.setText(teacher.getName());
			JButton btnDelete = new JButton("삭제");
			btnDelete.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int y = JOptionPane.showConfirmDialog(null, "삭제하시겠습니까?", "삭제확인", JOptionPane.YES_NO_OPTION);

					if (y == 0) {
						User wantTeacherDelete = new User();
						wantTeacherDelete.setProtocol(GreenProtocol.DELETE_TEACHER);
						wantTeacherDelete.setId(Integer.valueOf(lblId.getText()));
						Client.service.deleteTeacher(wantTeacherDelete);
						try {
							Thread.sleep(700);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
						makeTeacherList(Client.TeacherList);
						revalidate();
						repaint();
					}
				}
			});

			pnlTeacher.add(lblId);
			pnlTeacher.add(lblName);
			pnlTeacher.add(btnDelete);

			pnlBig.add(pnlTeacher);
		}
	}
}
