//this is the module witch will listen for broadcasting messages in the network 

package networkdiscovery;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import tools.BroadcastFrameChecker;
import chat.Chat;
import chat.ChatFrame;
import tools.Search;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import filetransfert.FileClient;
import services.SignalingFrame;

public  class ServerBroadcast {

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
	
	public void listen(int port,ArrayList<JsonObject> discoveredHosts,String hostname) throws Exception {

		//this will receive datagram packets
		DatagramSocket ds = new DatagramSocket(port);
		byte[] receive = new byte[65535];

		DatagramPacket Dpreceive ;

		JsonParser jsonParser = new JsonParser();
		JsonObject receivedFrame;
		BroadcastFrameChecker broadcastFrameChecker = new BroadcastFrameChecker();
		String data ;System.out.println("server is listening on  "+port);
		FileClient fileclient = new FileClient(NetworkScanner.TRANSFER_FILE_PORT);
		SocketChannel client ;
		Chat chat = new Chat(NetworkScanner.CHAT_PORT);
		ChatFrame chatFrame = new ChatFrame();


		//is always listening
		while(true) {
			Dpreceive = new DatagramPacket(receive,receive.length);
			ds.receive(Dpreceive);
			data = convertData(receive).toString();
			//converts the receive JSON string into JSON object
			receivedFrame = jsonParser.parse(data).getAsJsonObject();
			
			//check if the received host information already exists in the array list and if the
			//received information has the right frame

			if (receivedFrame.get("SERVICE").toString().replace("\"","").equals("availability")){
				//if the frame of the incoming information matches with the broadcastFrame then it is for discovering
				if(!discoveredHosts.contains(receivedFrame) && broadcastFrameChecker.check(receivedFrame,hostname)){
					discoveredHosts.add(receivedFrame);
					System.out.println(discoveredHosts);
				}
			}

			if(receivedFrame.get("SERVICE").toString().replace("\"","").equals("sendFile")){
				//if it matches with SignalingFrame then it is for receiving file
				if(SignalingFrame.check(receivedFrame)) {
					System.out.println("starting receiving file");
					//this is the signal that will permit to launch the file client here
					Path path = Paths.get(NetworkScanner.DEFAULT_DESTINATION_PATH+receivedFrame.get("fileName").toString().replace("\"",""));
					client = fileclient.listenForAConnection();
					fileclient.receivefile(path,client);
				}
			}

			if(receivedFrame.get("SERVICE").toString().replace("\"","").equals("chat")){
				System.out.println(receivedFrame);
				if(chatFrame.check(receivedFrame)){
					System.out.println("rting chat");
					String ip = Search.searchIpAddress(receivedFrame.get("clientName").toString().replace("\"",""),NetworkScanner.discoveredHosts);
					System.out.println(ip);
					chat.launchClient(ip,NetworkScanner.CHAT_PORT,receivedFrame.get("clientName").toString().replace("\"",""));
				}
			}




			receive = new byte[65535];
			if(convertData(receive).toString().equals("close")) {
				break;
			}
		}
		ds.close();
		fileclient.close();
	}
	
}
