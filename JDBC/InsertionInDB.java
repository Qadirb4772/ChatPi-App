package JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.sql.Statement;


public class InsertionInDB extends JavaDatabaseConnectivity{
    public InsertionInDB(){
        super();
      }

    private static Connection connection = JavaDatabaseConnectivity.getConnection();
    private static String userDataQuery = "INSERT INTO Users (first_name, last_name, username, email, phone_number, password) VALUES(?, ?, ?, ?,?,?)";
    private static String userMsgQuery = "INSERT INTO Chats (chat_name, created_at, is_Grouped) VALUES (?, NOW(), ?)";

    public static boolean insertData(String firstName, String lastName, String username, String email, String phoneNumber, int password){
        boolean isInserted = false;
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(userDataQuery);
           
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, username);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, phoneNumber);
            preparedStatement.setInt(6, password);
            
            int rowsAffected = preparedStatement.executeUpdate();
            
            if(rowsAffected > 0){
                isInserted = true;
            }
            
                
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Username or Email or Phone Number already exists", "Error!", JOptionPane.ERROR_MESSAGE, null);
        }finally{
            JavaDatabaseConnectivity.closeConnection(connection);
        }

        return isInserted;
    }


     public static void insertChat(String chatName, boolean isGrouped){
        Connection conn = JavaDatabaseConnectivity.getConnection();
        try {
            PreparedStatement pstmt = conn.prepareStatement(userMsgQuery);
            pstmt.setString(1, chatName);
            pstmt.setBoolean(2, isGrouped);
            pstmt.executeUpdate();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }finally{
            JavaDatabaseConnectivity.closeConnection(conn);
        }
     }

     public static int createOrGetChat(String groupName) {
        int chatId = -1;

        Connection conn = JavaDatabaseConnectivity.getConnection();
        if (conn == null) return -1;

       try {
            String checkSql = "SELECT chat_id FROM Chats WHERE chat_name = ?";
            PreparedStatement ps = conn.prepareStatement(checkSql);
            ps.setString(1, groupName);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                chatId = rs.getInt("chat_id");
            } else {
                String insertSql = "INSERT INTO Chats (chat_name, created_at, is_Grouped) VALUES (?, NOW(), 1)";
                PreparedStatement insertPs = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);

                insertPs.setString(1, groupName);
                insertPs.executeUpdate();

                ResultSet keys = insertPs.getGeneratedKeys();
                if (keys.next()) {
                    chatId = keys.getInt(1);
                }
            }

        } catch (SQLException e) {
                e.printStackTrace();
        }

    return chatId; //
    }    
}