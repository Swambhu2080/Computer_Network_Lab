import java.io.*;
import java.net.*;

public class EServer{
 	public static void main(String[] args){
 		try{
 			ServerSocket serverSocket = new ServerSocket(3000);
 			System.out.println("Server Started");
	  		while(true)
	  		{
	   			Socket client = serverSocket.accept();
	   			InputStream in = client.getInputStream();
	   			DataInputStream dis = new DataInputStream(in);
	   			System.out.println(dis.readUTF());
	   			client.close();
	  		}
 		}catch(Exception e){
 			System.out.println(e.getMessage());
 		}
 	}
}
