package gui.mainpage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.MaskFormatter;

import client.Client;
import shared.User;

public class Mypage extends JPanel {
	private JTextField tfName;
	private JTextField tfMyMessage;
	private JPasswordField tfPw;
	private JFormattedTextField tfPhone;
	private JLabel lblProfile;

	private Image scale1;
	private Image scale2;
	private BufferedImage imBuff;
	private File choosefile = null;
	private String path;
	private User user;

	private MouseAdapter ma;

	public Mypage(User user) {
		this.user = user;
		JPanel pnlAll = new JPanel();

		JPanel pnlImage = new JPanel();
		Image image = user.getPhoto();
		scale1 = image.getScaledInstance(120, 120, Image.SCALE_DEFAULT);
		lblProfile = new JLabel();
		lblProfile.setIcon(new ImageIcon(scale1));
		lblProfile.setPreferredSize(new Dimension(120, 120));
		lblProfile.setMaximumSize(new Dimension(120, 120));
		lblProfile.setMinimumSize(new Dimension(120, 120));
		pnlImage.add(lblProfile);

		JPanel pnlInfo = new JPanel();

		JPanel pnlName = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lblName = new JLabel("이름");
		tfName = new JTextField(15);
		tfName.setText(user.getName());
		tfName.setEnabled(false);
		pnlName.add(lblName);
		pnlName.add(tfName);

		JPanel pnlMyMessage = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lblMyMessage = new JLabel("상메");
		tfMyMessage = new JTextField(15);
		tfMyMessage.setText(user.getMyMessage());
		tfMyMessage.setEnabled(false);
		pnlMyMessage.add(lblMyMessage);
		pnlMyMessage.add(tfMyMessage);

		JPanel pnlId = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lblId1 = new JLabel("아이디");
		lblId1.setForeground(new Color(100, 100, 100));
		JLabel lblId2 = new JLabel();
		lblId2.setText(String.valueOf(user.getId()));
		pnlId.add(lblId1);
		pnlId.add(lblId2);

		JPanel pnlPw = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lblPw = new JLabel("비밀번호");
		lblPw.setForeground(new Color(100, 100, 100));
		tfPw = new JPasswordField(15);
		tfPw.setText(user.getPassword());
		tfPw.setEnabled(false);
		pnlPw.add(lblPw);
		pnlPw.add(tfPw);

		JPanel pnlPhone = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lblPhone = new JLabel("전화번호");
		lblPhone.setForeground(new Color(100, 100, 100));
		MaskFormatter formatterPhone = null;
		try {
			formatterPhone = new MaskFormatter("010-####-####");
			formatterPhone.setPlaceholderCharacter('_');
		} catch (ParseException e) {
			e.printStackTrace();
		}
		tfPhone = new JFormattedTextField(formatterPhone);
		tfPhone.setText(user.getPhone());
		tfPhone.setEnabled(false);
		pnlPhone.add(lblPhone);
		pnlPhone.add(tfPhone);

		JPanel pnlBirth = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lblBirth1 = new JLabel("생년월일");
		lblBirth1.setForeground(new Color(100, 100, 100));
		JLabel lblBirth2 = new JLabel();
		lblBirth2.setText(user.getBirth());
		pnlBirth.add(lblBirth1);
		pnlBirth.add(lblBirth2);

		JPanel pnlSubject = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lblSubject1 = new JLabel("과정");
		lblSubject1.setForeground(new Color(100, 100, 100));
		JLabel lblSubject2 = new JLabel();
		lblSubject2.setText(Client.getSubjectString(user.getSubject()));
		pnlSubject.add(lblSubject1);
		pnlSubject.add(lblSubject2);

		pnlInfo.setLayout(new BoxLayout(pnlInfo, BoxLayout.Y_AXIS));
		pnlInfo.add(pnlName);
		pnlInfo.add(pnlMyMessage);
		pnlInfo.add(pnlId);
		pnlInfo.add(pnlPw);
		pnlInfo.add(pnlPhone);
		pnlInfo.add(pnlBirth);
		pnlInfo.add(pnlSubject);

		JPanel pnlBtn = new JPanel();
		JButton btnModify = new JButton("수정");
		JButton btnOk = new JButton("확인");
		btnOk.setEnabled(false);

		btnModify.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 9/6
//				System.out.println(Client.mf.mcdMap.size());
				if ((user.isTeacher() && Client.mf.mcdMap.size() == 0) || !user.isTeacher())  {
					if (btnModify.getText().equals("수정")) {
						btnModify.setText("취소");
						btnOk.setEnabled(true);

						tfName.setEnabled(true);
						tfMyMessage.setEnabled(true);
						tfPw.setEnabled(true);
						tfPhone.setEnabled(true);
						ma = new MouseAdapter() {
							JFileChooser chooseImage = new JFileChooser();

							@Override
							public void mouseClicked(MouseEvent e) {
								chooseImage.setCurrentDirectory(new File("C:\\Users\\namki\\Desktop"));

								FileNameExtensionFilter ff = new FileNameExtensionFilter("이미지 파일", "jpg", "png");
								chooseImage.setFileFilter(ff);

								// "모든 파일" 텝 없애는 방법
								chooseImage.setAcceptAllFileFilterUsed(false);
								// 파일 여러개 선택하거나 하나만 선택할 수 있게 하는 방법
								chooseImage.setMultiSelectionEnabled(false);

								int result = chooseImage.showSaveDialog(null);
								if (result == JFileChooser.APPROVE_OPTION) {
									choosefile = chooseImage.getSelectedFile();
									path = choosefile.getAbsolutePath();

									imBuff = null;
									lblProfile.setIcon(new ImageIcon(scale1));
									try {
										imBuff = ImageIO.read(choosefile);
										scale2 = imBuff.getScaledInstance(120, 120, Image.SCALE_DEFAULT);
										lblProfile.setIcon(new ImageIcon(scale2));
									} catch (IOException e1) {
										e1.printStackTrace();
									}
								}
							}
						};
						lblProfile.addMouseListener(ma);
					} else if (btnModify.getText().equals("취소")) {
						btnModify.setText("수정");
						btnOk.setEnabled(false);

						tfName.setEnabled(false);
						tfMyMessage.setEnabled(false);
						tfPw.setEnabled(false);
						tfPhone.setEnabled(false);

						lblProfile.removeMouseListener(ma);
						Client.service.repaintMyProfile(user);
						try {
							Thread.sleep(700);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}

						tfName.setText(user.getName());
						tfMyMessage.setText(user.getMyMessage());
						tfPw.setText(user.getPassword());
						tfPhone.setText(user.getPhone());

						Image image = user.getPhoto();
						scale1 = image.getScaledInstance(120, 120, Image.SCALE_DEFAULT);
						lblProfile.setIcon(new ImageIcon(scale1));

						revalidate();
						repaint();
					}
				} else {
					JOptionPane.showMessageDialog(Mypage.this, "채팅창을 종료 후 수정해주세요");
				}
			}
		});
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (choosefile == null) {
					Client.service.changeUserInformation(user);
				} else {
					Client.service.changeUserInformation(user, choosefile);
				}
				try {
					Thread.sleep(700);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				tfName.setEnabled(false);
				tfMyMessage.setEnabled(false);
				tfPw.setEnabled(false);
				tfPhone.setEnabled(false);
				lblProfile.removeMouseListener(ma);
				btnModify.setText("수정");
				btnOk.setEnabled(false);
				revalidate();
				repaint();
			}
		});
		pnlBtn.add(btnModify);
		pnlBtn.add(btnOk);

		pnlAll.setLayout(new BoxLayout(pnlAll, BoxLayout.Y_AXIS));
		pnlAll.add(pnlImage);
		pnlAll.add(pnlInfo);
		pnlAll.add(pnlBtn);

		add(pnlAll);
	}

	public JTextField getTfName() {
		return tfName;
	}

	public JTextField getTfMyMessage() {
		return tfMyMessage;
	}

	public JPasswordField getTfPw() {
		return tfPw;
	}

	public JFormattedTextField getTfPhone() {
		return tfPhone;
	}

	public JLabel getLblProfile() {
		return lblProfile;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
