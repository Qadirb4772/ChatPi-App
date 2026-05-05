package JDBC;

import java.sql.*;

import javax.swing.JOptionPane;

public class Searching extends JavaDatabaseConnectivity{
    public Searching(){
        super();
    }

    private static String query = "SELECT username FROM Users";
   public static boolean isPresent(String username){
        boolean present = false;
        Connection conn = JavaDatabaseConnectivity.getConnection();
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                if(rs.getString("username").equalsIgnoreCase(username)){
                    present = true;
                    break;
                }
            }
            return present;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Database Error");
            return present;
        }finally{
            JavaDatabaseConnectivity.closeConnection(conn);
        }
   }
}
