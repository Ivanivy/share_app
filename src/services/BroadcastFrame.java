package services;

import com.google.gson.JsonObject;

public class BroadcastFrame {
	private static final String APP_NAME="ishare";
	private String client,ipAddress;
	private static final String SERVICE = "availability";
	
	public BroadcastFrame(String client,String ipAddress) {
		this.client = client; 
		this.ipAddress = ipAddress;
	}
	
	public JsonObject getJsonObject() {
		JsonObject broadcastFrame = new JsonObject();
		broadcastFrame.addProperty("APP_NAME", BroadcastFrame.APP_NAME);
		broadcastFrame.addProperty("client", this.client);
		broadcastFrame.addProperty("ipAddress", this.ipAddress);
		broadcastFrame.addProperty("SERVICE",this.SERVICE);
		return broadcastFrame;
	}
	
}
