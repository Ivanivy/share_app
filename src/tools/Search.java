package Tools;

import com.google.gson.JsonObject;

import java.util.ArrayList;

public class Search {
    public static String searchIpAddress(String clientName, ArrayList<JsonObject> discoveredHosts){
        for (JsonObject host : discoveredHosts) {
            if(host.get("client").toString().replace("\"","").equals(clientName)){
                return host.get("ipAddress").toString().replace("\"","");
            }
        }
        return null;
    }
}
