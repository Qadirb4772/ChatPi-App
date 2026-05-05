package PkgGUI;

import javax.swing.*;
import java.awt.*;
import JDBC.DataRetrieval;
// import Zafran.*;
public class LogIn {
    private JFrame loginFrame = new JFrame("Log In");
    private JLabel logIn = new JLabel("Log In");
    private JPanel loginPanel = new JPanel(new BorderLayout());
    private JLabel usernameL = new JLabel("Username");
    private static JTextField usernameF = new JTextField(25);
    private static JLabel passwordL = new JLabel("Password");
    private static JPasswordField passwordF = new JPasswordField();
    private JPanel logInForm = new JPanel();
    private JButton loginBtn = new JButton("Log In");
    private JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
    private JButton signUpBtn = new JButton("Sign Up");
    private JPanel mainPanel = new JPanel(new BorderLayout());
    private JButton forgetPasswordBtn = new JButton("Forget Password?");

    public LogIn() {
       
        //====== LOG IN FRAME =========
        loginFrame.setSize(500, 700);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setLayout(new GridBagLayout());

        //====== LOG IN LABEL ==========
        logIn.setHorizontalAlignment(SwingConstants.CENTER);
        logIn.setFont(new Font("Arial", Font.PLAIN, 25));
        loginPanel.add(logIn, BorderLayout.CENTER);
        
        //======= USERNAME LABEL + FIELD =======
        usernameL.setBounds(70, 10, 100, 75);
        usernameF.setBounds(50, 60, 240, 45);
        passwordL.setBounds(70, 95, 100, 75);
        passwordF.setBounds(50, 150, 240, 45);

        //======= ADDING COMPONENTS TO LOGIN FORM ====
        logInForm.setPreferredSize(new Dimension(300, 400));
        logInForm.setLayout(null);
        logInForm.add(usernameL);
        logInForm.add(usernameF);
        logInForm.add(passwordL);
        logInForm.add(passwordF);

        //====== LOGIN BUTTON ======
        loginBtn.setBackground(new Color(0, 120, 215));
        loginBtn.setForeground(Color.white);
        
        //====== SIGN UP BUTTON ======
        signUpBtn.setBackground(new Color(0, 120, 215));
        signUpBtn.setForeground(Color.WHITE);

        //===== FORGOT PASSWORD BUTTON ======
        forgetPasswordBtn.setBackground(new Color(0, 120, 215));
        forgetPasswordBtn.setForeground(Color.WHITE);
        forgetPasswordBtn.setFocusable(false);
    
        //===== BUTTONS PANEL =======
        btnPanel.setPreferredSize(new Dimension(150, 150));
        
        //===== ADDING COMPONENTS TO BUTTONS PANEL =====
        btnPanel.add(loginBtn);
        btnPanel.add(signUpBtn);
        btnPanel.add(forgetPasswordBtn);

        //====== MAIN PANEL ========
        mainPanel.setBackground(Color.gray);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        mainPanel.setPreferredSize(new Dimension(350, 400));
        mainPanel.add(loginPanel, BorderLayout.NORTH);
        mainPanel.add(logInForm, BorderLayout.CENTER);
        mainPanel.add(btnPanel, BorderLayout.SOUTH);

        //======= ACTION PERFORMED WHEN SIGN UP BUTTON IS CLICKED ====
        signUpBtn.addActionListener(e -> {
            loginFrame.dispose();
            new SignUp();
        });

        //====== ACTION PERFORMED WHEN LOGIN BUTTON IS CLICKED ====
        loginBtn.addActionListener(e -> {
            String username = usernameF.getText();
            int password = String.valueOf(passwordF.getPassword()).hashCode();

            boolean areCredentialsCorrect = DataRetrieval.getData(username, password);
            if(areCredentialsCorrect){
                loginFrame.dispose();
                new Chat.Main.TestMain();
            }else{
                JOptionPane.showMessageDialog(mainPanel, "Wrong Credentials", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        //====== ACTION PERFORMED WHEN FORGOT PASSWORD BUTTON IS CLICKED =====
        forgetPasswordBtn.addActionListener(e -> {
            loginFrame.dispose();
            new ForgotPassword();
        });

        //===== ADDING MAIN PANEL TO FRAME AND SETTING THE FRAME VISIBLE =====
        loginFrame.add(mainPanel);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setVisible(true);
    }

    // ===== STATIC METHOD FOR GETTING FIRST NAME BECAUSE IT WAS USED SOMEWHERE IN THE MAIN CHATSCREEN ===
    public static String getFirstName(){
        return DataRetrieval.getFirstName(usernameF.getText());
    }

    public static String getUsername(){
        return usernameF.getText();
    }
}