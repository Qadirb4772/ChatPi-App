package PkgGUI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBC {
        private static final String url = "jdbc:mysql://127.0.0.1:3306/Schema1";
        private static final String username = "qadir";
        private static final String password = "Asaad@Ahmed@786";
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found: " + e.getMessage());
        }
        try{
        Connection conn = DriverManager.getConnection(url, username, password);
        Statement statement = conn.createStatement();
        String query = "Select * from Students";
        ResultSet students = statement.executeQuery(query);

        while(students.next()){
            int id = students.getInt("student_id");
            String name = students.getString("name");
            int age = students.getInt("age");
            String dept = students.getString("department");
            int marks = students.getInt("marks");

            System.out.println("Student ID: "+id);
            System.out.println("Name: "+name);
            System.out.println("Age: "+age);
            System.out.println("Department: "+dept);
            System.out.println("Marks: "+marks);
            System.out.println();
            System.out.println();
        }
        
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

}
