import java.io.*;
import java.net.*;

public class MyConnection {
	Socket socket;
	PrintWriter out;
	BufferedReader in;
	OutputStream os;
	InputStream is;
	
		public MyConnection(Socket s){
			socket = s;
			
			try{
			os = socket.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os);
			out = new PrintWriter(osw);
			
			} catch (Exception e){ e.printStackTrace();}
			
			try{
			is = socket.getInputStream();
			InputStreamReader isr = new InputStreamReader(is); 
			in = new BufferedReader(isr);
			} catch (Exception e){ e.printStackTrace();}
		}

		
		
		public boolean sendMessage(String msg){
			try{
			out.println(msg);
			out.flush();
			return true;
			} catch (Exception e){ e.printStackTrace(); return false; }
		}

		public String getMessage(){
			try{
			String msg = in.readLine();
			return msg;
			
			} catch (Exception e){ e.printStackTrace(); return "Error getting message.";}
		}
		
		public int sendBytes(File file){
			try{
				 byte [] bytearray  = new byte [(int)file.length()];
             	 FileInputStream fin = new FileInputStream(file);
            	 BufferedInputStream bin = new BufferedInputStream(fin);
             	 bin.read(bytearray,0,bytearray.length);
             	 
             	 os.write(bytearray,0,bytearray.length);
             	 os.flush();
				 
             	 return  0;
			}
			catch (Exception ex){ex.printStackTrace(); return -1;}
		}
	
		public void getBytes(int len, String fileName){
				int totalBytes = 0;
				byte [] bytearray  = new byte [len];
				
			try{
				FileOutputStream fos = new FileOutputStream(fileName);
        		BufferedOutputStream bos = new BufferedOutputStream(fos);
        		int bytesRead = is.read(bytearray,0,bytearray.length);
				totalBytes = bytesRead;
				
				while(bytesRead > 0){
          			bytesRead = is.read(bytearray, totalBytes, (bytearray.length-totalBytes));
          			if(bytesRead >= 0) totalBytes += bytesRead;
        		}
        		
        		bos.write(bytearray, 0 , totalBytes);
        		bos.flush();
			
				
        		}
			catch (Exception ex){ex.printStackTrace();}
			
		}

}
