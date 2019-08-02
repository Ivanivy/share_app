package Chat;

import com.google.gson.JsonObject;

public class ChatFrame {

    private final static String SERVICE = "chat";

    public JsonObject getJson(String clientName){
        JsonObject jsonFrame = new JsonObject();
        jsonFrame.addProperty("SERVICE",this.SERVICE);
        jsonFrame.addProperty("APP_NAME","ishare");
        jsonFrame.addProperty("clientName",clientName);
        return jsonFrame;
    }

    public boolean check(JsonObject jsonFrame){
        if(jsonFrame.get("APP_NAME").toString().equals("\"ishare\"") && jsonFrame.get("SERVICE").toString().replace("\"","").equals(SERVICE) && !jsonFrame.get("clientName").toString().equals("")){
            return true;
        }
        return false;
    }
}
