package networkdiscovery;

import Chat.ChatFrame;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class SignalChat {
    private DatagramSocket socket;

    public void signal(InetAddress address, int port, String clientName) throws IOException {

        socket = new DatagramSocket();
        ChatFrame chatFrame = new ChatFrame();
        JsonObject jsonFrame = chatFrame.getJson(clientName);
        byte[] buffer = jsonFrame.toString().getBytes();
        System.out.println(address);
        DatagramPacket packet = new DatagramPacket(buffer,buffer.length,address,port);
        socket.send(packet);
        socket.close();


    }
}
