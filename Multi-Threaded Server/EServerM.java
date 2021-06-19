import java.io.*;
import java.net.*;

public class EServerM{
 	public static void main(String[] args){
 		int port = Integer.parseInt(args[0]);
 		try{
 			ServerSocket serverSocket = new ServerSocket(port);
			int count = 0;
			System.out.println("Server Started !!");
	  		while(true)
	  		{
	   			Socket socket = serverSocket.accept();
				EServerMWorker worker = new EServerMWorker(socket,count++);
				worker.start();
	  		}
 		}catch(Exception e){
 			System.out.println(e.getMessage());
 		}

 	}
}
