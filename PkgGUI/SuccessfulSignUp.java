package PkgGUI;

import javax.swing.*;
import java.awt.*;

public class SuccessfulSignUp {
    JFrame frame = new JFrame("Successful Sign Up");
    JPanel mainPanel = new JPanel();
    JLabel success = new JLabel("You have signed up successfully!");
    JButton loginBtn = new JButton("Log in");
    
    public SuccessfulSignUp(){
        //=====FRAME=======
        frame.setSize(500, 700);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new GridBagLayout());

        //==========MAIN PANEL========
        mainPanel.setLayout(new BorderLayout());
        
        //=======LABEL======
        success.setHorizontalAlignment(SwingConstants.CENTER);
        success.setFont(new Font("SansSarif", Font.BOLD, 25));
        
        //=========LOGIN BUTTON=======
        loginBtn.setBackground(new Color(0, 120, 215));
        loginBtn.setForeground(Color.white);
        loginBtn.setPreferredSize(new Dimension(150, 40));
        loginBtn.setFocusable(false);

        //========ADDING COMPONENTS TO MAIN FRAME=======
        mainPanel.add(success, BorderLayout.NORTH);
        mainPanel.add(loginBtn,BorderLayout.SOUTH);

        //=======ADDING MAIN FRAME TO ORIGINAL FRAME AND SETTING THE ORIGINAL FRAME VISIBLE =====
        frame.add(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        //======ACTION PERFOMED WHEN LOGIN BUTTON IS CLICKED
        loginBtn.addActionListener(e -> {
            frame.dispose();
            new LogIn();          
        });
    }
}
