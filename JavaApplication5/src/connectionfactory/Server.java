package connectionfactory;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import listener.ServerListener;
import model.Message;

public class Server {
    
    private int serverPort;
    private ServerListener serverListener;
    private DatagramSocket serverSocket;
    private Thread serverThread;
    
    public Server(int serverPort){
        this.serverPort = serverPort;
        this.serverThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    start();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void setServerListener(ServerListener serverListener) {
        this.serverListener = serverListener;
    }
    
    public void startServer(){
        
        try{
            serverSocket = new DatagramSocket(serverPort);
            serverThread.start();
        } catch (Exception e){
            e.printStackTrace();
            return;
        }
    }
    
    private void start() throws SocketException, IOException{
        while (true) {
            byte[] receiveData = new byte[1024];
            byte[] sendData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);
            String sentence = new String(receivePacket.getData());
            if(serverListener!=null){
                try{
                    Message message = new Message();
                    message.fromString(sentence);
                    serverListener.messageReceived(message);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            
        }
    }
    
    public void closeServer(){
        if(serverSocket!=null){
            try{
                serverThread.interrupt();
                serverThread.stop();
                serverSocket.close();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
