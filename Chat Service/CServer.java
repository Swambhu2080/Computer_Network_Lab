import java.io.*;
import java.net.*;

public class CServer{
 	public static void main(String[] args){
 		int port = Integer.parseInt(args[0]);
 		try{
 			ServerSocket serverSocket = new ServerSocket(port);
 			System.out.println("Server Started");

	  		while(true){	
	  			Socket client = serverSocket.accept();

 				BufferedReader buff = new BufferedReader(new InputStreamReader (System.in));

	   			InputStream in = client.getInputStream();
	   			OutputStream out = client.getOutputStream();
	   			DataInputStream dis = new DataInputStream(in);
	   			DataOutputStream dos = new DataOutputStream(out);

	   			String serverMessage="",clientMessage="";
	   			boolean quit=false;

	   			while(!quit){
	   				clientMessage = dis.readUTF();
	   				System.out.println(clientMessage);
	   				if(clientMessage.contains("bye")){
	   					quit=true;
	   					System.out.println("Exiting.....");
	   				}else{
	   					serverMessage = buff.readLine();
			  			dos.writeUTF("Server Says :: " + serverMessage);
			  			dos.flush();
	   				}
	   			}
	   			dis.close();
	   			dos.close();
	   			client.close();
	  		}
 		}catch(Exception e){
 			System.out.println(e.getMessage());
 		}
 	}
}
