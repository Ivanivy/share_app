package scanner;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Objects;

public class AdressesScanner {
	
	ArrayList<InetAddress> listAllBroadcastAdresses() throws Exception{
		ArrayList<InetAddress> broadcastList = new ArrayList<>();
		Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
		while(interfaces.hasMoreElements()) {
			NetworkInterface networkInterface = interfaces.nextElement();
			if(networkInterface.isLoopback()|| !networkInterface.isUp()) {
				continue;
			}
			
			networkInterface.getInterfaceAddresses().stream()
				.map(a->a.getBroadcast())
				.filter(Objects::nonNull)
				.forEach(broadcastList::add);
		}
			return broadcastList;
	}
}
