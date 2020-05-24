import java.util.*;
import java.net.*;

public class LBUtils {

	private ArrayList<InetSocketAddress> listOfServers;
	private static LBUtils utilsObject = null;
	private int roundRobinSeed = 0;

	private LBUtils() {
		listOfServers = new ArrayList<InetSocketAddress>();
	}

	public static LBUtils getInstance() {
		if (utilsObject == null)
			return utilsObject = new LBUtils();
		return utilsObject;
	}

	public void addServer() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter server address :");
		String serverAddress = scanner.nextLine();
		System.out.println("Enter port:");
		int port = scanner.nextInt();
		listOfServers.add(new InetSocketAddress(serverAddress, port));
		System.out.println("Server added successfully.."+listOfServers.size());
	}

	public void deleteServer(InetSocketAddress serverToDelete) {	
		System.out.println("deleting server");
		for (InetSocketAddress server : listOfServers) {
			if (serverToDelete.equals(server)) {
				System.out.println(serverToDelete.getAddress() + " is deleted.");
				listOfServers.remove(server);
				break;
			}
		}
	}

	public void showServers() {
		for (InetSocketAddress server : listOfServers) {
			System.out.println(server.toString());
		}
		for (InetSocketAddress server : listOfServers) {
			System.out.println(server.toString());
		}
	}

	public InetSocketAddress getServerAddress() {
		int noOfServers = listOfServers.size();
		if (noOfServers == 0)
			return null;
		roundRobinSeed = (roundRobinSeed + 1) % noOfServers;
		return listOfServers.get(roundRobinSeed);
	}

	public ArrayList<InetSocketAddress> getListOfServers() {
		return listOfServers;
	}
}