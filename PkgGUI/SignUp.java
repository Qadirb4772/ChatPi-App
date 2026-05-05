package PkgGUI;

import java.awt.*;
import javax.swing.*;
import PkgLogic.*;
import JDBC.*;
import java.util.Arrays;
public class SignUp {
    private JFrame frame = new JFrame("Sign Up Page");
    private JPanel card = new JPanel();
    private JLabel title = new JLabel("Sign Up");
    private JLabel fNameL = new JLabel("First name");
    private JTextField fNameF = new JTextField();
    private JLabel lNameL = new JLabel("Last Name");
    private JTextField lNameF = new JTextField();
    private JLabel usernameL = new JLabel("Username");
    private JTextField usernameF = new JTextField();
    private JLabel emailL = new JLabel("Email");
    private JTextField emailF = new JTextField();
    private JLabel phoneNoL = new JLabel("Phone Number");
    private JTextField phoneNoF = new JTextField();
    private JLabel passwordL = new JLabel("Password");
    private JPasswordField passwordF = new JPasswordField();
    private JLabel confrmPasswordL = new JLabel("Confirm Password");
    private JPasswordField confrmPasswordF = new JPasswordField();
    private JButton signUpBtn = new JButton("Sign Up");
    private JButton loginBtn = new JButton("Login");
    
   public SignUp() {
        
        frame.setSize(500, 700);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new GridBagLayout());
        
        
        card.setPreferredSize(new Dimension(400, 600));
        card.setLayout(null);
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2, true));

        
        title.setBounds(100, 20, 200, 40);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setHorizontalAlignment(SwingConstants.CENTER);


        
        fNameL.setBounds(30, 80, 150, 20);
        fNameF.setBounds(30, 105, 150, 30);
       
        
        lNameL.setBounds(220, 80, 150, 20);
        lNameF.setBounds(220, 105, 150, 30);

        
        usernameL.setBounds(30, 150, 150, 20);
        usernameF.setBounds(30, 175, 340, 30);

        
        emailL.setBounds(30, 210, 150, 20);
        emailF.setBounds(30, 235, 340, 30);

        
        phoneNoL.setBounds(30, 270, 150, 20);
        phoneNoF.setBounds(30, 295, 340, 30);

        
        passwordL.setBounds(30, 330, 150, 20);
        passwordF.setBounds(30, 355, 340, 30);

        

        confrmPasswordL.setBounds(30, 390, 150, 20);
        confrmPasswordF.setBounds(30, 415, 340, 30);

        
        signUpBtn.setBounds(30, 470, 340, 40);
        signUpBtn.setBackground(new Color(0, 120, 215));
        signUpBtn.setForeground(Color.WHITE);
        signUpBtn.setFont(new Font("SansSerif", Font.BOLD, 16));

        
        loginBtn.setBounds(30, 530, 340, 40);
        loginBtn.setBackground(new Color(0, 120, 215));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFont(new Font("SansSerif", Font.BOLD, 16));

        card.add(loginBtn);
        card.add(signUpBtn);
        card.add(passwordL);
        card.add(passwordF);
        card.add(confrmPasswordL);
        card.add(confrmPasswordF);
        card.add(emailL);
        card.add(emailF);
        card.add(phoneNoF);
        card.add(phoneNoL);
        card.add(usernameF);
        card.add(usernameL);
        card.add(lNameF);
        card.add(lNameL);
        card.add(title);
        card.add(fNameL);
        card.add(fNameF);

        frame.add(card);
        
        loginBtn.addActionListener(e -> {
            frame.dispose();
            new LogIn();
        });

        signUpBtn.addActionListener(e -> {
            String fName = fNameF.getText().trim();
            String lName = lNameF.getText().trim();
            String username = usernameF.getText().trim();
            String phoneNumber = phoneNoF.getText().trim();
            String email = emailF.getText().trim();
            char[] password = passwordF.getPassword();
            char[] confirmPassword = confrmPasswordF.getPassword();
            boolean isAllFilled = ((fName.isEmpty()) || 
            (lName.isEmpty()) ||
             (username.isEmpty()) || 
             (email.isEmpty()) || (phoneNumber.isEmpty()) ||
             (password.length == 0));

            if(isAllFilled){
                JOptionPane.showMessageDialog(frame, "Please fill all fields", "Error!!!", JOptionPane.ERROR_MESSAGE);
            }
            if(!(email.contains("@gmail.com"))){
                JOptionPane.showMessageDialog(frame, "Invalid Email", "Error!", JOptionPane.ERROR_MESSAGE);
            }
            if(password.length < 10){
                JOptionPane.showMessageDialog(frame, "Password Should be at least 10 characters", "Error!", JOptionPane.ERROR_MESSAGE);
            }
            if(!(Arrays.equals(password, confirmPassword))){
                JOptionPane.showMessageDialog(frame, "Passwords Don't match", "Password Mismatch!", JOptionPane.ERROR_MESSAGE);
            }
            else if(!isAllFilled && (email.contains("@gmail.com")) && (password.length >= 10) && (Arrays.equals(password, confirmPassword))){
                User user = new User( fName, lName, username, email, phoneNumber, password);
                boolean isOkay = InsertionInDB.insertData(user.getFirstName(), 
                user.getLastName(), user.getUsername(), 
                user.getEmail(), user.getPhoneNumber(), 
                String.valueOf(user.getPassword()).hashCode());


                if(isOkay){
                    frame.dispose();
                    new SuccessfulSignUp();
                }
            }
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    
    }
}