package tools;

import com.google.gson.JsonObject;

public class BroadcastFrameChecker {
	public boolean check(JsonObject broadcastFrame,String hostname) {
		if(broadcastFrame.get("APP_NAME").toString().equals("\"ishare\"") && broadcastFrame.get("ipAddress").toString()!="" && broadcastFrame.get("client").toString()!="" && !broadcastFrame.get("client").toString().equals("\""+hostname+"\"") && broadcastFrame.get("SERVICE").toString().equals("\"availability\""))  {
			System.out.println(broadcastFrame.get("client").toString().equals("\""+hostname+"\""));
			return true;
		}
		return false;
	}
}
