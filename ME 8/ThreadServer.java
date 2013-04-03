import java.io.*;
import java.net.*;
import java.util.Date;

public class ThreadServer extends Thread{
	Socket s;
	
	
	public ThreadServer(Socket s){
		this.s = s;
		start();
	}
	
	public void run(){
		MyConnection conn = new MyConnection(s);
		
		String msgIn = "";
		String msgIn2 = "";
		String msgOut = "";
		msgIn = conn.getMessage();
		msgIn2 = msgIn;
		
		boolean check = true;
		
		//get header
		do {
			msgIn2 = conn.getMessage();
			msgIn = msgIn + msgIn2;
		}while (msgIn2.equals("Connection: keep-alive") == false);
			
					System.out.println(msgIn);
					if(msgIn.substring(0,4).equals("GET ")){
						String fileName = "";
						int i= 5;
						while(msgIn.substring(i, i+9).equals(" HTTP/1.1") == false){
							fileName = fileName + msgIn.charAt(i); i++;}
						
						if(fileName.equals("")) msgOut = "Connection established. Please specify a file name.";
						else{
							String fileType = "";
							i = 1;
							while(fileName.length() - i >= 0 && fileName.charAt(fileName.length() - i) != '.'){
								fileType = fileName.charAt(fileName.length() - i) + fileType;
								i++;
							}
							System.out.println(fileName);
							System.out.println(fileType);
							File file = new File(fileName);
							Date d = new Date();
							if (file.exists()) {
								int numOfBytes = (int)file.length();
								
								msgOut = "HTTP/1.1 200 OKConnection closeDate: " +  d + "Server: Apache/1.3.0 (Unix)Last-Modified: Sat, 02 February 2013, 03:11:06 CSTContent-Length: " + numOfBytes + "Content-Type: image/" + fileType + "\r\n" ;
								check = conn.sendMessage(msgOut);
								if(!check) System.out.println("Error.");
								System.out.println(msgOut);
								
								
								numOfBytes = conn.sendBytes(file);
								msgOut = "\n";
								//}
								
							}
							else {msgOut = "HTTP/1.1 404 Not foundConnection closeDate: " +  d + "Server: Apache/1.3.0 (Unix)Last-Modified: Sat, 02 February 2013, 03:11:06 CSTContent-Length: " + ("404 Not found").length() + "Content-Type: text/plain" + "\n\n" ;
								check = conn.sendMessage(msgOut);
								if(!check) System.out.println("Error.");
								System.out.println(msgOut);
								conn.sendMessage("404 Not found");
							
							}
						}
				
				
					}

		try{
			s.close();
		}catch(Exception e){}
		//	msgIn = conn.getMessage();
			
	}
	
}
