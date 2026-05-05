package Chat.DB;

import java.sql.*;
import javax.swing.*;

public class DBConnection {

    private static final String URL      = "jdbc:mysql://127.0.0.1:3306/ChatPi_App";
    private static final String USER     = "qadir";
    private static final String PASSWORD = "Asaad@Ahmed@786";

    private static Connection connection = null;

    // ================= GET CONNECTION =================
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Database Connected Successfully!");
            }
            return connection;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Database Connection Failed!\n" + e.getMessage(),
                    "DB Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    // ================= CLOSE CONNECTION =================
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database Connection Closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ================= TEST CONNECTION =================
    public static boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}




// //package db;

// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.SQLException;

// public class DBConnection {

//     private static DBConnection instance = null;
//     private static Connection conn       = null;

//     private String url      = "jdbc:mysql://localhost:3306/ChatPi";
//     private String user     = "root";
//     private String password = "ZafranKhan@06";

//     private DBConnection() {
//         try {
//             conn = DriverManager.getConnection(url, user, password);
//             System.out.println(" Connected Successfully!");
//         } catch (SQLException e) {
//             System.out.println(" Connection Failed!");
//             e.printStackTrace();
//         }
//     }

//     // Step 3 — single access point
//     public static Connection getConnection() {
//         if (instance == null) {
//             instance = new DBConnection();   
//         }
//         return conn;
//     }
// }