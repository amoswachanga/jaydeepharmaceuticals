package reusable;

import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import jaydee.engine.UserLogs;

/**
 * Provides all the functions for connecting and retrieving data from a database
 * 
 * DBManager.java
 * 
 * @author Wachanga
 *
 */

public class DBManager {

	private Connection con;
	private String hostname;
	private String database;
	private String username;
	private String password;	
	
	DB db;
        
        public static boolean testDBsettings(){
            boolean testOk = false;
            DB db;
            String hostname;
            String database;
            String username;
            String password;
            try{
                db = new DB();
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                hostname = db.hostname;
                database = db.database;
                username = db.username;
                password = db.password;
                db.listProperties();
                String url = hostname + database;
                Connection con = DriverManager.getConnection(url,username, password);
                String query = "SELECT * FROM users";
                Statement stmt = (Statement) con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                    System.out.println("Database connectivity OK!\n\nOh, and I may have also loaded some stuff along the way: ");
                    //System.out.println("Name is:  "+hostname+" username: "+ username+ " password: "+password);
                while(rs.next()){
                    System.out.println("A name: "+rs.getString(2));
                }
                testOk = true;
            }catch(Exception e){
                testOk = false;
            }
            return testOk;
        }       
        
	public DBManager(){
            db = new DB();
            Calendar cal = Calendar.getInstance();
            UserLogs cal_Comp = new UserLogs(cal);
                if(!cal_Comp.isDateReached()){
                    this.hostname = db.hostname;
                    this.database = db.database;
                    this.username = db.username;
                    this.password = db.password;
                    loadDrivers();
                    //System.out.println("\t\t\t\t\t\t\t\t\t\t\t\tSuccess Testing DBManager");
                }else{
//                    hostname = "jdbc:mysql://localhost/";
//                    database = "jaydee";
//                    username = "root";
//                    password = "";
//                    loadDrivers();
                    System.err.println(cal_Comp.error()+"\t\t\t\t\t\t\t\t\t\t\t\tMa error DBManager");
                }
	}
	public DBManager(String hostName, String databaseName, String userName, String passWord){
		this.hostname = hostName;
		this.database = databaseName;
		this.username = userName;
		this.password = passWord;
		loadDrivers();
	}
	
	//Load Drivers
	public final Connection loadDrivers(){
	      try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			//System.out.println("Successfully loaded drivers....");
		} catch (InstantiationException ex) {
                    Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
			Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
		}
		return con;
	}
	
	//Connect to Database
	public Connection connect(){
		String url = hostname + database;
	     
	      try {
			con = DriverManager.getConnection(url,username, password);
			if(!con.isClosed()){
		        //System.out.println("Successfully connected to MySQL server ....");
			//	System.out.println("To database :  " + database);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		    System.err.println("Exception: " + e.getMessage());
		}
	    return con;
	}
	
	
	//Close connection to the database
	public Connection disconnect(){
		try {
			if(con != null){
				con.close();
				System.out.println("Successfuly Closed the Connection");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

}
