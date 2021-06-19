import java.io.*;
import java.net.*;

public class EServerMWorker extends Thread{
	Socket client;
	int number;
	public EServerMWorker(Socket client, int number){
		this.client = client;
		this.number=number;
	}

 	public void run(){
		System.out.println("Starting thread "+this.number);
		try{
			BufferedReader buff = new BufferedReader(new InputStreamReader (System.in));
			InputStream in = client.getInputStream();
			OutputStream out = client.getOutputStream();
			DataInputStream dis = new DataInputStream(in);
			DataOutputStream dos = new DataOutputStream(out);

			String clientMessage="",serverMessage="";
			boolean quit=false;

			while(!quit){
				clientMessage = dis.readUTF();
				System.out.println("Thread "+this.number+" : "+clientMessage);
				if(clientMessage.contains("bye")){
					quit=true;
					System.out.println("Thread "+this.number+" :: Client Left....");
				}
				else{
					serverMessage = buff.readLine();
				  	dos.writeUTF("Server Says :: " + serverMessage);
				  	dos.flush();
				}
			}
			dis.close();
	   		dos.close();
	   		client.close();
		} catch(IOException ex){
			System.out.println(ex.getMessage());
		} finally{
			System.out.println("Ending thread");
		}
 	}
}
