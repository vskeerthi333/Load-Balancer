import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;

public class Server {

	private ServerSocket serverSocket;
	private DBOperations DBObj = new DBOperations();

	public Server(int port) throws IOException {
		serverSocket = new ServerSocket(port);
	}

	public void startServer() throws SQLException {
		while (true) {
			try {
				System.out.println("waiting for query...");
				Socket server = serverSocket.accept();

            	DataInputStream receivedQuery = new DataInputStream(server.getInputStream());
            	String query = receivedQuery.readUTF();
            	String response = DBObj.execute(query); 

            	DataOutputStream queryResponse = new DataOutputStream(server.getOutputStream());
            	queryResponse.writeUTF(response);
            	System.out.println("Response sent..");

            	server.close();
         
			}
			catch (SocketTimeoutException s) {
            	System.out.println("Socket timed out!");
         	} 
         	catch (Exception e) {
            	e.printStackTrace();
         	}
		}
	}
}
