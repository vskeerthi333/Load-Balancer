import java.net.*;
import java.io.*;
import java.util.*;

class HeartBeatHandler implements Runnable {

      private int port = 8889; // defualt port
      private ServerSocket serverSocket;

      public HeartBeatHandler(int port) throws IOException {
         this.port = port;
         serverSocket = new ServerSocket(port);
      }

      public HeartBeatHandler() throws IOException {
         serverSocket = new ServerSocket(port);
      }

      public void run() {
         while (true) {
            try { 
               Socket server = serverSocket.accept();
               DataInputStream receivedSignal = new DataInputStream(server.getInputStream());
            	String signal = receivedSignal.readUTF();

            	DataOutputStream responseToSignal = new DataOutputStream(server.getOutputStream());
               if (signal.equalsIgnoreCase("isAlive"))
            	  responseToSignal.writeUTF("true");
               responseToSignal.writeUTF("received Invalid Signal");

            	server.close();
			   }
			   catch (SocketTimeoutException s) {
               System.out.println("Socket timed out!");
            } 
            catch (Exception e) {
               System.out.println("Error in binding.");
            }
         }
	}
}