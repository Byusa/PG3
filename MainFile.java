import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;

class MainFile {

    private static final String url = "jdbc:postgresql://comp421.cs.mcgill.ca:5432/cs421";
    private static final String usernamestring = "cs421g48";
    private static final String passwordstring = "@group48";
    private static Connection con;
    private static Scanner input;

    public static void main(String[] args) throws SQLException, ParseException {

        boolean notDone = true;

        input = new Scanner(System.in);

        while (notDone) {
            System.out.println("---------- YouNite ----------");
            System.out.println("Hell0, here are five options that you can choose from to try YouNite App");
            System.out.println("1 - to select all proffessional that got paid from a given clients");
            System.out.println("2 - to see top 5 higest paying categotries of job by the average earnings perhour worked for each job in that category. ");
            System.out.println("3 - to create a job");
            System.out.println("4 - to update all 3+ reviews(good enough) for new clients who have less than 10 reviews to 5 stars");
            System.out.println("5 - Deprecating old job status into closed ");
            System.out.println("0 - Quit the application");
            System.out.println("Just type the number of the option you want:");
            int i = input.nextInt();
            switch (i) {
                case 0:
                    System.out.println("We are sad to see you go :(");
                    notDone = false;
                    System.exit(0);
                    break;
                case 1:
                    QuerringPays();
                    break;
                case 2:
                	TopFiveHighestPaidCategories();
                    break;
                case 3:
                	createAJob();
                    break;
                case 4:
                	UpdateReview();
                    break;
                case 5:
                    UpdateJobStatus();
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
        
        System.out.println("Input your Id");
        int cid = input.nextInt();
        // Querying a table
        try {
            String querySQL = "SELECT * from Pays where cli_id = "+cid;
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

    //Update all 3+ reviews(good enough) for new clients who have less than 10 reviews to 5 stars 
    //to motivate them to use the app more
    private static void UpdateReview() throws SQLException {
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
        // Updating a table
        try {
            String updateSQL ="UPDATE review\n" + 
            		"SET ratings = 5\n" + 
            		"WHERE cli_id NOT IN (\n" + 
            		"    SELECT cli_id FROM (\n" + 
            		"        SELECT * FROM review r1 WHERE (\n" + 
            		"        SELECT COUNT(*)\n" + 
            		"        FROM review r2\n" + 
            		"        WHERE r1.cli_id = r2.cli_id\n" + 
            		"        AND r1.r_date <= r2.r_date ) >= 10 \n" + 
            		"    ) foo\n" + 
            		") AND review.ratings > 3;";
            //System.out.println(updateSQL);
            statement.executeUpdate(updateSQL);
            System.out.println("DONE");

        } catch (SQLException e) {
            sqlCode = e.getErrorCode(); // Get SQLCODE
            sqlState = e.getSQLState(); // Get SQLSTATE

            // Your code to handle errors comes here;
            // something more meaningful than a print would be good
            System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
        }
    }
    
    
    private static void createAJob() throws SQLException, ParseException{
    	
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
           
			//creating the jobID

			HashSet<Integer> JobIdList = new HashSet<Integer>();
           try {
	            String querySQL = "SELECT job_id from job";
	            // System.out.println(querySQL);
	            java.sql.ResultSet rs = statement.executeQuery(querySQL);
	            while (rs.next()) {
	                int thejob_id = rs.getInt(1);
	                JobIdList.add(thejob_id);
	            }

	        } catch (SQLException e) {
	            sqlCode = e.getErrorCode(); // Get SQLCODE
	            sqlState = e.getSQLState(); // Get SQLSTATE
	            System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
	        }
           
           Random r = new Random();
    	   int Jobid = (int)(r.nextInt((999 - 200) + 1) + 200);
	
    	   // Querying a table
    	   if(JobIdList.contains(Jobid)) {
    		   Jobid = (int)(r.nextInt((999 - 200) + 1) + 200);
    	   }    	   
           
           //creating the jobCategory
    	  String theCategory="Not Categorized";
    	   HashMap<Integer,String> jobCategoryMap = new HashMap<Integer, String>();
           try {
	            String querySQL = "SELECT category from job";
	            // System.out.println(querySQL);
	            java.sql.ResultSet rs = statement.executeQuery(querySQL);
	            int i =1;
	            while (rs.next()) {
	                theCategory = rs.getString(1);
	                jobCategoryMap.put(i,theCategory);
	                i++;
	            }
	            System.out.println();

	        } catch (SQLException e) {
	            sqlCode = e.getErrorCode(); // Get SQLCODE
	            sqlState = e.getSQLState(); // Get SQLSTATE
	            System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
	        }
	        
           
           System.out.println("Choose a Job Category by typing a number in front of the category eg:1 for (Financial Analyst)");
           System.out.println("Categories :  " + jobCategoryMap);
           int numberCategory = input.nextInt();
           
           String ChosenCategory = (String)(jobCategoryMap.get(numberCategory));
           
           String JobStatus = "Not Done";
           
           System.out.println("Set the Price of the job by inputing the price in $: ");
           int Chosenprice = input.nextInt();
                      
           System.out.println("Input the location of the job: ");
           input.nextLine(); 
           String ChosenLocation = input.nextLine();
           
           
           // Inserting Data into the table
           try {
	      
        	   String insertSQL = "INSERT INTO job VALUES ( "+Jobid+", \'14:56:00\', \'15:56:00\', \'2020-01-09\', "+Chosenprice+", '"+ChosenLocation+"', '"+ChosenCategory+"', '"+ JobStatus+"' ) ";
        	   System.out.println(insertSQL);
        	   statement.executeUpdate(insertSQL);
        	   System.out.println("DONE");


		} catch (SQLException e) {
			sqlCode = e.getErrorCode(); // Get SQLCODE
			sqlState = e.getSQLState(); // Get SQLSTATE

			// Your code to handle errors comes here;
			// something more meaningful than a print would be good
			System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
		}
    }
    
    private static void TopFiveHighestPaidCategories()  throws SQLException, ParseException {
    	
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
            String querySQL = "SELECT category, avgwage\n" + 
            		"    FROM (SELECT category, AVG(wage) AS avgwage\n" + 
            		"        FROM (SELECT category, \n" + 
            		"            (floatprice/((CAST((EXTRACT (HOUR FROM (endtime - starttime))) AS FLOAT)) +\n" + 
            		"            (CAST((EXTRACT (MINUTE FROM (endtime - starttime))) AS FLOAT)/ 60.0)))\n" + 
            		"    AS wage\n" + 
            		"    FROM (SELECT category, endtime, starttime, CAST (price AS FLOAT) AS floatprice\n" + 
            		"    FROM job) AS jobfloat) AS wagejob GROUP BY category\n" + 
            		") AS avgwagejob ORDER BY avgwage DESC LIMIT 5;";
            // System.out.println(querySQL);
            java.sql.ResultSet rs = statement.executeQuery(querySQL);
            while (rs.next()) {
                String Thecategory = rs.getString(1);
                Double Average = rs.getDouble(2);
                System.out.print("The Category: " + Thecategory+"\t");
                System.out.println("  Average Pay :  " + Average);
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
    
    private static void UpdateJobStatus() throws SQLException {
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
        // Updating a table
        try {
            String updateSQL ="UPDATE\n" + 
            		"job\n" + 
            		"SET status = 'Closed' WHERE\n" + 
            		"j_date > '2019-11-21' AND\n" + 
            		"j_date < '2020-1-21';";
            //System.out.println(updateSQL);
            statement.executeUpdate(updateSQL);
            System.out.println("DONE");

        } catch (SQLException e) {
            sqlCode = e.getErrorCode(); // Get SQLCODE
            sqlState = e.getSQLState(); // Get SQLSTATE

            // Your code to handle errors comes here;
            // something more meaningful than a print would be good
            System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
        }
    }

    
}
