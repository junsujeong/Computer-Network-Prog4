/**
 *  Name: Junsu Jeong, Vang Xiong
 *  Purpose: This is the client class of the pinger that runs the client aspect.
 */

import java.io.*;
import java.net.*;
import java.util.Random;

public class PingClient extends UDPPinger
{
   private static DatagramSocket socket;
   private static int num = 0;
   private long MaxRTT = 0;
   private long MinRTT = 1000;
   private double TotalRTT = 0.0;  
   private final int PORT = 5757;
   private final int TIMER = 1000;
   private final int ADDTIME = 5000;   
   private String[] ping = new String[10];
   private final float TIME_FINDER = 10.0f;
   
//----------------------------------------------------------------
// constructor 
//-----------------------------------------------------------------
   public PingClient(DatagramSocket socket) 
   {
      super(socket);
   }
   
   //----------------------------------------------------------------
   // main method that runs the client 
   //-----------------------------------------------------------------
   public static void main(String[] args)
   {
      try
      {
         socket = new DatagramSocket();
         num = 0;
         PingClient pingClient = new PingClient(socket);
         pingClient.run();
      }
      catch(IOException ex)
      {
         System.out.println(ex);
      }
   }
   
//----------------------------------------------------------------
// runs the client
//-----------------------------------------------------------------
   public void run()
   {
      try
      {
         UDPPinger udpPinger = new UDPPinger(socket);      
         InetAddress Address = InetAddress.getByName("137.104.121.5");
         System.out.println("Contacting host: " + Address.getHostAddress() 
                            + " at port " + PORT);   
         socket.setSoTimeout(TIMER);    
         for(int i = 0; i < 10; i++)
         {
            getMsg(i, Address, udpPinger);
         }          
         double AverRTT = TotalRTT / TIME_FINDER;
         for(int n = 0; n< num; n++)
             System.out.println(ping[n]);
         System.out.println("Minimum = " + MinRTT + "ms, Maximum = " + MaxRTT 
                            + "ms, Average = " + AverRTT + "ms.");            
      }
      catch(IOException ex)
      {
         System.out.println(ex);
      }
   }
//----------------------------------------------------------------
// calculates the rtt in milliseconds
//-----------------------------------------------------------------
   public void calculateRTT(long rtt)
   {
      TotalRTT += rtt;
      if(MaxRTT < rtt)
         MaxRTT = rtt;
      if(MinRTT > rtt)
         MinRTT = rtt;      
   }
   
//----------------------------------------------------------------
// gets the message of the pinger and rtt. 
//-----------------------------------------------------------------
   public void getMsg(int index, InetAddress add,UDPPinger pinger)
   {
      long SendTime = System.currentTimeMillis();
      String message = "Ping "+ index + " " + SendTime + "\n";
      PingMessage pingMsg = new PingMessage(add, PORT, message);
      pinger.sendPing(pingMsg);
      try
      {
         socket.setSoTimeout(ADDTIME);
         PingMessage msg = pinger.receivePing();
         long Rtt = System.currentTimeMillis() - SendTime; 
         if(Rtt > TIMER)
            Rtt = TIMER;
         if(msg != null)
         {
            calculateRTT(Rtt);
            ping[index] = ("PING " + index +  " true RTT: " + Rtt);
            num++;
         }
         else
         {
            calculateRTT(Rtt);
            ping[index] = ("PING " + index +  " false RTT: " + Rtt);
            num++;
         }
      }
      catch(Exception ex)
      {
      }   
   }
}
