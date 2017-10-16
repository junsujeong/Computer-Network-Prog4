/**
 * Name: Junsu Jeong, Vang Xiong
 * Purpose: Contains constructor and methods returning address and portnum and
 * payloadstring.
 */

import java.io.*;
import java.net.*;

public class PingMessage 
{
   private InetAddress address;
   private int portNum;
   private String payloadStr;
//----------------------------------------------------------------
// Constructor creates Ping message
//-----------------------------------------------------------------
   public PingMessage(InetAddress addr, int port, String payload)
   {
      address = addr;
      portNum = port;
      payloadStr = payload;
   }
   
//----------------------------------------------------------------
// returns address
//-----------------------------------------------------------------
   public InetAddress getIp()
   {
      return address;
   }
   
//----------------------------------------------------------------
// returns portNum
//-----------------------------------------------------------------
   public int getPort()
   {
      return portNum;
   }
   
//----------------------------------------------------------------
// returns payloadStr
//-----------------------------------------------------------------
   public String getPayload()
   {
      return payloadStr;
   }
}
