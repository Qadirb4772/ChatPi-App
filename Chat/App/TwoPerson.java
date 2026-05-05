package Chat.App;


import Chat.DB.DBConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import JDBC.Searching;
import PkgGUI.LogIn;
import JDBC.InsertionInDB;

public class TwoPerson {

    public TwoPerson(){

    }
    JFrame frame;  // for window
    JPanel chatPanel; /// group all container
    JScrollPane scrollPane;  // scrollable y axis
    JTextField messageField;  // where message will show

    private String personName;
    TwoPerson otherPerson;   // Only one connected person

    Color myMessageColor = Color.BLUE;
    Color otherMessageColor = Color.GRAY;

    // ================= CONSTRUCTOR FIRST USER=================
    public TwoPerson(String name, int xPosition, int yPosition) {

        personName = name; // FIRST USERNAME

        frame = new JFrame(name);
        frame.setSize(450, 600);
        frame.setLayout(null);
        frame.setLocation(xPosition, yPosition);
        frame.getContentPane().setBackground(Color.lightGray);

        // ============= MESSAGE SHOW AREA ============
        chatPanel = new JPanel();
        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS)); //upar se neeche arrange honge
        chatPanel.setBackground(Color.lightGray);

        // ================ SCROLLABLE MEANS WHEN MESSAGES ZIADA HO ================
        scrollPane = new JScrollPane(chatPanel);
        scrollPane.setBounds(20, 20, 400, 460);
        // scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        frame.add(scrollPane);

        // ===========   Message likhne ke liye ==========================
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(null);
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        inputPanel.setBounds(20, 490, 400, 60);
        frame.add(inputPanel);

        //  =============== USER TYPE MESSAGE AREA ===================
        messageField = new JTextField();
        messageField.setBounds(10, 15, 290, 30);
        inputPanel.add(messageField);

        // ================ SEND MESSAGE BUTTON ===============
        JButton sendButton = new JButton("Send");
        sendButton.setBounds(310, 15, 75, 30);
        sendButton.setBackground(Color.BLACK);
        sendButton.setForeground(Color.WHITE);
        inputPanel.add(sendButton);

        sendButton.addActionListener(e -> sendMessage());  /// WHEN CLICK ON BUTTON
        messageField.addActionListener(e -> sendMessage()); /// WHEN PRESS ENTER

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    // ================= CONNECT OTHER PERSON =================
    
    public void addOtherPerson(TwoPerson other) {
        this.otherPerson = other;
        loadChatHistoryWith(other.personName);
    }

    // ================= LOAD HISTORY =================
    private void loadChatHistoryWith(String otherName) {

        Connection conn = DBConnection.getConnection();

        if (conn == null) return;

        String sql = "SELECT sender, receiver, message, sent_at " +
                     "FROM chat_history " +
                     "WHERE (sender=? AND receiver=?) " +
                     "OR (sender=? AND receiver=?) " +
                     "ORDER BY sent_at ASC";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, personName);
            ps.setString(2, otherName);
            ps.setString(3, otherName);
            ps.setString(4, personName);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                String sender = rs.getString("sender");
                String message = rs.getString("message");
                String time = rs.getString("sent_at");

                boolean isMine = sender.equals(personName);

                addMessageBubble("[" + time + "] " + message, sender, isMine);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= MESSAGE BUBBLE =================
    public void addMessageBubble(String message, String sender, boolean isMyMessage) {

        JPanel row = new JPanel();
        row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
        row.setBackground(new Color(240, 240, 240));

        //  =========== Text Field Area ==============
        JTextArea text = new JTextArea(message);
        text.setEditable(false);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        text.setOpaque(false);
        text.setFont(new Font("Arial", Font.PLAIN, 13));
        text.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));

        JPanel bubble = new JPanel(new BorderLayout());
        bubble.add(text);
        bubble.setBackground(isMyMessage ? myMessageColor : otherMessageColor);

        if (isMyMessage) {
            row.add(Box.createHorizontalGlue());
            row.add(bubble);
        } else {
            row.add(bubble);
            row.add(Box.createHorizontalGlue());
        }

        chatPanel.add(row);
        chatPanel.add(Box.createVerticalStrut(5));

        chatPanel.revalidate();
        chatPanel.repaint();

        SwingUtilities.invokeLater(() -> {
            JScrollBar bar = scrollPane.getVerticalScrollBar();
            bar.setValue(bar.getMaximum());
        });
    }

    // ================= SEND MESSAGE =================
    public void sendMessage() {

        String message = messageField.getText();

        if (!message.isEmpty()) {

            addMessageBubble(message, personName, true);
           
            if (otherPerson != null) {

                otherPerson.receiveMessage(personName, message);

                ChatHistory.saveMessage(
                        personName,
                        otherPerson.personName,
                        message
                );
            }

            messageField.setText("");
        }
    }

    // ================= RECEIVE MESSAGE =================
    public void receiveMessage(String fromPerson, String message) {
        addMessageBubble(message, fromPerson, false);
    }

    // ================= NAME INPUT =================
    public static String getNameFromUser() {

        String name = JOptionPane.showInputDialog(
                null,
                "Enter username of Person " 
        );

        if (name == null || name.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Invalid Username", "Error", JOptionPane.ERROR_MESSAGE);
            return "";
        }

        return name;
    }

    // ================= OPEN WINDOW =================
    public void openWindow() {

        personName = LogIn.getUsername();
        String name2 = getNameFromUser();

        if(Searching.isPresent(name2)){
            InsertionInDB.insertChat(name2, false);
            TwoPerson person1 = new TwoPerson(personName, 50, 100);
            TwoPerson person2 = new TwoPerson(name2, 550, 100);

            person1.addOtherPerson(person2);
            person2.addOtherPerson(person1);
        }else{
            JOptionPane.showMessageDialog(null, name2+" is invalid username");
        }
    }
}