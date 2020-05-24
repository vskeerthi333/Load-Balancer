import java.io.*;
import java.net.*;
import java.util.*;

public class LoadBalancer {
	public static void main(String[] args) throws IOException {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter port Number to run LoadBalancer :");
		int port = scanner.nextInt();

		LBClientInterface loadbalancer = new LBClientInterface(port);
		HealthCheck healthcheck = HealthCheck.getInstance();
		Thread healthchecker = new Thread(healthcheck);
		healthchecker.run();

		loadbalancer.startServer();
	}
}