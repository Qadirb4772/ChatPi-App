package JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class DataRetrieval extends JavaDatabaseConnectivity{
   
    public DataRetrieval(){
        super();
    }
    
    private static String query = "SELECT * FROM Users WHERE username = ?";
    
    
    public static boolean getData(String username, int password){
        boolean isAvailable = false;
        Connection connection = JavaDatabaseConnectivity.getConnection();
        try {
        PreparedStatement prpdStmt = connection.prepareStatement(query);
        prpdStmt.setString(1, username);
        ResultSet record = prpdStmt.executeQuery();
        if(record.next()){
            int passwordInDatabase = record.getInt("password");
            if(passwordInDatabase == password){
                isAvailable = true;
                return isAvailable;
            }
        }
        return isAvailable;

        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE, null);
        }
        JavaDatabaseConnectivity.closeConnection(connection);
        return isAvailable;

    }
    public static String getFirstName(String username){
        String firstName = "";
        String query = "SELECT * FROM Users WHERE username = ?";
        Connection connection = JavaDatabaseConnectivity.getConnection();
        
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                firstName = rs.getString("first_name");
                return firstName;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return firstName;
        }finally{
            JavaDatabaseConnectivity.closeConnection(connection);
        }
        return firstName;
    }

    public static int getUserId(String username){
        int id = 0;
        Connection connection = JavaDatabaseConnectivity.getConnection();
        String query = "SELECT * FROM Users";
        try{
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                if(rs.getString("username").equalsIgnoreCase(username)){
                    id = rs.getInt("user_id");
                    break;
                }
            }
            return id;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Database Error");
            return id;
        }finally{
            JavaDatabaseConnectivity.closeConnection(connection);
        }
    }

    public static int getChatId(int user1Id, int user2Id){
        int id = 0;
        String query = "SELECT * FROM ChatParticipants WHERE user_id = ? AND chat_id = ? "+
                        " OR chat_id = ? AND user_id = ?";
        Connection connection = JavaDatabaseConnectivity.getConnection();
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, user1Id);
            pstmt.setInt(2, user2Id);
            pstmt.setInt(3, user2Id);
            pstmt.setInt(4, user1Id);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                return rs.getInt("chat_id");
            }
            return id;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Database Error");
            return id;
        }finally{
            JavaDatabaseConnectivity.closeConnection(connection);
        }
    }

}
