import java.net.*;
import java.io.*;
 
public class TClient {
 
	public static void main(String[] args) throws Exception{
		String IP = args[0];
 		int port = Integer.parseInt(args[1]);
		try{
			System.out.println("Server Says ::>");
		 
		    DatagramSocket cs = new DatagramSocket();
		 
		    InetAddress ip=InetAddress.getByName(IP);
		 
		    byte[] rd=new byte[100];
		    byte[] sd=new byte[100];
		 
		    DatagramPacket sp=new DatagramPacket(sd,sd.length,ip,port);
		    DatagramPacket rp=new DatagramPacket(rd,rd.length);
			
		    System.out.println("Ip Addess :: "+ip);
		 
		    cs.send(sp);
		    cs.receive(rp);
		 
		    String time=new String(rp.getData());
		    System.out.println("Server time :: "+time);
		 
		    cs.close();
		}catch(Exception e){
 			System.out.println(e.getMessage());
 		}
	}
 
}
