package chat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Chat {

    static ServerSocket serverSocket;

    public Chat(int port) throws Exception{
        serverSocket = new ServerSocket(port);
    }

    public static void listen(String clientName) throws Exception{
        Socket socket;
        BufferedReader in;
        PrintWriter out;
        Scanner sc = new Scanner(System.in);
        socket = serverSocket.accept();
        out = new PrintWriter(socket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        Thread send = new Thread(new Runnable(){
            String msg;
            @Override
            public void run(){
                while(true){
                    System.out.println("enter a message");
                    msg = sc.nextLine();
                    out.println(msg);
                    out.flush();
                }
            }
        });
        send.start();

        Thread receive = new Thread(new Runnable(){
            String msg;
            @Override
           public void run(){
                try{
                    msg = in.readLine();
                    while(msg!=null){
                        System.out.println(clientName+": "+msg);
                        msg = in.readLine();
                    }
                    out.close();
                    socket.close();
                    serverSocket.close();
                }catch(Exception e){
                    e.printStackTrace();
                }

           }
        });

        receive.start();
    }

    public static void launchClient(String ipAddress,int port,String clientName) throws Exception{
        Socket socket;
        BufferedReader in;
        PrintWriter out;
        Scanner sc = new Scanner(System.in);
        socket = new Socket(ipAddress,port);
        System.out.println("client connected");
        out = new PrintWriter(socket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        Thread send = new Thread(new Runnable(){
           String msg;
           @Override
            public void run(){
               while(true){
                   System.out.println("enter a message");
                   msg = sc.nextLine();
                   out.println(msg);
                   out.flush();
               }
           }
        });
        send.start();

        Thread receive= new Thread(new Runnable(){
            String msg;
            @Override
            public void run(){
                try{
                    msg = in.readLine();
                    while(msg!=null){
                        System.out.println(clientName+": "+msg);
                        msg = in.readLine();
                    }
                    System.out.println("server disconnected");
                    out.close();
                    socket.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        receive.start();
    }
}

