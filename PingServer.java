/**
 * Name: Junsu Jeong, Vang Xiong
 * Purpose: The server class of the pinger. Runs the server.
 */

import java.io.*;
import java.net.*;
import java.util.*;

public class PingServer 
{

/*
 * Server to process ping requests over UDP.
 */
   private static final int PORT = 5757;
   private static final double LOSS_RATE = 0.3;
   private static final int AVERAGE_DELAY = 100;  // milliseconds
   private final static int DOUBLE = 2;
   private final static int PACKET_SIZE = 512;

//----------------------------------------------------------------
// main method which calls the run method to the server.  
//-----------------------------------------------------------------
   public static void main(String[] args) throws Exception
   {

      DatagramSocket socket = new DatagramSocket(PORT);
      System.out.println("Ping Server running....");
      runServer(socket);
   }
//----------------------------------------------------------------
// the run mehod that checks  
//-----------------------------------------------------------------
   public static void runServer(DatagramSocket sock) throws Exception
   {
      try
      {
         while (true) 
         { 
            Random random = new Random(new Date().getTime());
            System.out.println("Waiting for UDP packet....");
            DatagramPacket request = new DatagramPacket
                                    (new byte[PACKET_SIZE], PACKET_SIZE);
            sock.receive(request);
            String line = parse(request.getData());
            System.out.println( "Received from: " + line);
            if (random.nextDouble() < LOSS_RATE) 
            {
               System.out.println("Reply not sent.");
               continue;
            }
            Thread.sleep((int) (random.nextDouble() * DOUBLE * AVERAGE_DELAY));
            DatagramPacket reply = new DatagramPacket(request.getData(), 
                                   request.getData().length, 
                                   request.getAddress(), request.getPort());
            sock.send(reply);  
            System.out.println("Reply sent.");   
        }
      }
      catch (IOException ex)
      {
      }
   }
//----------------------------------------------------------------
// Reads the stream
//-----------------------------------------------------------------
   public static String parse(byte[] buf)
   {
      try
      {
         ByteArrayInputStream bais = new ByteArrayInputStream(buf);
         InputStreamReader isr = new InputStreamReader(bais);
         BufferedReader br = new BufferedReader(isr);  
         return br.readLine();
      }
      catch (IOException ex)
      {
         System.out.println(ex);
         return null;                 
      }         
   }
}
   
   


