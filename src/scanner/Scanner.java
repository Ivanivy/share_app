package scanner;

public class Scanner {
	public static void main(String[] args) {
		String broadcastAddress ;
		AdressesScanner myscanner = new AdressesScanner();
		BroadcastClient broadcastclient = new BroadcastClient();
		try {
			broadcastAddress = myscanner.listAllBroadcastAdresses().get(0).getCanonicalHostName();
			System.out.println(broadcastAddress);
			broadcastclient.brodcast("bonjour", myscanner.listAllBroadcastAdresses().get(0), 5006);
		}catch(Exception e ) {
			System.out.println(e);
		}
	}

}

