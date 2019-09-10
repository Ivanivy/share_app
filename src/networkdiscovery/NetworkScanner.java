package networkdiscovery;

import java.util.ArrayList;
import java.util.Scanner;

import userinterface.Menu;
import com.google.gson.JsonObject;
import services.BackgroundClient;
import services.BackgroundServer;


public class NetworkScanner {
	
	//properties & attributes
	public static Scanner clavier = new Scanner(System.in);
	//hostname for identity on network
	public static String hostname;
	//BackgroundClient will launch the thread for broadcast his presence on the network
	static BackgroundClient backgroundclient;

	//list of discovered host in an arraylist of form of json object {appname:"","ipaddress":"","client":"name of the client"}
	public static ArrayList<JsonObject> discoveredHosts = new ArrayList<>();

	//the port for network listening broadcasts and listening signalisation of files
	public static final int DISCOVERY_PORT = 3011;
	public static final int CHAT_PORT = 3012;
	public static final int TRANSFER_FILE_PORT = 9000;
	public static String DEFAULT_DESTINATION_PATH = "/home/ivan/";


	//creating object for launching menu
	static Menu menu = new Menu();

	//main method  witch will launch the main functionalities
	public static void main(String[] args) throws Exception {

		System.out.println("enter the host name");
		hostname = clavier.nextLine();
		BackgroundServer backgroundserver = new BackgroundServer(DISCOVERY_PORT, "serverthread",discoveredHosts,hostname);
		backgroundclient = new BackgroundClient(DISCOVERY_PORT,"clientthread",hostname);
		backgroundclient.start();
		backgroundserver.start();
		menu.launchMenu();


		}
	}



