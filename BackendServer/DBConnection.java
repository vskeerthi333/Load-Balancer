import java.sql.*;
import java.util.*;

public class DBConnection {
	private static final String DBCLASSNAME = "com.mysql.jdbc.Driver";
	private static final String CONNECTION = "jdbc:mysql://127.0.0.1/sampledb?useSSL=false";
	private String username = null;
	private String password = null;

	public DBConnection(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public Connection getDBConnection() {
		Properties properties = new Properties();
     	properties.put("user", username);
    	properties.put("password", password);
    	Connection connection = null;

    	try {
    		Class.forName(DBCLASSNAME);
			connection = DriverManager.getConnection(CONNECTION,properties);
		}
		catch(Exception e){
			System.out.println(e);
		}
		return connection;
	}
}