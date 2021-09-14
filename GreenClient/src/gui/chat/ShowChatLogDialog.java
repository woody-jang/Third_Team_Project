package gui.chat;

import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;

public class ShowChatLogDialog extends JDialog {
	public ShowChatLogDialog(String chatLog, String chatTitle) {
		setTitle(chatTitle);
		JPanel mainPnl = new JPanel();
		mainPnl.setLayout(new BoxLayout(mainPnl, BoxLayout.Y_AXIS));

		JTextArea ta = new JTextArea();
		JScrollPane scrl = new JScrollPane(ta);
		String[] chatLogArray = chatLog.split("[.]");
		StringBuilder sb = new StringBuilder();

		if (chatLog.equals("")) {
			sb.append("");
		} else {
			for (String str1 : chatLogArray) {
				String[] strs = str1.split(",");
				sb.append(strs[0] + " : ");
				sb.append(strs[1] + ",   ");
				sb.append(strs[2] + "\n");
			}
		}

		ta.setText(sb.toString());
		ta.setEditable(false);
		mainPnl.add(scrl);

		JButton saveBtn = new JButton("저장");
		saveBtn.setFont(new Font("굴림", Font.PLAIN, 15));
		saveBtn.setMargin(new Insets(0, 30, 0, 30));
		saveBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileFilter(new FileFilter() {
					@Override
					public boolean accept(File f) {
						if (f.isDirectory()) {
							return true;
						}
						return f.getName().endsWith("txt");
					}

					@Override
					public String getDescription() {
						return "텍스트 (*.txt)";
					}
				});
				chooser.setAcceptAllFileFilterUsed(false);
				chooser.setCurrentDirectory(new File("."));

				int i = chooser.showSaveDialog(ShowChatLogDialog.this);

				if (i == JFileChooser.APPROVE_OPTION) {
					File file = chooser.getSelectedFile();
					PrintWriter pw = null;
					try {
						pw = new PrintWriter(file + ".txt");
						pw.print(sb.toString());
						pw.flush();
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} finally {
						if (pw != null) {
							pw.close();
						}
					}
				}
			}
		});

		mainPnl.add(saveBtn);

		add(mainPnl);

		setModal(true);
		setSize(500, 300);
		setLocation(600, 450);
		setVisible(true);
	}
}