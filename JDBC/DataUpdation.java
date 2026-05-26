package JDBC;
import java.sql.*;
import javax.swing.JOptionPane;
public class DataUpdation extends JavaDatabaseConnectivity {
    public DataUpdation(){
        super();
    }

    private static String query = "UPDATE Users SET password = ? WHERE username = ?";
    
    public static boolean updatePassword(String username, int password){
        boolean passwordUpdated = false;
        Connection connection = JavaDatabaseConnectivity.getConnection();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, password);
            preparedStatement.setString(2, username);

            int rowsAffected = preparedStatement.executeUpdate();
        
            if(rowsAffected > 0){
                passwordUpdated = true;
                return passwordUpdated;
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error!!", JOptionPane.ERROR_MESSAGE);
        }finally{
            JavaDatabaseConnectivity.closeConnection(connection);
        }
      return passwordUpdated;
    }
}
