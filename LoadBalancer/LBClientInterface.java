import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;

public class LBClientInterface {

	private ServerSocket lbSocket;
	private String request;
	private String response;
	private LBUtils utils;

	public LBClientInterface(int port) throws IOException {
		lbSocket = new ServerSocket(port);
		utils = LBUtils.getInstance();
	}

	public void startServer() {
		while (true) {
			try {
				System.out.println("Waiting...");
				Socket server = lbSocket.accept();

            	DataInputStream receivedRequest = new DataInputStream(server.getInputStream());
            	request = receivedRequest.readUTF();
				
				response = sendRequest(request);

            	DataOutputStream sendResponse = new DataOutputStream(server.getOutputStream());
            	sendResponse.writeUTF(response);

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

	public String sendRequest(String query) {
		
		if (query.startsWith("UPDATE") || query.startsWith("INSERT")) {
			ArrayList<InetSocketAddress> listOfServers = utils.getListOfServers();
			return sendAndReceiveRequest(query, listOfServers);
		}

		else {
			InetSocketAddress server = utils.getServerAddress();
			return sendAndReceiveRequest(query, server);
		}
		
	}

	public String sendAndReceiveRequest(String query, ArrayList<InetSocketAddress> listOfServers) {
		String response = null;
		for (InetSocketAddress server : listOfServers) {
			response = sendAndReceiveRequest(query, server);
		}
		return response;
	}

	public String sendAndReceiveRequest(String query, InetSocketAddress server) {
		try {
			Socket socket = new Socket(server.getAddress(), server.getPort());

			OutputStream outToServer = socket.getOutputStream(); 
        	DataOutputStream request = new DataOutputStream(outToServer); 
        	request.writeUTF(query);

        	InputStream inFromServer = socket.getInputStream();
        	DataInputStream receivedResponse = new DataInputStream(inFromServer); 
        	String response = receivedResponse.readUTF();

        	socket.close();
        }
        catch(Exception e) {
        	System.out.println(e);
        }
        return response;
	}
}


