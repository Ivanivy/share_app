package videostream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;
import fileexplorer.FileExplorer;

public class JavaHttpServer {

//    public static void main(String args[]) throws Exception {
    public static void launchVideoServer() throws Exception{
        HttpServer server =HttpServer.create(new InetSocketAddress(3000),0);
        server.createContext("/stream",new Myhandler(new FileExplorer().explore()));
        server.setExecutor(null);
        server.start();

    }



    public static boolean isVideo(String path){
        return true;
    }



    static class Myhandler implements HttpHandler{
        private String videoPath ;

        public Myhandler(String videoPath){
            this.videoPath = videoPath;
        }
        @Override
        public void handle(HttpExchange t ) {
            try{
                 t.getResponseHeaders().set("Content-Type","video/*");
                OutputStream os = t.getResponseBody();
                File file = new File(videoPath);
                t.sendResponseHeaders(200,file.length());
                byte[] buff = new byte[1024];
                FileInputStream fs = new FileInputStream(file);
                int count = 0;
                while((count=fs.read(buff))>=0){
                    os.write(buff,0,count);
                }
                os.close();

            }catch(Exception e){
                e.printStackTrace();
            }

        }

    }
}