import java.io.*;
import java.net.*;


public class MyServer {
	public static void main(String args[]) {
		
		
			
		try {
			System.out.println("Server: Starting...");
			ServerSocket ssocket = new ServerSocket(80);

			System.out.println("Server: Waiting for connections...");
			
		while(true){	
			Socket socket = ssocket.accept(); // waiting
			System.out.print("Server: somebody connected!");
			System.out.println(socket.getInetAddress());
			new ThreadServer(socket);
			//socket.close();

		}
		
		
		} catch (Exception e){ e.printStackTrace(); }
	}
}

