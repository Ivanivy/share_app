package networkdiscovery;

import java.util.ArrayList;

import com.google.gson.JsonObject;

public class BackgroundServer extends Thread {
	Thread t ; 
	String name;
	int PORT;
	ServerBroadcast broadcastserver = new ServerBroadcast();
	ArrayList<JsonObject> discoveredHosts ;
	String hostname;
	
	public BackgroundServer(int PORT,String name,ArrayList<JsonObject> discoveredHosts,String hostname){
		this.PORT = PORT;
		this.name = name;
		this.discoveredHosts = discoveredHosts;
		this.hostname = hostname;
	}
	
	public void run() {
		System.out.println("running brodcast server in background");
		try {
			broadcastserver.listen(this.PORT,this.discoveredHosts,this.hostname);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void start() {
		System.out.println("starting broadcast server ");
		if(this.t == null) {
			t = new Thread(this,this.name);
			t.start();
		}
	}
	
	
}
