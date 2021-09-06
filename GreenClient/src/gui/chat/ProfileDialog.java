package gui.chat;

import java.awt.Font;
import java.awt.Image;

import javax.swing.*;

import shared.User;

// 목록에서 아이디 눌렸을때 뜨는 프로필창
public class ProfileDialog extends JDialog {
   private Font font25 = new Font("고딕", Font.PLAIN, 25);
   private Font font15 = new Font("고딕", Font.PLAIN, 15);

   public ProfileDialog(User user) {
     //9/6
      JPanel mainPnl = new JPanel();
      mainPnl.setLayout(new BoxLayout(mainPnl, BoxLayout.Y_AXIS));
      Image image = user.getPhoto();
      Image scale = image.getScaledInstance(200, 200, Image.SCALE_DEFAULT);
      JPanel photoPnl = new JPanel();

      JLabel photoLbl = new JLabel();
      photoLbl.setIcon(new ImageIcon(scale));

      photoPnl.add(photoLbl);
      mainPnl.add(photoPnl);

      JPanel userNamePnl = new JPanel();
      JLabel userNameLbl = new JLabel(user.getName());
      userNameLbl.setFont(font25);
      userNamePnl.add(userNameLbl);
      mainPnl.add(userNamePnl);

      JPanel userStatePnl = new JPanel();
      JLabel userStateLbl = new JLabel(user.getMyMessage());
      userStateLbl.setFont(font15);
      userStatePnl.add(userStateLbl);
      mainPnl.add(userStatePnl);

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