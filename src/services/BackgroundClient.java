package services;

import networkdiscovery.BroadcastClient;
import networkdiscovery.AdressesScanner;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

public class BackgroundClient extends Thread{
	Thread t; 
	String name;
	int PORT ; 
	String clientName;
	HashMap<String,String> addresses ;
	AdressesScanner myscanner = new AdressesScanner();
	BroadcastClient broadcastclient = new BroadcastClient();
	BroadcastFrame broadcastframe ;
	
	public BackgroundClient(int PORT,String name,String clientName) {
		this.PORT = PORT;
		this.name = name;
		this.clientName = clientName;
	}
	
	public void run() {
		System.out.println("running client in background");
		try {
			while(true) {				
				addresses = myscanner.listAllAdresses();
				
				for(Map.Entry<String, String> element : addresses.entrySet()) {
					broadcastframe = new BroadcastFrame(this.clientName,element.getKey());
					broadcastclient.brodcast(broadcastframe.getJsonObject().toString(),InetAddress.getByName(element.getValue()),this.PORT);
				}
				Thread.sleep(500);
			}
		}catch(Exception e ) {
			System.out.println(e);
		}
	}
	
	public void start() {
		if(this.t==null) {
			t = new Thread(this,this.name);
			t.start();
		}
	}
	


}
