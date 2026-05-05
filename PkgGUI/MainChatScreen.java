package PkgGUI;

import javax.swing.*;
import java.awt.*;

public class MainChatScreen {
    JFrame mainChatScreen = new JFrame("ChatPi Main Chat Screen");
    JPanel mainChatPanel = new JPanel(new BorderLayout());
    JPanel btnsPanel = new JPanel(null);
    JLabel label = new JLabel();

    

    public MainChatScreen(){
        mainChatScreen.setSize(500, 500);
        mainChatScreen.setLocationRelativeTo(null);
        mainChatScreen.setLayout(new BorderLayout());
        label.setText("Welcome "+LogIn.getFirstName());
        label.setFont(new Font("Calibri", Font.BOLD, 35));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        
        try {
            Icon createIcon = new ImageIcon(getClass().getResource("/src/Create32.png"));
            Icon showIcon = new ImageIcon(getClass().getResource("/src/History32.png"));
            JButton createChatBtn = new JButton("Start Chat");
            createChatBtn.setFont(new Font("Arial", Font.BOLD, 10));
            createChatBtn.setForeground(Color.WHITE);
            createChatBtn.setFocusable(false);
            createChatBtn.setLayout(new BorderLayout());
            JButton showChatBtn = new JButton("Show Chats");
            showChatBtn.setFont(new Font("Calibri", Font.BOLD, 10));
            showChatBtn.setForeground(Color.WHITE);
            showChatBtn.setFocusable(false);


            showChatBtn.setIconTextGap(10);
            btnsPanel.setPreferredSize(new Dimension(200, 200));
            createChatBtn.setBackground(new Color(0, 120, 25));
            createChatBtn.setPreferredSize(new Dimension(100, 100));
            showChatBtn.setBackground(new Color(0, 120, 25));


            
            createChatBtn.setIcon(createIcon);
            showChatBtn.setIcon(showIcon);

            createChatBtn.setBounds(25, 25, 150, 50);
            showChatBtn.setBounds(25, 100, 150, 50);

            btnsPanel.add(createChatBtn, BorderLayout.NORTH);
            btnsPanel.add(showChatBtn, BorderLayout.SOUTH);
            mainChatPanel.add(btnsPanel, BorderLayout.CENTER);
            mainChatScreen.add(mainChatPanel, BorderLayout.CENTER);
            mainChatScreen.add(label, BorderLayout.NORTH);
            
            mainChatScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainChatScreen.setVisible(true);
        }catch(Exception e){
            JOptionPane.showMessageDialog(mainChatScreen, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
