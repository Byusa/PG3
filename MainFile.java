import java.util.Scanner;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


class MainFile{
	
	private static final String url = "jdbc:postgresql://comp421.cs.mcgill.ca:5432/cs421";
	private static final String usernamestring = "cs421g48";
	private static final String passwordstring = "@group48";
	private static Connection con;
    private static Scanner input;
    
	public static void main(String[] args) throws SQLException {
	
		boolean notDone = true;

		input = new Scanner(System.in);

		while (notDone) {
			System.out.println("---------- YouNite ----------");
			System.out.println("Hell0, here are five options that you can choose from to try YouNite App");
			System.out.println("1 - to select all proffessional that got paid from clients");
			System.out.println("2 - XXX");
			System.out.println("3 - XXX");
			System.out.println("4 - XXX");
			System.out.println("5 - XXX");
			System.out.println("0 - Quit the application");
			System.out.println("Just type the number of the option you want:");
			int i = input.nextInt();
			switch (i) {
				case 0:
					System.out.println("We are sad to see you go :(");
					notDone = false;
					break;
				case 1:
					QuerringPays();
					break;
				case 2:
					System.out.println("Not yet done!!!!");
					break;
				case 3:
					System.out.println("Not yet done!!!!!");
					break;
				case 4:
					System.out.println("Not yet done!!!!!");
					break;
				case 5:
					System.out.println("Not yet done!!!!!");
					break;
				default:
					System.out.println("Unknown Selection");
					break;
			}
		}

	}

	private static void QuerringPays() throws SQLException {
		int sqlCode = 0; // Variable to hold SQLCODE
		String sqlState = "00000"; // Variable to hold SQLSTATE

		// Register the driver. You must register the driver before you can use it.
		try {
			DriverManager.registerDriver(new org.postgresql.Driver());
		} catch (Exception cnfe) {
			System.out.println("Class not found");
		}

		
		con = DriverManager.getConnection(url, usernamestring, passwordstring);
		Statement statement = con.createStatement();

		// Querying a table
		try {
			String querySQL = "SELECT * from Pays";
			// System.out.println(querySQL);
			java.sql.ResultSet rs = statement.executeQuery(querySQL);
			while (rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getString(2);
				System.out.print("Professional Id :  " + id);
				System.out.println("  Client Id:  " + name);
			}
			System.out.println("DONE");
		} catch (SQLException e) {
			sqlCode = e.getErrorCode(); // Get SQLCODE
			sqlState = e.getSQLState(); // Get SQLSTATE
			System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
		}


		// Finally but importantly close the statement and connection
		statement.close();
		con.close();
	} 
	
	
	
	

}
