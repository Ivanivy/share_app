package filetransfert;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;

public class FileServer extends Thread {
	Thread t; 
	String name ;
	Path path;
	String address;
	int PORT ;
	
	public FileServer(String name) {
		this.name = name;
	}
	
	public void setParameters(Path path,String address,int PORT) {
		this.path = path;
		this.address = address;
		this.PORT = PORT;
	}
	
	public SocketChannel createChannel(String address,int PORT){
		try{
			SocketChannel socketChannel = SocketChannel.open();
			SocketAddress socketAddress = new InetSocketAddress(address,PORT);
			System.out.println("connecting..."+address+":"+PORT);
			socketChannel.connect(socketAddress);
			System.out.println("channel created");
			return socketChannel;
		}catch(Exception e){
			e.printStackTrace();
		}

		return null;
	}


	public void sendFile(SocketChannel socketChannel, Path path){
		try{
			FileChannel inChannel = FileChannel.open(path);
			final double fileSize = inChannel.size();
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			System.out.println("sending file...");
			while(inChannel.read(buffer)>0){
				System.out.println((float)100*inChannel.position()/fileSize);
				buffer.flip();
				socketChannel.write(buffer);
				buffer.clear();
			}
			socketChannel.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			System.out.println("starting file server");
			SocketChannel socketChannel = createChannel(this.address,this.PORT);
			sendFile(socketChannel,path);
			System.out.println("file sended");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void start() {
		if(this.t == null) {
			t = new Thread(this,this.name);
			t.start();
		}
	}
	
	
}
