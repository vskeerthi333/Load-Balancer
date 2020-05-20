import java.net.*;
import java.io.*;
import java.util.*;
import java.sql.*;


public class Client {

   public static void main(String [] args) {

      String serverName = args[0];
      int port = Integer.parseInt(args[1]);

      try {
         Socket client = new Socket(serverName, port);

         System.out.println("Write query:");
         String query = (new Scanner(System.in)).nextLine();

         OutputStream outToServer = client.getOutputStream(); 
         DataOutputStream out = new DataOutputStream(outToServer); 
         out.writeUTF(query);

         InputStream inFromServer = client.getInputStream();
         DataInputStream in = new DataInputStream(inFromServer); 
         System.out.println(in.readUTF());

         client.close();
      } 
      catch (IOException e) {
         e.printStackTrace();
      }
   }
}