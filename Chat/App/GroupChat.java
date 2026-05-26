package Chat.App;

import Chat.DB.DBConnection;

import PkgGUI.LogIn;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import JDBC.Searching;
public class GroupChat {

    JFrame frame;
    JPanel chatPanel;
    JScrollPane scrollPane;
    JTextField messageField;
    String personName;
    String groupName;

    ArrayList<GroupChat> groupMembers;

    Color myMessageColor    = new Color(0, 122, 255);
    Color otherMessageColor = new Color(230, 230, 230);

    // ================= CONSTRUCTOR =================
    public GroupChat(String name, String groupName, int x, int y) {
        this.personName  = name;
        this.groupName   = groupName;
        this.groupMembers = new ArrayList<>();

        frame = new JFrame(name + " (" + groupName + ")");
        frame.setSize(450, 600);
        frame.setLayout(null);
        frame.setLocation(x, y);
        frame.getContentPane().setBackground(new Color(240, 240, 240));

        // Chat panel
        chatPanel = new JPanel();
        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
        chatPanel.setBackground(new Color(240, 240, 240));

        scrollPane = new JScrollPane(chatPanel);
        scrollPane.setBounds(20, 20, 400, 460);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        frame.add(scrollPane);

        // Input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(null);
        inputPanel.setBounds(20, 490, 400, 60);
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        frame.add(inputPanel);

        messageField = new JTextField();
        messageField.setBounds(10, 15, 290, 30);
        messageField.setFont(new Font("Arial", Font.PLAIN, 14));
        inputPanel.add(messageField);

        JButton sendButton = new JButton("Send");
        sendButton.setBounds(310, 15, 75, 30);
        sendButton.setBackground(new Color(0, 122, 255));
        sendButton.setForeground(Color.WHITE);
        sendButton.setBorder(BorderFactory.createEmptyBorder());
        inputPanel.add(sendButton);

        sendButton.addActionListener(e -> sendMessage());
        messageField.addActionListener(e -> sendMessage());

        // Load previous group messages from DB on open
        loadGroupHistoryFromDB();

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    // ================= ADD GROUP MEMBER =================
    public void addMember(GroupChat person) {
        groupMembers.add(person);
    }

    // ================= LOAD HISTORY FROM DB =================
    private void loadGroupHistoryFromDB() {
        Connection conn = DBConnection.getConnection();
        if (conn == null) return;

        String sql = "SELECT sender, message, sent_at " +
                     "FROM group_chat_history " +
                     "WHERE group_name = ? ORDER BY sent_at ASC";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, groupName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String sender  = rs.getString("sender");
                String message = rs.getString("message");
                boolean isMine = sender.equals(personName);
                addMessageBubble("[" + rs.getString("sent_at") + "] " + message,
                                 sender, isMine);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ================= SAVE MESSAGE TO DB =================
    private void saveMessageToDB(String sender, String message) {
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

    // ================= MESSAGE BUBBLE =================
    public void addMessageBubble(String msg, String sender, boolean isMine) {
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
        container.setBackground(new Color(240, 240, 240));

        JPanel bubbleContent = new JPanel();
        bubbleContent.setLayout(new BoxLayout(bubbleContent, BoxLayout.Y_AXIS));

        if (!isMine) {
            JLabel nameLabel = new JLabel(sender);
            nameLabel.setFont(new Font("Arial", Font.BOLD, 11));
            nameLabel.setForeground(new Color(100, 100, 100));
            bubbleContent.add(nameLabel);
        }

        JTextArea text = new JTextArea(msg);
        text.setEditable(false);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        text.setFont(new Font("Arial", Font.PLAIN, 13));
        text.setOpaque(false);
        text.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        bubbleContent.add(text);

        JPanel bubble = new JPanel(new BorderLayout());
        bubble.add(bubbleContent);
        bubble.setBackground(isMine ? myMessageColor : otherMessageColor);
        bubble.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)));

        if (isMine) {
            container.add(Box.createHorizontalGlue());
            container.add(bubble);
        } else {
            container.add(bubble);
            container.add(Box.createHorizontalGlue());
        }

        chatPanel.add(container);
        chatPanel.add(Box.createVerticalStrut(5));

        SwingUtilities.invokeLater(() -> {
            JScrollBar bar = scrollPane.getVerticalScrollBar();
            bar.setValue(bar.getMaximum());
        });

        chatPanel.revalidate();
        chatPanel.repaint();
    }

    // ================= SEND MESSAGE =================
    public void sendMessage() {
        String msg = messageField.getText().trim();
        if (!msg.isEmpty()) {
            addMessageBubble(msg, personName, true);
            saveMessageToDB(personName, msg);  // Save to DB

            for (GroupChat member : groupMembers) {
                member.receiveMessage(personName, msg);
            }
            messageField.setText("");
        }
    }

    // ================= RECEIVE MESSAGE =================
    public void receiveMessage(String sender, String msg) {
        addMessageBubble(msg, sender, false);
    }

    // ================= NAME INPUT DIALOG =================
    public static String getName(int num) {
        return JOptionPane.showInputDialog("Enter username for Person " + num);
    }

    // ================= LAUNCH =================
    public static void launch() {
        String n1 = LogIn.getUsername();
        String n2 = getName(1);
        String n3 = getName(2);
        String n4 = getName(3);


        if(Searching.isPresent(n4) && Searching.isPresent(n3) && Searching.isPresent(n2)){
            String group = JOptionPane.showInputDialog("Enter Group Name: ");

            GroupChat p1 = new GroupChat(n1, group, 50,  50);
            GroupChat p2 = new GroupChat(n2, group, 550, 50);
            GroupChat p3 = new GroupChat(n3, group, 50,  700);
            GroupChat p4 = new GroupChat(n4, group, 550, 700);

            p1.addMember(p2); p1.addMember(p3); p1.addMember(p4);
            p2.addMember(p1); p2.addMember(p3); p2.addMember(p4);
            p3.addMember(p1); p3.addMember(p2); p3.addMember(p4);
            p4.addMember(p1); p4.addMember(p2); p4.addMember(p3);
        }else if(!(Searching.isPresent(n4))){
            JOptionPane.showMessageDialog(null, n4+" is not a valid username");
        }else if(!(Searching.isPresent(n3))){
            JOptionPane.showMessageDialog(null, n3 +" is not a valid username");
        }else{
            JOptionPane.showMessageDialog(null, n2 + " is not a valid username");
        }
    }
}
