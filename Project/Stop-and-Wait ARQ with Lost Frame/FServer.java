/**
 *
 * @author Swambhu Mullick and Sunny Priyadarshi
 */
import java.net.*;
import java.io.*;
import java.util.*;

public class FServer {
	private static final double LOSS_RATE = 0.3;
    private static final int AVERAGE_DELAY = 100;  // milliseconds
    public static void main(String[] args) {
    	
    	DatagramSocket ss = null;
    	FileInputStream fis = null;
    	DatagramPacket rp, sp=null;
    	byte[] rd, sd, mmsg, temp;
    	byte[] RDT = new byte[] { 0x52, 0x44, 0x54 };
    	byte[] SEQ_0 = new byte[] { 0x30 };
    	byte[] END = new byte[] { 0x45, 0x4e, 0x44 };
    	byte[] CRLF = new byte[] { 0x0a, 0x0d };
    	InetAddress ip;
    	int port;
    	int sequnce=0,count=0;
    	try {
    		ss = new DatagramSocket(Integer.parseInt(args[0]));
    		System.out.println("Server is up....");
    		Random random = new Random();
    		int notsend=0;
    		String data,strGreeting, acknowledgement;
			int result = 0,frame=5; // number of bytes read
			boolean end=false;
			
			rd=new byte[100];
			rp = new DatagramPacket(rd,rd.length);
			ss.receive(rp);	 

			// get client's consignment request from DatagramPacket
			ip = rp.getAddress(); 
			port =rp.getPort();
			strGreeting = new String(rp.getData());

			if (new String(rp.getData()).contains("REQUEST")) {
				String a = strGreeting.trim();
                a = a.substring(7, a.length());//Sliceout the filename
                System.out.println("Reqeuest received from client for " +a);
                // read file into buffer
                fis = new FileInputStream(a);
                ss.setSoTimeout(30);
            }
            
            while(result!=-1 || !end){
            	rd=new byte[100];
            	sd=new byte[512];
				//decides whether to send the data or not
            	if (random.nextDouble() < LOSS_RATE) {
            		System.out.println("Forgot Consignment "+(sequnce+1)+" \n");
            		continue;
            	}
				if(notsend!=1){// prepare data
					result = fis.read(sd);
					sequnce++;
				}
				if (result == -1) {
					data=new String("RDT"+sequnce+"END\r\n");
					sd=new byte[(data.getBytes()).length];
					sd = data.getBytes();
					sp=new DatagramPacket(sd,sd.length,ip,port);
					end=true;
				}else{
					if(result<512){
						temp=sd;
						sd= new byte[result];
						sd= Arrays.copyOfRange(temp,0,result);
					}
					mmsg= new byte[(RDT.length)+((Integer.toString(sequnce)).length())+sd.length+CRLF.length];
					mmsg=concatenateByteArrays(RDT,(Integer.toString(sequnce)).getBytes(),sd,CRLF);
					sp=new DatagramPacket(mmsg,mmsg.length,ip,port);
					System.out.println("Sent Consignment "+sequnce);
				}
				ss.send(sp);
				//RECEIVE ACKNOWLEDGEMENT
				rp=null;
				try{
					rd=new byte[100];
					rp = new DatagramPacket(rd,rd.length);
					ss.receive(rp);
					acknowledgement = new String(rp.getData());
					System.out.println("Received " + acknowledgement);
					notsend=0;
					if (result==-1) {
						System.out.println("END");
					}
				}catch(SocketTimeoutException e){
					notsend=1;// acknowledgement not received from client
					System.out.println("Timeout ");
					end=false;
					continue;
				}
				rp=null;
				sp = null;			 
			}
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		} finally {
			try {
				if (fis != null)
					fis.close();
			} catch (IOException ex) {
				System.out.println(ex.getMessage());
			}
		}	
	}
	public static byte[] concatenateByteArrays(byte[] a, byte[] b, byte[] c, byte[] d) {
		byte[] result = new byte[a.length + b.length + c.length + d.length]; 
		System.arraycopy(a, 0, result, 0, a.length); 
		System.arraycopy(b, 0, result, a.length, b.length);
		System.arraycopy(c, 0, result, a.length+b.length, c.length);
		System.arraycopy(d, 0, result, a.length+b.length+c.length, d.length);
		return result;
	}
}
