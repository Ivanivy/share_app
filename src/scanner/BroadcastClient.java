package scanner;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class BroadcastClient {
	private DatagramSocket socket;
	public void brodcast(String broadcastMessage,InetAddress address,int port) throws IOException{
		socket = new DatagramSocket();
		socket.setBroadcast(true);
		byte[] buffer = broadcastMessage.getBytes();
		DatagramPacket packet = new DatagramPacket(buffer,buffer.length,address,port);
		socket.send(packet);
		socket.close();
	}
}
