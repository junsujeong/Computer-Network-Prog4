/**
 * Name: Junsu Jeong, Vang Xiong
 * Purpose: The UPD Pinger that implements the message sent and receives by the
 * pinger. 
 * 
 */

import java.io.*;
import java.net.*;

public class UDPPinger 
{
    final static int BYTE_NUMBER = 512;
   public DatagramSocket dataSocket;
   public UDPPinger(DatagramSocket socket)
   {
      dataSocket = socket;
   }
//----------------------------------------------------------------
// sends the pinger message
//-----------------------------------------------------------------
   public void sendPing(PingMessage ping)
   {
      try
      {
         String msg = ping.getIp() + " " +  ping.getPort() + " " + ping.getPayload();
         DatagramPacket sendPacket = new DatagramPacket(msg.getBytes(),
                 msg.length(), ping.getIp(), ping.getPort() );
         dataSocket.send(sendPacket);
      }
      
      catch(IOException ex)
      {
         System.out.println(ex);
      }
   }
//----------------------------------------------------------------
// receives the pinger message and prints out the message of 
// whether it was received or not.
//-----------------------------------------------------------------
   public PingMessage receivePing() throws SocketTimeoutException
   {
      try
      {
         DatagramPacket reply = new DatagramPacket(new byte[BYTE_NUMBER], 
                                                   BYTE_NUMBER);
         dataSocket.receive(reply);
         byte[] buf = reply.getData(); 
         ByteArrayInputStream bais = new ByteArrayInputStream(buf);
         InputStreamReader isr = new InputStreamReader(bais);
         BufferedReader br = new BufferedReader(isr);
         String line = br.readLine();
         PingMessage pingMsg = new PingMessage(reply.getAddress(), 
                                               reply.getPort(), line);
         long SendTime = System.currentTimeMillis();
         System.out.println("Received packet from " + reply.getAddress() 
                            + " " + reply.getPort() + " " + SendTime);
         return pingMsg;         
      }
      catch(SocketTimeoutException e)
      {
         System.out.println("receivePing..." + e + ": Receive timed out");
         return null;
      }
      catch(IOException ex)
      {
         return null;
      }
   }     
}
