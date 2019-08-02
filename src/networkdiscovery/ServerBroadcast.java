//this is the module witch will listen for broadcasting messages in the network 

package networkdiscovery;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ServerBroadcast {
	
		
	//convert a byte string to a string 
	private static StringBuilder convertData(byte[] a) {
		if(a==null) return null;
		StringBuilder ret = new StringBuilder();
		int i =0;
		while(a[i]!=0) {
			ret.append((char) a[i]);
			i++;
		}
		return ret;
	}
	
	public void listen(int port,ArrayList<JsonObject> discoveredHosts,String hostname) throws IOException {
		
		DatagramSocket ds = new DatagramSocket(port);
		byte[] receive = new byte[65535];
		DatagramPacket Dpreceive = null;
		String data ;
		System.out.println("server is listening on  "+port);
		
		JsonParser jsonParser = new JsonParser();
		JsonObject broadcastFrame = new JsonObject();
		BroadcastFrameChecker broadcastFrameChecker = new BroadcastFrameChecker();
		
		//is always listening
		while(true) {
			Dpreceive = new DatagramPacket(receive,receive.length);
			ds.receive(Dpreceive);
			data = convertData(receive).toString();
			//converts the receive JSON string into JSON object
			broadcastFrame = jsonParser.parse(data).getAsJsonObject();
			
			//check if the received host informations already exists in the array list and if the 
			//received information has the right frame
			if(!discoveredHosts.contains(broadcastFrame) && broadcastFrameChecker.check(broadcastFrame,hostname)){
				discoveredHosts.add(broadcastFrame);
				System.out.println(discoveredHosts);
			}
			
			receive = new byte[65535];
			if(convertData(receive).toString().equals("close")) {
				break;
			}
		}
		ds.close();
	}
	
}
