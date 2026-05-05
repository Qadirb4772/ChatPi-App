package PkgGUI;

import javax.swing.*;
import java.awt.*;


public class WelcomeScreen {
    private JFrame mainFrame = new JFrame();
    private JButton signUpButton = new JButton("Sign Up");
    private JButton loginButton = new JButton("Login");
    private JLabel welcomeLabel = new JLabel("Welcome to ChatPi App");
    private JPanel welcomeTxt = new JPanel(new BorderLayout());
    private JPanel welcomeCard = new JPanel();
    private JPanel buttonsPanel = new JPanel(new BorderLayout());
    private JPanel mainPanel = new JPanel(new GridLayout(2, 1, 10, 10));

    public WelcomeScreen() {
        
        mainFrame.setSize(520, 520);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setLayout(new GridBagLayout());

        try {
        ImageIcon logo = new ImageIcon(WelcomeScreen.class.getResource("Logo.png"));
        
        signUpButton.setBackground(new Color(0, 120, 215));
        signUpButton.setForeground(Color.WHITE);
        signUpButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        signUpButton.setFocusPainted(false);

        loginButton.setBackground(new Color(0, 120, 215));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        loginButton.setFocusPainted(false);
        
        
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 22));


        
        welcomeTxt.setBounds(130, 20, 260, 50);
        welcomeTxt.setBackground(Color.WHITE);
        welcomeTxt.add(welcomeLabel, BorderLayout.CENTER);

        
        
        welcomeCard.setBounds(110, 80, 300, 250);
        welcomeCard.setBackground(Color.WHITE);
        welcomeCard.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));

        
        buttonsPanel.setBounds(130, 350, 260, 100);
        buttonsPanel.setBackground(Color.WHITE);
        buttonsPanel.add(signUpButton, BorderLayout.NORTH);
        buttonsPanel.add(loginButton, BorderLayout.SOUTH);

        JLabel imgLabel = new JLabel(logo);
        imgLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeCard.add(imgLabel, BorderLayout.CENTER);

        
        mainPanel.setPreferredSize(new Dimension(480, 480));
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true));

        mainPanel.add(welcomeTxt);
        mainPanel.add(welcomeCard);
        mainPanel.add(buttonsPanel);

        
        mainFrame.add(mainPanel);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);

        signUpButton.addActionListener(e -> {
            mainFrame.dispose();
            new SignUp();
        });
        loginButton.addActionListener(e -> {
            mainFrame.dispose();
            new LogIn();
        });
        
        }catch (Exception e){
            System.out.println("Logo image not found.");
        }

    }
}
