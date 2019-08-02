package networkdiscovery;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import com.google.gson.JsonObject;
import services.SignalingFrame;

public class SignalingFileClient {
	
	private DatagramSocket socket;

	public void signal(InetAddress address,int port,String fileName) throws IOException {
			
			socket = new DatagramSocket();
			SignalingFrame frame = new SignalingFrame(fileName);
			JsonObject jsonFrame = frame.getJson();
			byte[] buffer = jsonFrame.toString().getBytes();
			System.out.println(address);
			DatagramPacket packet = new DatagramPacket(buffer,buffer.length,address,port);
			socket.send(packet);
			socket.close();
			
	}
}
