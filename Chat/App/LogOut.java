package Chat.App;

import Chat.DB.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import PkgGUI.WelcomeScreen;
import PkgGUI.LogIn;

public class LogOut {

    // ================= LOGOUT LOGIC =================
    public static void performLogout(String username) {
        
        Connection conn = DBConnection.getConnection();

        // Update last_logout timestamp in DB if table exists
        if (conn != null) {
            String sql = "UPDATE users SET last_logout = NOW() WHERE username = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, username);
                ps.executeUpdate();
            } catch (SQLException e) {
                // Table may not exist yet — silently ignore
                System.out.println("Note: Could not update logout time. " + e.getMessage());
            }
        }

        System.out.println("LogOut Successfully: " + username);
        new WelcomeScreen();
        DBConnection.closeConnection();
    }

    // ================= LAUNCH GUI =================
    public static void launchGUI() {
        SwingUtilities.invokeLater(LogOutGUI::new);
    }

    // ================= GUI CLASS =================
    public static class LogOutGUI extends JFrame {

        public LogOutGUI() {
            setTitle("Logout");
            setSize(400, 400);
            setLayout(new BorderLayout());
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            getContentPane().setBackground(new Color(240, 240, 240));

            // ---- Center Panel ----
            JPanel centerPanel = new JPanel(new GridBagLayout());
            centerPanel.setBackground(new Color(240, 240, 240));

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.gridx = 0;

            JLabel iconLabel = new JLabel("👤", SwingConstants.CENTER);
            iconLabel.setFont(new Font("Arial", Font.PLAIN, 60));
            gbc.gridy = 0;
            centerPanel.add(iconLabel, gbc);

            JLabel titleLabel = new JLabel("Are you sure you want to logout?",
                                           SwingConstants.CENTER);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
            gbc.gridy = 1;
            centerPanel.add(titleLabel, gbc);

            JTextField usernameField = new JTextField(15);
            usernameField.setFont(new Font("Arial", Font.PLAIN, 13));
            usernameField.setBorder(BorderFactory.createTitledBorder("Username"));
            gbc.gridy = 2;
            centerPanel.add(usernameField, gbc);

            // ---- Buttons ----
            JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
            btnPanel.setBackground(new Color(240, 240, 240));

            JButton logoutBtn = new JButton("Logout");
            logoutBtn.setBackground(new Color(220, 53, 69));
            logoutBtn.setForeground(Color.WHITE);
            logoutBtn.setFont(new Font("Arial", Font.BOLD, 13));
            logoutBtn.setFocusPainted(false);
            logoutBtn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));

            JButton cancelBtn = new JButton("Cancel");
            cancelBtn.setBackground(new Color(108, 117, 125));
            cancelBtn.setForeground(Color.WHITE);
            cancelBtn.setFont(new Font("Arial", Font.BOLD, 13));
            cancelBtn.setFocusPainted(false);
            cancelBtn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));

            btnPanel.add(logoutBtn);
            btnPanel.add(cancelBtn);
            gbc.gridy = 3;
            centerPanel.add(btnPanel, gbc);

            add(centerPanel, BorderLayout.CENTER);

            // ---- Actions ----
            logoutBtn.addActionListener(e -> {
                String username = usernameField.getText().trim();
                
                if (username.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Please enter your username.", "Warning",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if(username.equals(LogIn.getUsername())){
                    performLogout(username);
                    JOptionPane.showMessageDialog(this,
                        "You have been logged out successfully.",
                        "Logout", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                }else{
                    JOptionPane.showMessageDialog(null, "Please Enter Your valid Username");
                }
            });

            cancelBtn.addActionListener(e -> dispose());

            setVisible(true);
        }
    }
}
