import java.net.*;
import java.io.*;
import java.util.*;
 
public class FClient {
 	private static final double LOSS_RATE = 0.3;
    private static final int AVERAGE_DELAY = 100;
	public static void main(String[] args) {
	 
	    DatagramSocket cs = null;
		FileOutputStream fos = null;

		try {

	    	cs = new DatagramSocket();
			Random random = new Random();
			byte[] rd, sd, data;
			byte[] RDT = new byte[] { 0x52, 0x44, 0x54 };
			byte[] SEQ_0 = new byte[] { 0x30 };
	    	byte[] END = new byte[] { 0x45, 0x4e, 0x44 };
	    	byte[] CRLF = new byte[] { 0x0a, 0x0d };
			String reply="";
			DatagramPacket sp,rp;
			int count=1,prevrdt=9;
			boolean end = false;
			String filename="demoText.html",GREETING = "REQUEST"+filename+"\r\n", Acknowledgement;
			int frame=5,i=0,flag=0;
			// write received data into demoText1.html
			fos = new FileOutputStream(filename.split("\\.")[0] + "1." + filename.split("\\.")[1]);

			// send Greeting      
		    sd=GREETING.getBytes();	 
		    sp=new DatagramPacket(sd,sd.length, InetAddress.getByName(args[0]),Integer.parseInt(args[1]));	 
			cs.send(sp);

			while(!end)
			{   
				//recieve data
				// get next consignment

				rd=new byte[515+(Integer.toString(prevrdt)).length()];
				rp=new DatagramPacket(rd,rd.length); 
			    cs.receive(rp);	

				// Converting data into String
			    reply=new String(rp.getData());	 

			    //Checking if the data has been recieved earlier if so then discard
			    if(prevrdt==Integer.parseInt((reply.substring(3,4)))){
					System.out.println("Received duplicate data for RDT "+(reply.substring(4,5))+"......Discarding");
			    }
				else{//else print the data
					prevrdt=Integer.parseInt((reply.substring(3,4)));
					System.out.println(reply);
					if (!(reply.contains("END"))){//write the data excluding the end part
						for(i=0;i<(rp.getData()).length;i++){
							if(rp.getData()[i]==0x00){
								flag=1;
								break;
							}
						}
						if (flag==1) {
							data= Arrays.copyOfRange(rp.getData(), (reply.substring(3,4)).length() + 3, i - CRLF.length);
						}else{
							data= Arrays.copyOfRange(rp.getData(), (reply.substring(3,4)).length() + 3, rp.getData().length + 2 - CRLF.length);
						}
						fos.write(data);
					}
				}
				//decides whether to send the Acknowledgement or not
				if (random.nextDouble() < LOSS_RATE) {
					System.out.println("Forgot to send the acknowledgement \n");				
				}else{
					if (reply.contains("END")) {// if last consignment
						end = true;
						Acknowledgement = "ACK 0"+" \r\n";
					}
					//send acknowledgement
					else
						Acknowledgement = "ACK "+ count+" \r\n";
					// send ACK      
				    sd=Acknowledgement.getBytes();	
				    sp=new DatagramPacket(sd,sd.length, 
										  InetAddress.getByName(args[0]),
	  									  Integer.parseInt(args[1]));	  
					cs.send(sp);
					count++;
				}	
			}
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		} finally {
			try {
				if (fos != null)
					fos.close();
				if (cs != null)
					cs.close();
			} catch (IOException ex) {
				System.out.println(ex.getMessage());
			}
		}
	}
}