package gui.chat;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import shared.ChatMessage;
import shared.GreenProtocol;
import shared.User;

public class Emoticondialog extends JDialog {

	User user;
	ObjectOutputStream oos;

	public Emoticondialog(User user, int subjectCode, ObjectOutputStream oos) {

		this.user = user;
		this.oos = oos;

		JPanel pnl = new JPanel();
		pnl.setLayout(new GridLayout(0, 3));
		setModal(true);

		String[] emoticonpath = new String[13];

		emoticonpath[0] = "emoticon/222.jpg";
		emoticonpath[1] = "emoticon/75.jpg";
		emoticonpath[2] = "emoticon/기분이조크든요.jpg";
		emoticonpath[3] = "emoticon/망 이모티콘.jpg";
		emoticonpath[4] = "emoticon/몰루.png";
		emoticonpath[5] = "emoticon/뭐.라고.jpg";
		emoticonpath[6] = "emoticon/비둘기.jpg";
		emoticonpath[7] = "emoticon/선생님..모르겠는데요...jpg";
		emoticonpath[8] = "emoticon/의욕 이모티콘.jpg";
		emoticonpath[9] = "emoticon/ok.jpg";
		emoticonpath[10] = "emoticon/wtf.jpg";
		emoticonpath[11] = "emoticon/A36A9B3C-9CC7-4DD8-8C22-DE52FD73AF0B.gif";
		emoticonpath[12] = "emoticon/BEF27F18-4BCE-4AA2-868C-3128BAD6C845.gif";

		for (int i = 0; i < emoticonpath.length; i++) {
			ImageIcon img = null;
			Image beforeImg = new ImageIcon(emoticonpath[i]).getImage();
			Image afterImg = beforeImg.getScaledInstance(200, 200, Image.SCALE_SMOOTH);

			if (emoticonpath[i].endsWith(".gif"))
				img = new ImageIcon(beforeImg);
			else
				img = new ImageIcon(afterImg);
			JLabel lbl = new JLabel(img);
			lbl.setToolTipText(emoticonpath[i]);
			lbl.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {

					ChatMessage cm = new ChatMessage();
					cm.setProtocol(GreenProtocol.Emoticon);
					cm.setSendUser(user.getName());
					cm.setSubject(subjectCode);
					cm.setTime(LocalDateTime.now());
					cm.setEmopath(lbl.getToolTipText());

					try {
						oos.reset();
						oos.writeObject(cm);
						oos.flush();
					} catch (IOException e1) {
						e1.printStackTrace();
					}

					Emoticondialog.this.dispose();
				}

			});

			pnl.add(lbl);

		}

		add(pnl);

		pack();
		setVisible(true);

	}

}