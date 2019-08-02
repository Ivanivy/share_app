package networkdiscovery;

import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;

public class FileClient {

	ServerSocketChannel serverSocketChannel = null;
	SocketChannel client = null;
	public static int numberOfTasks=0;

	public FileClient(int PORT){
		try{
			serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.socket().bind(new InetSocketAddress(PORT));
			System.out.println("background file client binded to : "+PORT);
		}
		catch (Exception e){
			e.printStackTrace();
		}

	}




	public SocketChannel listenForAConnection(){
		try{
			client = serverSocketChannel.accept();
			return client;
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}



	public void receivefile(Path path,SocketChannel client) {
		LaunchThreadForReceivingFile launchThread = new LaunchThreadForReceivingFile("task"+this.numberOfTasks,client,path);
		launchThread.start();
		this.numberOfTasks++;
	}

	public void close(){
		try{
			this.serverSocketChannel.close();
		}
		catch(Exception e ){
			e.printStackTrace();
		}
	}
}
