
import java.sql.*;  
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MainFile {
    public static void main(String []args) {
        Connection con = null;
       try {
            Class.forName("com.mysql.jdbc.Driver");

            con = DriverManager.getConnection(
                "jdbc:mysql://comp421.cs.mcgill.ca:50000/cs421, user_name" +"user=cs421g48&password=@group48");
            //here sonoo is database name, root is username and password  
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from emp");
            while (rs.next())
                System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3));
            con.close();
        } catch (Exception ex) {
              // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ((SQLException) ex).getSQLState());
            System.out.println("VendorError: " + ((SQLException) ex).getErrorCode());
        }
    }
}  
