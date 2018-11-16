/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marcelo
 */
public class Client {
    
    private List<Socket> socketList = null; //Lista de conexões 
    private int port;
    
    //Lista de clientes
    public Client(){
        socketList = new ArrayList<>();
    }
    
    //Adiciona as conexões
    public void addConnection (int port) {
        try{
            Socket client = new Socket("localhost", port);
            socketList.add(client);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //Envia a mensagem para o servidor
    public void outToServer (String json) throws Exception {
        for(int i=0; i<socketList.size(); i++){
            Socket client = socketList.get(i);
            DataOutputStream outToServer = new DataOutputStream(client.getOutputStream());
            
            if(client!=null && outToServer!=null){
                try{
                    outToServer.writeBytes(json + '\n');
                    outToServer.flush();
                } catch (Exception e) {
                    //e.printStackTrace();
                    client.close();
                    socketList.remove(i--);
                }
            }
        }
    }
    
    //Envia a mensagem para o servidor
    public void outToServer (String json, int serverPort) throws Exception {
        for(int i=0; i<socketList.size(); i++){
            Socket client = socketList.get(i);
            if(client.getPort()==serverPort){
                DataOutputStream outToServer = new DataOutputStream(client.getOutputStream());

                if(client!=null && outToServer!=null){
                    try{
                        outToServer.writeBytes(json + '\n');
                        outToServer.flush();
                    } catch (Exception e) {
                        //e.printStackTrace();
                        client.close();
                        socketList.remove(i--);
                    }
                }
            }
        }
    }
    
    //Fecha socket
    public void close() throws Exception {
        for(Socket client : socketList){
            client.close();
        }
    }
}