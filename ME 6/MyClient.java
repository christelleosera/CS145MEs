import java.io.*;
import java.net.*;

public class MyClient {
	public static void main(String args[]) {
		try {
			System.out.println("Client tries to connect to server...");
			Socket s = new Socket("127.0.0.1", 8888);
			System.out.println("Client connected!");

			MyConnection conn = new MyConnection(s);
			
			String msgIn = "";
			String msgOut = "";	
			while(true){
				BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
				System.out.printf("> ");msgOut = bufferRead.readLine();
				
				conn.sendMessage(msgOut);
				
				msgIn = conn.getMessage();
				if(msgIn != null && msgIn.length() > 14 && msgIn.substring(0,15).equals("***FILE LIST***") == true){
					msgIn = msgIn.substring(16);
					msgIn = msgIn.replaceAll(";", "\n");
				}
				
				System.out.println(msgIn);
			}
			
		} catch (Exception e){ e.printStackTrace(); }
	}
}

