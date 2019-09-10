package services;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import filetransfert.FileClient;
import networkdiscovery.NetworkScanner;

public class BackgroundSignalingFile extends Thread {
	
	private Thread t; 
	private int  PORT;
	private String name ;

	public BackgroundSignalingFile(int PORT, String name) {
		this.PORT  = PORT;
		this.name = name;
	}
	
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
	

	
	public void run() {
		try {
			System.out.println("the signaling server is turning in background on port "+this.PORT);
			DatagramSocket ds = new DatagramSocket(this.PORT);
			byte[] receive = new byte[65535];
			DatagramPacket dpreceive ;
			JsonParser jsonParser = new JsonParser();
			FileClient fileclient = new FileClient(NetworkScanner.TRANSFER_FILE_PORT);
			String data;
			SocketChannel client ;
			while(true) {
				dpreceive = new DatagramPacket(receive,receive.length);
				ds.receive(dpreceive);
				data = convertData(receive).toString();
				System.out.println(data);
				JsonObject broadcastFrame = jsonParser.parse(data).getAsJsonObject();
				if(SignalingFrame.check(broadcastFrame)) {
					System.out.println("starting receiving file");
					//this is the signal that will permit to launch the file client here
					Path path = Paths.get(NetworkScanner.DEFAULT_DESTINATION_PATH+broadcastFrame.get("fileName").toString().replace("\"",""));
					client = fileclient.listenForAConnection();
					fileclient.receivefile(path,client);
				}
				receive = new byte[65535];
				if(convertData(receive).toString().equals("close")) {
					break;
				}
			}
			ds.close();
			fileclient.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void start() {
		if(t==null) {
			t = new Thread(this,this.name);
			t.start();
		}
	}
	
}
