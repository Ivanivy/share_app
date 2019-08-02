package networkdiscovery;

import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

public class AdressesScanner {
	
	String address,broadcast;
	
	public HashMap<String,String> listAllAdresses() throws Exception{
		
		HashMap <String,String> addresses= new HashMap<>();
		
		Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
		//System.out.println("scanning interfaces");
		
		while(interfaces.hasMoreElements()) {
			NetworkInterface networkInterface = interfaces.nextElement();
			if(networkInterface.isLoopback()|| !networkInterface.isUp()) {
				continue;
			} 
			
			//the values of the ip and broadcast address differs on the running platform
			if(System.getProperties().getProperty("os.name").toLowerCase().contains("linux")) {
				address = networkInterface.getInterfaceAddresses().get(1).getAddress().getCanonicalHostName();
				broadcast = networkInterface.getInterfaceAddresses().get(1).getBroadcast().getCanonicalHostName();
			}
			else if(System.getProperties().getProperty("os.name").toLowerCase().contains("windows")) {
				address = networkInterface.getInterfaceAddresses().get(0).getAddress().getHostAddress();
				broadcast = networkInterface.getInterfaceAddresses().get(0).getBroadcast().getHostAddress();
			}
			
			
			addresses.put(address, broadcast);
		}
			return addresses;
	}
	
}
