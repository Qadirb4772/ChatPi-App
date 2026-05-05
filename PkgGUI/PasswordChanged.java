package PkgGUI;

import java.awt.*;

import javax.swing.*;

public class PasswordChanged {
    JFrame frame = new JFrame();
    JPanel panel = new JPanel(new BorderLayout(0, 0));
    JLabel label = new JLabel("Your Password Has Been Changed Successfully");
    JButton button = new JButton("Log In");

    PasswordChanged(){
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new GridBagLayout());

        label.setFont(new Font("Times", Font.ITALIC, 30));
        label.setHorizontalAlignment(SwingConstants.CENTER);

        button.setBackground(new Color(0, 120, 215));
        button.setForeground(Color.WHITE);

        panel.add(label, BorderLayout.NORTH);
        panel.add(button, BorderLayout.SOUTH);

        button.addActionListener(e -> {
            frame.dispose();
            new LogIn();
        });
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
