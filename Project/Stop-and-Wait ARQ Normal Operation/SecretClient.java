
/**
 *
 * @author Swetsubhra Sarkar and Vishal Kumar Sinha
 */

import java.net.*;
import java.io.*;
import java.util.*;
 
public class SecretClient {
 
	public static void main(String[] args) {
	 
	    DatagramSocket cs = null;

		try {
			cs = new DatagramSocket();

			byte[] rd, sd;
			String GREETING = "Request HELLO \r\n", Acknowledgement;
			String reply;
			DatagramPacket sp,rp;
			boolean end = false;
			int sequnece=0;

			while(!end)
			{   	  
				// send Greeting      
			    sd=GREETING.getBytes();	 
			    sp=new DatagramPacket(sd,sd.length, 
									InetAddress.getByName(args[0]),
  									Integer.parseInt(args[1]));	 
				cs.send(sp);	
				//System.out.println("sent Greeting");

				// get next consignment
				rd=new byte[512];
				rp=new DatagramPacket(rd,rd.length); 
			    cs.receive(rp);	

				// print SECRET
				reply=new String(rp.getData());	 
				sequnece++;
				if (reply.contains("END")){ // last consignment
					Acknowledgement="ACK 0"+" \r\n";
					System.out.println(removeWord(reply, "END"));
					end = true;
				}
				else{
					Acknowledgement="ACK "+sequnece+" \r\n";
					System.out.println(reply);
				}
				sd=Acknowledgement.getBytes();
				//System.out.println(new String(sd));
				sp=new DatagramPacket(sd,sd.length, 
									InetAddress.getByName(args[0]),
  									Integer.parseInt(args[1]));
  				cs.send(sp);	
			}
		 
			cs.close();

		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}
 	

	 public static String removeWord(String string, String word)
    {
        if (string.contains(word)) {
            String tempWord = word + " ";
            string = string.replaceAll(tempWord, "");
            tempWord = " " + word;
            string = string.replaceAll(tempWord, "");
        }
        return string;
    }
}
