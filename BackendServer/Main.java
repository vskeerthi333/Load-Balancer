import java.io.*;
import java.sql.*;
import java.util.*;

public class Main {
	public static void main(String[] args) throws IOException, SQLException {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter port Number to run server.");
		int port = scanner.nextInt();
		Server server = new Server(port);
		server.startServer();
	}
}