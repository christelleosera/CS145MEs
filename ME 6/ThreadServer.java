import java.io.*;
import java.net.*;

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
		
		String currDir = "C:\\";
		File f = new File(currDir);
		boolean check = true;
		
		while (true){
				System.out.print("Client ");
				System.out.print(s.getInetAddress() + ":");
				System.out.println(" " + msgIn);

				msgOut = "";
				
				
				
				if (msgIn != null){
					if(msgIn.length() > 2 && msgIn.substring(0,3).equals("pwd")){
					msgOut = "Current directory is " + currDir;
					
					}
					
					else if(msgIn.substring(0,2).equals("ls")){
						msgOut = "***FILE LIST***";
						String[] fileList = f.list();
						for (int j = 0; j < fileList.length; j++) {
							if (fileList[j] != null) {
									msgOut = msgOut + ";" + fileList[j];
							   }
						}
					
					}
					
					else if(msgIn.substring(0,2).equals("cd")){
							if(msgIn.length() > 4){
								if(msgIn.charAt(3) == '.' && msgIn.charAt(4) == '.'){
									String parent = f.getParent();
									parent = parent + "\\";
									if(parent != null){
										f = new File(parent);
										msgOut = "Current directory changed to " + parent;
										currDir = parent;
									}
									else msgOut = "Current directory is " + currDir;
								}
								else{
									String dir = msgIn.substring(3);
									dir = currDir + dir + "\\";
									File dirFile = new File(dir);
									if(dirFile.exists() && dirFile.isDirectory()){
										f = new File(dir);
										msgOut = "Current directory changed to " + dir;
										currDir = dir;
									}
									else msgOut = "Cannot find directory " + dir;
								}
							}
							else {msgOut = "Please enter directory.";}
					}
					
					else{
						msgOut = "Invalid command.";
					}
					
				
				}
				
				check = conn.sendMessage(msgOut);
				if(!check) System.out.println("Error.");
				

				msgIn = conn.getMessage();
				msgIn2 = msgIn;
			}
			
	}
	
}