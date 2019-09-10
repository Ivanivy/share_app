package userinterface;

import chat.SignalChat;
import fileexplorer.FileExplorer;
import filetransfert.FileServer;
import filetransfert.SignalingFileClient;
import services.BackgroundChat;

import java.net.InetAddress;
import java.nio.file.Paths;

import static networkdiscovery.NetworkScanner.*;

public class Menu {
    public Menu(){

    }

    public void launchMenu() throws  Exception{

        while(true) {
            System.out.println("choose an option");
            System.out.println("1:display available hosts");
            System.out.println("2:send a file");
            int choice ;
            FileExplorer fileExplorer = new FileExplorer();
            choice = clavier.nextInt();
            switch(choice) {

                case 1:
                    System.out.println(discoveredHosts);
                    break;

                case 2:
                    if (discoveredHosts.isEmpty()){
                        System.out.println("there is not host available");
                    }else{
                        String sendPath ;
                        System.out.println("entez the path of the file");
                        sendPath = fileExplorer.explore();
                        if(sendPath.equals("")){
                            break;
                        }
                        System.out.println(discoveredHosts);
                        System.out.println("enter the host number");
                        choice = clavier.nextInt();
                        if(choice>=discoveredHosts.size()){
                            System.out.println("no such host ...");
                            break;
                        }
                        String destinationHost =discoveredHosts.get(choice).get("ipAddress").toString().replace("\"", "");
                        System.out.println(destinationHost);
                        System.out.println("try to send "+sendPath);
                        FileServer fileserver = new FileServer("serverthread");

                        fileserver.setParameters(Paths.get(sendPath), destinationHost,TRANSFER_FILE_PORT);
                        SignalingFileClient signalingfileclient = new SignalingFileClient();
                        signalingfileclient.signal(InetAddress.getByName(destinationHost),DISCOVERY_PORT,fileExplorer.getFileName());
                        Thread.sleep(5000);
                        fileserver.start();
                    }

                    break;

                case 3:
                    String receivePath ;
                    System.out.println("enter the destination path");
                    receivePath = clavier.nextLine();
                    System.out.println(receivePath);

                case 4:
                    System.out.println("launching chat");
                    if (discoveredHosts.isEmpty()){
                        System.out.println("there is not host available");
                    }else{
                        System.out.println(discoveredHosts);
                        System.out.println("enter the host number");
                        choice = clavier.nextInt();
                        if(choice>=discoveredHosts.size()){
                            System.out.println("no such host ...");
                            break;
                        }
                        String destinationHost =discoveredHosts.get(choice).get("ipAddress").toString().replace("\"", "");
                        System.out.println(destinationHost);
                        SignalChat signalChat = new SignalChat();
                        BackgroundChat backgroundChat= new BackgroundChat(discoveredHosts.get(choice).get("client").toString().replace("\"",""));
                        backgroundChat.start();
                        signalChat.signal(InetAddress.getByName(destinationHost), DISCOVERY_PORT, hostname);
                    }
                    break;

                default:
                    System.out.println("invalid choice");
                    break;

            }
    }
}
}
