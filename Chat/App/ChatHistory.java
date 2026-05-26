package Chat.App;

// import Chat.DB.*;

import JDBC.JavaDatabaseConnectivity;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ChatHistory {

    // ================= SAVE MESSAGE TO DB =================
    public static void saveMessage(String sender, String receiver, String message) {    

        Connection conn = JavaDatabaseConnectivity.getConnection();
        if (conn == null) return;


        String sql = "insert into chat_history" +
                      "(sender, receiver, message) values (?, ?, ?);";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sender);
            ps.setString(2, receiver);
            ps.setString(3, message);
            
            ps.executeUpdate();
        } 
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Failed to save message!\n" + e.getMessage(),
                    "DB Error", JOptionPane.ERROR_MESSAGE);
        }finally{
            JavaDatabaseConnectivity.closeConnection(conn);
        }
    }

    // ================= GET CHAT HISTORY =================
    public static String[][] getChatHistory(String person1, String person2) {
        String[][] messages = new String[1000][4]; // Max 1000 messages
        int count = 0;
        Connection conn = JavaDatabaseConnectivity.getConnection();
        if (conn == null) return new String[0][0];

        String sql = "SELECT sender, receiver, message, sent_at " +
                     "FROM chat_history " +
                     "WHERE (sender = ? AND receiver = ?) " +
                     "   OR (sender = ? AND receiver = ?) " +
                     "ORDER BY sent_at ASC";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, person1);
            ps.setString(2, person2);
            ps.setString(3, person2);
            ps.setString(4, person1);

            ResultSet rs = ps.executeQuery();
            while (rs.next() && count < 1000) {
                messages[count] = new String[]{
                    rs.getString("sender"),
                    rs.getString("receiver"),
                    rs.getString("message"),
                    rs.getString("sent_at")
                };
                count++;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Failed to load chat history!\n" + e.getMessage(),
                    "DB Error", JOptionPane.ERROR_MESSAGE);
        }
        
        // Trim to actual size
        String[][] result = new String[count][4];
        for (int i = 0; i < count; i++) {
            result[i] = messages[i];
        }
        return result;
    }

    // ================= GET ALL PARTICIPANTS =================
    public static String[] getAllParticipants() {
        String[] participants = new String[1000]; // Max 1000 participants
        int count = 0;
        Connection conn = JavaDatabaseConnectivity.getConnection();
        if (conn == null) return new String[0];

        String sql = "SELECT DISTINCT sender FROM chat_history " +
                     "UNION " +
                     "SELECT DISTINCT receiver FROM chat_history";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next() && count < 1000) {
                participants[count] = rs.getString(1);
                count++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        // Trim to actual size
        String[] result = new String[count];
        for (int i = 0; i < count; i++) {
            result[i] = participants[i];
        }
        return result;
    }

    // ================= GET ALL CHAT PAIRS =================
    public static String[][] getAllChatPairs() {
        String[][] pairs = new String[1000][2]; // Max 1000 pairs
        int count = 0;
        Connection conn = JavaDatabaseConnectivity.getConnection();
        if (conn == null) return new String[0][0];

        String sql = "SELECT DISTINCT " +
                     "LEAST(sender, receiver) AS p1, " +
                     "GREATEST(sender, receiver) AS p2 " +
                     "FROM chat_history";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next() && count < 1000) {
                pairs[count] = new String[]{rs.getString("p1"), rs.getString("p2")};
                count++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        // Trim to actual size
        String[][] result = new String[count][2];
        for (int i = 0; i < count; i++) {
            result[i] = pairs[i];
        }
        return result;
    }

    // ================= LAUNCH GUI =================
    public static void launchGUI() {
        SwingUtilities.invokeLater(ChatHistoryGUI::new);
    }

    // ================= GUI CLASS =================
    public static class ChatHistoryGUI extends JFrame {

        private JComboBox<String> person1Combo;
        private JComboBox<String> person2Combo;
        private JTextArea chatArea;
        private DefaultTableModel tableModel;
        private JTable searchTable;
        private JTextField searchField;

        public ChatHistoryGUI() {
            setTitle("Chat History Viewer");
            setSize(800, 600);
            setLayout(new BorderLayout());
            setLocationRelativeTo(null);
            getContentPane().setBackground(new Color(240, 240, 240));

            JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
            mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            mainPanel.setBackground(new Color(240, 240, 240));

            mainPanel.add(createSearchPanel(),  BorderLayout.NORTH);

            JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
            splitPane.setResizeWeight(0.4);
            splitPane.setLeftComponent(createLeftPanel());
            splitPane.setRightComponent(createRightPanel());
            mainPanel.add(splitPane, BorderLayout.CENTER);

            mainPanel.add(createBottomPanel(), BorderLayout.SOUTH);
            add(mainPanel);

            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setVisible(true);

            showAllChats(); // Load all on open
        }

        // ================= SEARCH PANEL =================
        private JPanel createSearchPanel() {
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            panel.setBackground(new Color(240, 240, 240));
            panel.setBorder(BorderFactory.createTitledBorder("Search Chats"));

            searchField = new JTextField(20);
            JButton searchButton = new JButton("Search");
            searchButton.setBackground(new Color(0, 122, 255));
            searchButton.setForeground(Color.WHITE);

            panel.add(new JLabel("Search by name:"));
            panel.add(searchField);
            panel.add(searchButton);

            searchButton.addActionListener(e -> searchChats());
            searchField.addActionListener(e -> searchChats());
            return panel;
        }

        // ================= LEFT PANEL =================
        private JPanel createLeftPanel() {
            JPanel panel = new JPanel(new BorderLayout());
            panel.setBorder(BorderFactory.createTitledBorder("Search Results"));

            tableModel = new DefaultTableModel(new String[]{"Person 1", "Person 2"}, 0);
            searchTable = new JTable(tableModel);

            JScrollPane scroll = new JScrollPane(searchTable);
            panel.add(scroll, BorderLayout.CENTER);

            return panel;
        }

        // ================= RIGHT PANEL =================
        private JPanel createRightPanel() {
            JPanel panel = new JPanel(new BorderLayout());
            panel.setBorder(BorderFactory.createTitledBorder("Chat History"));
            chatArea = new JTextArea();
            chatArea.setEditable(false);
            panel.add(new JScrollPane(chatArea), BorderLayout.CENTER);
            return panel;
        }

        // ================= BOTTOM PANEL =================
        private JPanel createBottomPanel() {
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
            panel.setBorder(BorderFactory.createTitledBorder("Select Two Persons"));

            String[] participants = getAllParticipants();
            DefaultComboBoxModel<String> m1 = new DefaultComboBoxModel<>();
            DefaultComboBoxModel<String> m2 = new DefaultComboBoxModel<>();
            m1.addElement("Select Person");
            m2.addElement("Select Person");
            for (String p : participants) { 
                m1.addElement(p); 
                m2.addElement(p); 
            }

            person1Combo = new JComboBox<>(m1);
            person2Combo = new JComboBox<>(m2);
            JButton viewBtn = new JButton("View Chat History");
            viewBtn.setBackground(new Color(0, 122, 255));
            viewBtn.setForeground(Color.WHITE);

            panel.add(new JLabel("Person 1:")); panel.add(person1Combo);
            panel.add(new JLabel("Person 2:")); panel.add(person2Combo);
            panel.add(viewBtn);

            viewBtn.addActionListener(e -> viewSelectedChat());
            return panel;
        }

        // ================= SEARCH =================
        private void searchChats() {
            String search = searchField.getText().trim().toLowerCase();
            tableModel.setRowCount(0);
            if (search.isEmpty()) { showAllChats(); return; }

            String[][] allPairs = getAllChatPairs();
            for (String[] pair : allPairs) {
                if (pair[0].toLowerCase().contains(search) ||
                    pair[1].toLowerCase().contains(search)) {
                    tableModel.addRow(new Object[]{pair[0], pair[1]});
                }
            }
        }

        private void showAllChats() {
            tableModel.setRowCount(0);
            String[][] allPairs = getAllChatPairs();
            for (String[] pair : allPairs) {
                tableModel.addRow(new Object[]{pair[0], pair[1]});
            }
        }

        private void viewSelectedChat() {
            String p1 = (String) person1Combo.getSelectedItem();
            String p2 = (String) person2Combo.getSelectedItem();
            if (p1 == null || p2 == null ||
                p1.equals("Select Person") || p2.equals("Select Person")) {
                JOptionPane.showMessageDialog(this, "Please select both persons!");
                return;
            }
            if (p1.equals(p2)) {
                JOptionPane.showMessageDialog(this, "Please select two different persons!");
                return;
            }
            loadChatBetween(p1, p2);
        }

        private void loadChatBetween(String p1, String p2) {
            String[][] messages = getChatHistory(p1, p2);
            if (messages.length == 0) { 
                chatArea.setText("No chat history found."); 
                return; 
            }

            StringBuilder sb = new StringBuilder();
            sb.append("=========== CHAT HISTORY ===========\n");
            sb.append("Between: ").append(p1).append(" and ").append(p2).append("\n");
            sb.append("Total Messages: ").append(messages.length).append("\n");
            sb.append("=====================================\n\n");
            for (String[] msg : messages) {
                sb.append("[").append(msg[3]).append("] ")
                  .append(msg[0]).append(": ").append(msg[2]).append("\n");
            }
            chatArea.setText(sb.toString());
            chatArea.setCaretPosition(0);
        }
    }
}