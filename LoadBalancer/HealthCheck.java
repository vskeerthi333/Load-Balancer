import java.net.*;
import java.util.*;
import java.io.*;

public class HealthCheck implements Runnable {

 	private int HEALTHY_THRESHOLD = 10;
	private int UNHEALTHY_THRESHOLD = 2;
	private int HEALTHCHECK_INTERVAL = 30000; 
	private int RESPONSETIME = 5000; 
	private int PORT = 8888;
	private Socket socket;
	private LBUtils utils;
	private static HealthCheck healthcheck = null;
	private ArrayList<InetSocketAddress> listOfServers;

	private HealthCheck() {
		this.utils = LBUtils.getInstance();
		this.listOfServers = utils.getListOfServers();
	}

	public static HealthCheck getInstance() {
		if (healthcheck == null) {
			return healthcheck = new HealthCheck();
		}
		return healthcheck;
	}

	public void run() {
		while (true) {
			startChecking();
			try {
				Thread.sleep(HEALTHCHECK_INTERVAL);
			}
			catch(Exception e) {
				System.out.println(e);
			}
		}
	}

	private void startChecking() {
		for (InetSocketAddress serverAddress : listOfServers) {
			InetAddress host = serverAddress.getAddress();
			System.out.println(host);
			if (!isServerAlive(host)) {
				utils.deleteServer(serverAddress);
			}
		}
	}

	private boolean isServerAlive(InetAddress host) {
		int healthyCount = 0;
		int unhealthyCount = 0;
		while (healthyCount < 10 && unhealthyCount < 2) {
			if (isConnectionEstablished(host)) 
				healthyCount++;
			else 
				unhealthyCount++;
		}
		if (healthyCount >= 10 && unhealthyCount < 2) 
			return true;
		return false;
	}

	private boolean isConnectionEstablished(InetAddress host) {
		try {
			socket = new Socket(host, PORT);
			socket.setSoTimeout(RESPONSETIME); 

			OutputStream outToServer = socket.getOutputStream(); 
        	DataOutputStream signal = new DataOutputStream(outToServer); 
        	signal.writeUTF("isAlive");

        	InputStream inFromServer = socket.getInputStream();
        	DataInputStream receivedResponse = new DataInputStream(inFromServer); 
        	String response = receivedResponse.readUTF();

        	socket.close();

        	if (response.equalsIgnoreCase("true")) 
        			return true;
        }
        catch(Exception e){
        	System.out.println(e);
        }
        return false;
	}

}