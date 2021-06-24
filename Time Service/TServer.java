import java.net.*;
import java.io.*;
import java.util.*;
 
public class TServer {
	public static void main(String[] args){
		int sPort = Integer.parseInt(args[0]);
		try{
			DatagramSocket ss = new DatagramSocket(sPort);
			while(true){
				System.out.println("Server is up....");
				 
				byte[] rd=new byte[100];
				byte[] sd=new byte[100];
				 
				DatagramPacket rp = new DatagramPacket(rd,rd.length);
				 
				ss.receive(rp);
				 
				InetAddress ip= rp.getAddress();
				int port=rp.getPort();
				 
				Date d=new Date();   // getting system time
				String time= d + "";  // converting it to String
				sd=time.getBytes();   // converting that String to byte
				
				System.out.println(port);
				System.out.println(ip);
				 
				DatagramPacket sp=new DatagramPacket(sd,sd.length,ip,port);
				 
				ss.send(sp);
				 
				rp=null;
				System.out.println("Done !! ");
			}
		}catch(Exception e){
 			System.out.println(e.getMessage());
 		}
	}
}

