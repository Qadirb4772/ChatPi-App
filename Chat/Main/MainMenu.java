package Chat.Main;

import Chat.App.ChatHistory;
import Chat.App.GroupChat;
import Chat.App.GroupChatHistory;
import Chat.App.LogOut;
import Chat.App.TwoPerson;
import Chat.DB.DBConnection;
import PkgGUI.LogIn;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class MainMenu {

    public MainMenu(){

        // ================ Verify DB CONNECTION ================
        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            JOptionPane.showMessageDialog(null,
                    "Database cannot connected.\nPlease check DBConnection setting.",
                    "Startup Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        // ================ SHOW MENU ===================
        SwingUtilities.invokeLater(MainMenu::showMain);
    }

    // ================= MAIN MENU =================
    private static void showMain() {

        TwoPerson twoPerson = new TwoPerson();
        JFrame menuFrame = new JFrame("ChatPi");
        menuFrame.setSize(420, 520);
        menuFrame.setLayout(null);
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setLocationRelativeTo(null);
        menuFrame.getContentPane().setBackground(Color.LIGHT_GRAY);

        //================ WELCOME LABEL FOR USER ==================
        JLabel wlcmLabel = new JLabel("Welcome "+LogIn.getFirstName(), SwingConstants.CENTER);
        wlcmLabel.setBounds(430, 50, 400, 50);
        wlcmLabel.setFont(new Font("Arial", Font.BOLD, 35));
        // =============== WELCOME TO CHATPI TEXT ==================
        JLabel appTitle = new JLabel("Welcome to ChatPi", SwingConstants.CENTER);
        appTitle.setFont(new Font("Arial", Font.BOLD, 27));
        appTitle.setForeground(Color.BLACK);
        appTitle.setBounds(420, 100, 420, 50); // Poori width mein center hoga

        menuFrame.add(appTitle);
        // ================== BUTTON PANEL ====================
        JPanel btnPanel = new JPanel(null);
        btnPanel.setBackground(Color.RED);
        btnPanel.setBounds(480, 190, 320, 350);

        // ============= BUTTON TWO PERSON CHAT =============
        JButton btn1 = new JButton("Two Person Chat");
        btn1.setBounds(10, 70, 300, 45);
        btn1.setBackground(Color.BLACK);
        btn1.setForeground(Color.WHITE);
        btn1.setFocusable(false);
        btn1.addActionListener(e -> twoPerson.openWindow());
        btnPanel.add(btn1);

        // ============= BUTTON GROUP CHAT =============
        JButton btn2 = new JButton("Group Chat");
        btn2.setBounds(10, 125, 300, 45);
        btn2.setBackground(Color.BLACK);
        btn2.setForeground(Color.WHITE);
        btn2.setFocusable(false);
        btn2.addActionListener(e -> GroupChat.launch());
        btnPanel.add(btn2);

        // ============= BUTTON CHAT HISTORY =============
        JButton btn3 = new JButton("Chat History");
        btn3.setBounds(10, 180, 300, 45);
        btn3.setBackground(Color.BLACK);
        btn3.setForeground(Color.WHITE);
        btn3.setFocusable(false);
        btn3.addActionListener(e -> ChatHistory.launchGUI());
        btnPanel.add(btn3);

        // ============= BUTTON GROUP CHAT HISTORY =============
        JButton btn4 = new JButton("Group Chat History");
        btn4.setBounds(10, 235, 300, 45);
        btn4.setBackground(Color.BLACK);
        btn4.setForeground(Color.WHITE);
        btn4.setFocusable(false);
        btn4.addActionListener(e -> GroupChatHistory.launchGUI());
        btnPanel.add(btn4);

        // ============= BUTTON LOGOUT =============
        JButton btn5 = new JButton("LogOut");
        btn5.setBounds(10, 290, 300, 45);
        btn5.setBackground(Color.BLACK);
        btn5.setForeground(Color.WHITE);
        btn5.setFocusable(false);
        btn5.addActionListener(e -> {
            LogOut.launchGUI();
            menuFrame.dispose();
        });
        btnPanel.add(btn5);

        menuFrame.add(btnPanel);
        menuFrame.add(wlcmLabel);
        menuFrame.setVisible(true);
    }
}