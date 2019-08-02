package networkdiscovery;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;

class LaunchThreadForReceivingFile extends Thread{
    Thread t ;
    String name;
    SocketChannel client ;
    Path path;

    public LaunchThreadForReceivingFile(String name, SocketChannel client, Path path){
        this.name  = name;
        this.client = client;
        this.path = path;
    }

    public void run(){
        try{
            readFileFromSocketChannel(client,path);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void start(){
        if(this.t ==null){
            t = new Thread(this,this.name);
            t.start();
        }
    }

    private void readFileFromSocketChannel(SocketChannel socketChannel, Path path){
        try{
            FileChannel fileChannel = FileChannel.open(path, EnumSet.of(StandardOpenOption.CREATE,StandardOpenOption.TRUNCATE_EXISTING,StandardOpenOption.WRITE));
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while(socketChannel.read(buffer)>0){
                buffer.flip();
                fileChannel.write(buffer);
                buffer.clear();
            }
            fileChannel.close();
            FileClient.numberOfTasks--;
            System.out.println("receiving file successful");
           socketChannel.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}