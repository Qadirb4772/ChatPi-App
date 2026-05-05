package JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public abstract class JavaDatabaseConnectivity {
    private static final String url = "jdbc:mysql://127.0.0.1:3306/ChatPi_App";
    private static final String username = "qadir";
    private static final String password = "Asaad@Ahmed@786";

    JavaDatabaseConnectivity(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(ClassNotFoundException e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error!!", JOptionPane.ERROR_MESSAGE, null);
        }
    }

    public static Connection getConnection(){
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            return connection;
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public static void closeConnection(Connection connection){
        if(connection != null){
            try{
                connection.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }
    
}
