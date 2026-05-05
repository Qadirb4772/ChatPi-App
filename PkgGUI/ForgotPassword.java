package PkgGUI;

import javax.swing.*;
import java.awt.*;
import JDBC.*;
import java.util.Arrays;
public class ForgotPassword {
    JFrame frame = new JFrame("Forgot Password");
    JPanel forgotPasswordPanel = new JPanel();
    JLabel usernameLabel = new JLabel("Username");
    JTextField usernameField = new JTextField(20);
    JLabel passwordLabel = new JLabel("New Password");
    JPasswordField passwordField = new JPasswordField(20);
    JLabel confirmPasswordLabel = new JLabel("Confirm Password");
    JPasswordField confirmPasswordField = new JPasswordField(20);
    JButton changePassword = new JButton("Change Password");
    JButton back = new JButton("Back");

    public ForgotPassword(){
        frame.setSize(500, 600);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

        forgotPasswordPanel.setLayout(null);
        forgotPasswordPanel.setBounds(30, 30, 400, 500);
        forgotPasswordPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2, true));
        forgotPasswordPanel.setBackground(Color.WHITE);

        //=========USERNAME===========
        usernameLabel.setBounds(25, 80, 150, 20);
        usernameLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        usernameField.setBounds(30, 105, 340, 30);

        //=======NEW PASSWORD==========
        passwordLabel.setBounds(30, 150, 150, 20);
        passwordLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        passwordField.setBounds(30, 175, 340, 30);
        
        //=======CONFIRM PASSWORD========
        confirmPasswordLabel.setBounds(30, 210, 150, 20);
        confirmPasswordLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        confirmPasswordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        confirmPasswordField.setBounds(30, 235, 340, 30);

        //==========BUTTONS============
        changePassword.setBounds(30, 300, 340, 40);
        changePassword.setBackground(new Color(0, 120, 215));
        changePassword.setForeground(Color.WHITE);
        changePassword.setFocusable(false);
        back.setBounds(30, 360, 340, 40);
        back.setBackground(new Color(0, 120, 215));
        back.setForeground(Color.WHITE);
        back.setFocusable(false);

        //========ADDING ALL COMPONENTS TO PANEL=====
        forgotPasswordPanel.add(usernameLabel);
        forgotPasswordPanel.add(usernameField);
        forgotPasswordPanel.add(passwordLabel);
        forgotPasswordPanel.add(passwordField);
        forgotPasswordPanel.add(confirmPasswordLabel);
        forgotPasswordPanel.add(confirmPasswordField);
        forgotPasswordPanel.add(changePassword);
        forgotPasswordPanel.add(back);

        //====ACTION DONE WHEN BACK BUTTON IS CLICKED
        back.addActionListener(e -> {
            frame.dispose();
            new LogIn();
        });

        //===== ACTION PERFORMED WHEN CHANGE PASSWORD BUTTON IS CLICKED ====
        changePassword.addActionListener(e -> {
            String username = usernameField.getText();
            char[] password = passwordField.getPassword();
            char[] confirmPassword = confirmPasswordField.getPassword();
            if(password.length >= 10 && Arrays.equals(password, confirmPassword)){
                if(DataUpdation.updatePassword(username, String.valueOf(password).hashCode())){
                        frame.dispose();
                        new PasswordChanged();
                }else{
                    JOptionPane.showMessageDialog(frame, "Invalid Username", "Error", JOptionPane.WARNING_MESSAGE);
                }
            }else if(password.length < 10){
                JOptionPane.showMessageDialog(frame, "Passwords should be at least 10 characters", "Error!!",JOptionPane.WARNING_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(frame, "Passwords do not match", "Error", JOptionPane.WARNING_MESSAGE);
            }
            
        });
        //====ADDING PANEL TO FRAME AND SETTING FRAME VISIBLE===
        frame.add(forgotPasswordPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
