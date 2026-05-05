package Chat.App;

import Chat.DB.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class GroupChatHistory {

    // ================= SAVE MESSAGE =================
    public static void saveMessage(String groupName, String sender, String message) {
        Connection conn = DBConnection.getConnection();
        if (conn == null) return;

        String sql = "INSERT INTO group_chat_history " +
                     "(group_name, sender, message, sent_at) VALUES (?, ?, ?, NOW())";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, groupName);
            ps.setString(2, sender);
            ps.setString(3, message);
            ps.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Failed to save group message!\n" + e.getMessage(),
                    "DB Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ================= GET GROUP HISTORY =================
    public static ResultSet getGroupHistory(String groupName) {
        Connection conn = DBConnection.getConnection();
        if (conn == null) return null;

        String sql = "SELECT sender, message, sent_at " +
                     "FROM group_chat_history " +
                     "WHERE group_name = ? ORDER BY sent_at ASC";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, groupName);
            return ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // ================= GET ALL GROUPS =================
    public static ResultSet getAllGroups() {
        Connection conn = DBConnection.getConnection();
        if (conn == null) return null;

        String sql = "SELECT DISTINCT group_name FROM group_chat_history ORDER BY group_name";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            return ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // ================= LAUNCH GUI =================
    public static void launchGUI() {
        SwingUtilities.invokeLater(GroupChatHistoryGUI::new);
    }

    // ================= GUI CLASS =================
    public static class GroupChatHistoryGUI extends JFrame {

        JTextField searchField;
        JTable table;
        DefaultTableModel model;
        JTextArea area;
        JComboBox<String> groupCombo;

        public GroupChatHistoryGUI() {
            setTitle("Group Chat History");
            setSize(800, 600);
            setLayout(new BorderLayout());
            setLocationRelativeTo(null);
            getContentPane().setBackground(new Color(240, 240, 240));

            JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
            mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            mainPanel.setBackground(new Color(240, 240, 240));

            mainPanel.add(createSearchPanel(), BorderLayout.NORTH);

            JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
            split.setResizeWeight(0.4);
            split.setLeftComponent(createLeftPanel());
            split.setRightComponent(createRightPanel());
            mainPanel.add(split, BorderLayout.CENTER);

            mainPanel.add(createBottomPanel(), BorderLayout.SOUTH);
            add(mainPanel);

            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setVisible(true);
            showAllGroups();
            loadGroupsToCombo();
        }

        // ================= SEARCH PANEL =================
        JPanel createSearchPanel() {
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            panel.setBackground(new Color(240, 240, 240));
            panel.setBorder(BorderFactory.createTitledBorder("Search Group"));

            searchField = new JTextField(20);
            JButton searchButton = new JButton("Search");
            searchButton.setBackground(new Color(0, 122, 255));
            searchButton.setForeground(Color.WHITE);

            panel.add(new JLabel("Search by name:"));
            panel.add(searchField);
            panel.add(searchButton);

            searchButton.addActionListener(e -> searchGroup());
            searchField.addActionListener(e -> searchGroup());
            return panel;
        }

        // ================= LEFT PANEL =================
        JPanel createLeftPanel() {
            JPanel panel = new JPanel(new BorderLayout());
            panel.setBorder(BorderFactory.createTitledBorder("Search Results"));

            model = new DefaultTableModel(new String[]{"Group Name"}, 0);
            table = new JTable(model);
            panel.add(new JScrollPane(table), BorderLayout.CENTER);

            table.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        int row = table.getSelectedRow();
                        if (row != -1) {
                            String group = (String) model.getValueAt(row, 0);
                            loadHistory(group);
                            groupCombo.setSelectedItem(group);
                        }
                    }
                }
            });
            return panel;
        }

        // ================= RIGHT PANEL =================
        JPanel createRightPanel() {
            JPanel panel = new JPanel(new BorderLayout());
            panel.setBorder(BorderFactory.createTitledBorder("Chat History"));
            area = new JTextArea();
            area.setEditable(false);
            area.setFont(new Font("Arial", Font.PLAIN, 13));
            panel.add(new JScrollPane(area), BorderLayout.CENTER);
            return panel;
        }

        // ================= BOTTOM PANEL =================
        JPanel createBottomPanel() {
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
            panel.setBackground(new Color(240, 240, 240));
            panel.setBorder(BorderFactory.createTitledBorder("Select Group"));

            groupCombo = new JComboBox<>();
            groupCombo.addItem("Select Group");

            JButton viewButton = new JButton("View Chat History");
            viewButton.setBackground(new Color(0, 122, 255));
            viewButton.setForeground(Color.WHITE);

            panel.add(new JLabel("Group:"));
            panel.add(groupCombo);
            panel.add(viewButton);

            viewButton.addActionListener(e -> {
                String group = (String) groupCombo.getSelectedItem();
                if (group == null || group.equals("Select Group")) {
                    JOptionPane.showMessageDialog(this, "Please select a group!");
                    return;
                }
                loadHistory(group);
            });
            return panel;
        }

        void loadGroupsToCombo() {
            groupCombo.removeAllItems();
            groupCombo.addItem("Select Group");
            
            try (ResultSet rs = getAllGroups()) {
                if (rs != null) {
                    while (rs.next()) {
                        groupCombo.addItem(rs.getString("group_name"));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        void searchGroup() {
            String text = searchField.getText().trim().toLowerCase();
            model.setRowCount(0);
            
            if (text.isEmpty()) { 
                showAllGroups(); 
                return; 
            }
            
            try (ResultSet rs = getAllGroups()) {
                if (rs != null) {
                    while (rs.next()) {
                        String groupName = rs.getString("group_name");
                        if (groupName.toLowerCase().contains(text)) {
                            model.addRow(new Object[]{groupName});
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        void showAllGroups() {
            model.setRowCount(0);
            
            try (ResultSet rs = getAllGroups()) {
                if (rs != null) {
                    while (rs.next()) {
                        model.addRow(new Object[]{rs.getString("group_name")});
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        void loadHistory(String groupName) {
            StringBuilder sb = new StringBuilder();
            sb.append("=========== GROUP CHAT HISTORY ===========\n\n");
            sb.append("Group Name: ").append(groupName).append("\n");
            sb.append("=========================================\n\n");
            
            int messageCount = 0;
            
            try (ResultSet rs = getGroupHistory(groupName)) {
                if (rs != null) {
                    while (rs.next()) {
                        sb.append("[").append(rs.getString("sent_at")).append("] ")
                          .append(rs.getString("sender")).append(": ")
                          .append(rs.getString("message")).append("\n");
                        messageCount++;
                    }
                }
                
                if (messageCount == 0) {
                    area.setText("No chat history found for group: " + groupName);
                } else {
                    // Insert count at the top
                    sb.insert(sb.indexOf("=========================================\n\n") + 41, 
                             "Total Messages: " + messageCount + "\n");
                    area.setText(sb.toString());
                    area.setCaretPosition(0);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                area.setText("Error loading chat history!");
            }
        }
    }
}