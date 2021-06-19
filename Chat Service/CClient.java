import java.io.*;
import java.net.*;
import java.util.*;

public class CClient{
 	public static void main(String[] args){
 		String ip = args[0];
 		int port = Integer.parseInt(args[1]);
 		try{
 			Socket client = new Socket(ip,port);
 			System.out.println("Connected!! Enter Your message :-");

	  		BufferedReader buff = new BufferedReader(new InputStreamReader (System.in));

	  		InputStream in = client.getInputStream();
	  		OutputStream out = client.getOutputStream();
	  		DataInputStream dis = new DataInputStream(in);
	  		DataOutputStream dos = new DataOutputStream(out);

	  		String clientMessage="";

	  		while(!clientMessage.contains("bye")){
	  			clientMessage = buff.readLine();
	  			dos.writeUTF("Client Says :: " + clientMessage);
	  			dos.flush();
	  			if(!clientMessage.contains("bye")){
	  				System.out.println(dis.readUTF());
	   			}
	  		}
	  		dis.close();
	   		dos.close();
	  		client.close();
 		}catch(Exception e){
 			System.out.println(e.getMessage());
 		}
 	}
}
