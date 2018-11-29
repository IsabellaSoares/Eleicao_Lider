/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;

import connectionfactory.Server;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import listener.ServerListener;
import model.Message;

/**
 *
 * @author Marcelo
 */
public class ConnectionManager {
    
    private int serverPort;
    private Server server;
    private int[] connectionPorts;
    
    public ConnectionManager(int serverPort, int[] connectionPorts){
        this.serverPort = serverPort;
        this.connectionPorts = connectionPorts;
    }
    
    public void startServer(ServerListener listener){
        server = new Server(serverPort);
        server.setServerListener(listener);
        server.startServer();
    }
    
    public void closeServer(){
        server.closeServer();
    }
    
    public int sendMessageToAll(Message message) throws SocketException, UnknownHostException, IOException, Exception {
        int sended = 0;
        for(int i=0; i<connectionPorts.length; i++){
            DatagramSocket clientSocket = new DatagramSocket();        
            InetAddress IPAddress = InetAddress.getByName("localhost");        
            byte[] sendData = new byte[1024];
            sendData = message.toString().getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, connectionPorts[i]);        
            clientSocket.send(sendPacket);
            clientSocket.close();
            sended++;
            //System.out.println("[ConnectionManager] Enviou para "+connectionPorts[i]);
        }
        return sended;
    }
    
    public int sendMessageToPort(Message message, int port) throws SocketException, UnknownHostException, IOException, Exception {
        DatagramSocket clientSocket = new DatagramSocket();        
        InetAddress IPAddress = InetAddress.getByName("localhost");        
        byte[] sendData = new byte[1024];
        sendData = message.toString().getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);        
        clientSocket.send(sendPacket);
        clientSocket.close();
        //System.out.println("[ConnectionManager] Enviou para "+port);
        return 1;
    }
    
    public int sendMessageExceptToPort(Message message, int port) throws SocketException, UnknownHostException, IOException, Exception {
        int sended = 0;
        for(int i=0; i<connectionPorts.length; i++){
            if(connectionPorts[i]!=port){
                DatagramSocket clientSocket = new DatagramSocket();        
                InetAddress IPAddress = InetAddress.getByName("localhost");        
                byte[] sendData = new byte[1024];
                sendData = message.toString().getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, connectionPorts[i]);        
                clientSocket.send(sendPacket);
                clientSocket.close();
                sended++;
                //System.out.println("[ConnectionManager] Enviou para "+connectionPorts[i]);
            }
        }
        return sended;
    }
    
    public int getNumberOfConnections(){
        if(connectionPorts!=null){
            return connectionPorts.length;
        }
        return 0;
    }
    
}
