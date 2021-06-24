import java.io.*;
import java.net.*;
import java.util.*;
public class PingClient{
	public static void main(String[] args) {
		String IP = args[0];
		int port = Integer.parseInt(args[1]);
		try{
			DatagramSocket cs = new DatagramSocket();
			cs.setSoTimeout(3000);
			InetAddress ip=InetAddress.getByName(IP);

			byte[] rd,sd;
			String reply;
			DatagramPacket sp,rp;
			int count=0,max=0,min=0;
			double loss=0,avar=0;
			Long time1,time2;

			System.out.println("\n**** Pinging Server ****\n");
			while(count<5){
				count++;

				Date d=new Date();
				String time=d + "";
				time1=d.getTime();
				String data="PING: "+count+" Time: "+time+" \r\n";

				rd=new byte[100];
				sd=data.getBytes();
				sp=new DatagramPacket(sd,sd.length,ip,port);
				rp=new DatagramPacket(rd,rd.length);

				cs.send(sp);

				try{
					cs.receive(rp);

					d=new Date();
					time2=d.getTime();
        			long timeDiff=time2 - time1;

        			avar+=timeDiff;
        			min=(int)timeDiff;
        			if(max<timeDiff){
        				max=(int)timeDiff;
        			}else if (min>timeDiff) {
        				min=(int)timeDiff;
        			}

					reply=new String(rp.getData());
					System.out.println("Reply from Server :: "+reply.substring(0,7)+" Time: "+timeDiff+"ms Bytes: "+data.length()+" ");
				}catch(SocketTimeoutException e){
					loss++;
					System.out.println(e.getMessage());
				}
			}
			System.out.println("----------------------------------------------------------");
			System.out.println(" Ping Statistics: ");
			System.out.println(" Packets: Sent = 5 Received = "+((int)(5-loss))+" Lost = "+((int)loss)+" ("+((int)((loss/5)*100))+"% loss)");
			System.out.println("-----------------------------------------------------------");
			System.out.println(" Approximate round trip times in milli-seconds: ");
			System.out.println(" Minimun = "+min+"ms , Maximun = "+max+"ms , Avarage = "+(avar/5)+"ms");
			cs.close();
		}catch(Exception e){
 			System.out.println(e.getMessage());
 		}
	}
}