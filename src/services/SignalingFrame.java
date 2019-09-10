package services;

import com.google.gson.JsonObject;

public class SignalingFrame {
	
	private static final String APP_NAME = "ishare";
	private String fileName;
	private static final String SERVICE = "sendFile";

	public SignalingFrame(String fileName) {
		this.fileName = fileName;
	}
	
	public JsonObject getJson() {
		JsonObject frame = new JsonObject();
		frame.addProperty("APP_NAME", SignalingFrame.APP_NAME);
		frame.addProperty("fileName", this.fileName);
		frame.addProperty("SERVICE",this.SERVICE);
		return frame;
	}
	
	public static boolean check(JsonObject frame) {
		if(frame.get("APP_NAME").toString().equals("\"ishare\"") && !frame.get("fileName").toString().equals("\"\"") && frame.get("SERVICE").toString().equals("\"sendFile\"")){
			return true;
		}
		return false;
	}
}
