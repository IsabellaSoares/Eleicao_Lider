package connectionfactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Client {
    
    public Client (int serverPort, String sentence) throws SocketException, UnknownHostException, IOException {
        DatagramSocket clientSocket = new DatagramSocket();        
        InetAddress IPAddress = InetAddress.getByName("localhost");        
        byte[] sendData = new byte[1024];     
        sendData = sentence.getBytes();        
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, serverPort);        
        clientSocket.send(sendPacket);
        clientSocket.close();
    }
    
}
