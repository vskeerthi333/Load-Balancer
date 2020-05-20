import java.sql.*;
import java.io.*;
import java.util.*;

public class DBOperations {

	Connection connection = null;
	Statement statement = null;

	public DBOperations() {
		connection = (new DBConnection("root", "root")).getDBConnection(); 
		try {
			statement = connection.createStatement();
		}
		catch(Exception e){
			System.out.println(e);
		}
	}

	public String execute(String query) throws SQLException {
	
		if (query.startsWith("SELECT")) {
			return executeSelectOperation(query);
		}
		else if (query.startsWith("UPDATE") || query.startsWith("INSERT") || query.startsWith("DELETE")) {
			return executeOperation(query);
		}
		else {
			return "Invalid query !! Check Syntax..";
		}
	}

	public String executeSelectOperation(String query) throws SQLException{

		ResultSet resultset = statement.executeQuery(query);
		ResultSetMetaData metadata = resultset.getMetaData();
		int coloumnsCount = metadata.getColumnCount();
		String result = "";
		while (resultset.next()) {
			for (int column = 1; column <= coloumnsCount; column++) {
				result += " | " + metadata.getColumnName(column) + ":" + resultset.getString(column) + " | ";
			}
			result += "\n";
		} 
		return result;
	}

	public String executeOperation(String query) throws SQLException {
		int successful = statement.executeUpdate(query);
		if (successful > 0) {
			return query.substring(0,query.indexOf(' ')) + " operation is successful";
		}
		return query.substring(0,query.indexOf(' ')) + " operation Failed!!";
	}
}