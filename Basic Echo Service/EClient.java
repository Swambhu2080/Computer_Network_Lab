import java.io.*;
import java.net.*;
import java.util.*;

public class EClient{
 	public static void main(String[] args){
 		try{
 			Socket client = new Socket("localhost",3000);
 			System.out.println("Connected!! Enter Your message :-");
	  		BufferedReader buff = new BufferedReader(new InputStreamReader (System.in));
	  		String message = buff.readLine();

	  		OutputStream out = client.getOutputStream();

	  		DataOutputStream dos = new DataOutputStream(out);      
	  		dos.writeUTF("Client Say :: " + message);
	  		dos.flush();

	  		client.close();
 		}catch(Exception e){
 			System.out.println(e.getMessage());
 		}
 	}
}
