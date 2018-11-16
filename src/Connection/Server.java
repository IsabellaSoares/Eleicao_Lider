/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;

import Listener.ServerListener;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marcelo
 */
public class Server {
    
    private ServerSocket server;
    private int port;
    private ServerListener serverListener;
    private List<Connection> connectionList = null;
    private Thread serverThread;
    
    public Server(int port){
        this.port = port;
        this.connectionList = new ArrayList<>();
        startServer();
    }
    
    private void startServer(){
        try{
            server = new ServerSocket(port);
            serverThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        while(true){
                            Socket socket = server.accept();
                            Connection conn = new Connection(socket, serverListener);
                            conn.start();
                            connectionList.add(conn);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            serverThread.start();
        } catch (Exception e) {
            server = null;
            e.printStackTrace();
        }
    }

    public void setServerListener(ServerListener serverListener) {
        this.serverListener = serverListener;
    }
    
    public void close() throws Exception{
        closeServerThread();
        for(Connection conn : connectionList){
            conn.close();
        }
        server.close();
    }
    
    public void closeServerThread() throws Exception{
        if(serverThread!=null){
            serverThread.interrupt();
            serverThread.stop();
        }
    }
    
}
