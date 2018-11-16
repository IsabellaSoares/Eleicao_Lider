/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Process;

import Listener.MessageManagerListener;
import Listener.ServerManagerListener;
import Manager.ConnectionManager;
import Manager.MessageManager;
import Manager.ResourceManager;
import Model.ACK;
import Model.Structure;
import Model.Message;
import Model.Resource;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Marcelo
 */
public class Process {
    
    private static final int FREE = 0;
    private static final int USING_RESOURCE = 1;
    private static final int WAITING_RESPONSE_RESOURCE = 2;
    
    // variaveis referente a conexao
    private static int pid;
    private static int serverPort;
    private static int[] connectionPorts;
    private static int currentLogicalClock = 1;
    private static char name; //Nome do nó
    private static int capacity; //Capacidade do nó
    private static boolean flagConections = false; //Indica se o nó já criou conexões
    
    // gerenciadores
    private static ConnectionManager manager;
    private static MessageManager sendedRequest;
    private static MessageManager receivedRequest;
    private static ResourceManager resourceManager;
    
    private static Scanner keyboard;
    
    public Process(char name, int capacity, int pid, int serverPort, int[] connectionPorts){
        
        this.name = name;
        this.capacity = capacity;
        
        // inicializa as variaveis
        this.connectionPorts = connectionPorts;
        this.serverPort = serverPort;
        this.pid = pid;
        this.keyboard = new Scanner(System.in);
        this.manager = new ConnectionManager(serverPort); //Gerenciador de conexão
        this.sendedRequest = new MessageManager(pid);
        this.receivedRequest = new MessageManager(pid);
        this.resourceManager = new ResourceManager();
        
        // inicializa os listeners
        this.sendedRequest.setListener(new MessageManagerListener() {
            @Override
            public void notifyProcess(Structure structure) {
                Message message = structure.getMessage();
                if(!resourceManager.isUsing(message.getRequestedResource())){
                    Resource resource = new Resource();
                    resource.setResourceId(message.getRequestedResource());
                    
                    List<Structure> structures = receivedRequest.getMessageList();
                    for(Structure s : structures){
                        Message m = s.getMessage();
                        if(m!=null){
                            if(m.getRequestedResource()==message.getRequestedResource()){
                                resource.putMessage(m);
                            }
                        }
                    }
                    
                    resourceManager.addResource(resource);
                    System.out.println("[Log] O processo P"+pid+" começou a usar o recurso "+message.getRequestedResource());
                } else {
                    System.out.println("[Log] O processo P"+pid+" já está utilizando o recurso "+message.getRequestedResource());
                }
            }
        });
        
        this.manager.setServerManagerListener(new ServerManagerListener() {
            @Override
            public void messageReceived(Message message) {
                // recebeu uma mensagem
//                System.out.println("[Log] P"+message.getSenderPid()+" solicitou acesso ao recurso "+message.getRequestedResource());
                
                if((message.getType() == 2) && (flagConections == false)) {
                    for(int i=0; i<connectionPorts.length; i++){
//                        manager.addConnection(connectionPorts[i]);
                        System.out.println("Criou conexão com a porta "+ manager.addConnection(connectionPorts[i]));
                        initConections();
                        flagConections = true;
                    }
                }

//                if(resourceManager.isUsing(message.getRequestedResource())){
//                    resourceManager.putMessage(message.getRequestedResource(), message);
//                    sendACK(message, ACK.NACK);
//                } else if (sendedRequest.isRequestingFor(message.getRequestedResource())) {
//                    receivedRequest.addMessage(message);
//                    sendACK(message, ACK.NACK);
//                } else {
//                    sendACK(message, ACK.ACK);
//                }
//                if(message.getLogicalClock()> currentLogicalClock){
//                    currentLogicalClock = message.getLogicalClock();
//                }
            }

            @Override
            public void ACKReceived(ACK ack) {
                //Recebeu um ACK
//                if(ack.getType()==ACK.ACK){
//                    System.out.println("[Log] Recebeu um 'ACK' de P" + ack.getSenderPid()+" para o recurso "+ack.getRequestedResource()+".");
//                    sendedRequest.addAck(ack); //Adiciona ACK na lista
//                } else {
//                    System.out.println("[Log] Recebeu um 'NACK'. Aguardando o processo P"+ack.getSenderPid()+" terminar de utilizar o recurso "+ack.getRequestedResource()+".");
//                }
                    
            }
        });
        
        System.out.println("Iniciou nó "+name+" na porta "+serverPort);
    }
    
    public void exec() {
        
        if(this.pid == 1) {
            System.out.print("Pressione 1 para iniciar as conexões:\n>> ");
            keyboard.next();

            //Cria conexão com os outros processos
            for(int i=0; i<connectionPorts.length; i++){
//                manager.addConnection(connectionPorts[i]);
                System.out.println("Criou conexão com a porta "+ manager.addConnection(connectionPorts[i]));
                initConections();
                flagConections = true;
            }
        }
        
        keyboard.next();
        
//        System.out.print("\nMenu:\n 0 - Finaliza processo\n 1 - Solicitar acesso\n 2 - Liberar recurso\n");
        
//        int option;
//        do{
//            option = keyboard.nextInt();
//            if(option!=0){
//                switch(option){
//                    case 1 :
//                        requestAccess();
//                        break;
//                    case 2:
//                        freeResource();
//                        break;
//                }
//            }
//        } while(option!=0);
        
        manager.close();
        System.out.println("Finalizou nó "+name);
    }
    
    public static void sendACK(Message message, int type){
        try {
            ACK ack = new ACK();            
            ack.setMessageId(message.getMessageId());
            ack.setSenderPid(pid);
            ack.setDestinationPid(message.getSenderPid());
            ack.setLogicalClock(message.getLogicalClock());
            ack.setRequestedResource(message.getRequestedResource());
            ack.setType(type);
            
            manager.sendACKToServer(ack, message.getSenderPort());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void requestAccess() {
        try{
            int recurso = 0;
            System.out.print("Escolha um recurso: ");
            recurso = keyboard.nextInt();
            Message m = new Message(recurso, pid, ++currentLogicalClock, serverPort);
            sendedRequest.addMessage(m);
            manager.sendMessageToServer(m);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    
    private void initConections() {
        try{
            Message m = new Message(2, pid, ++currentLogicalClock, serverPort);
//            sendedRequest.addMessage(m);
            manager.sendMessageToServer(m);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void freeResource() {
        if(resourceManager.isEmpty()){
            System.out.println("Nenhum recurso está sendo utilizado!");
            return;
        }
        System.out.print("Escolha um recurso para liberar: ");
        resourceManager.printResourceId();
        System.out.print("\n>> ");
        
        int resourceId = keyboard.nextInt();
        
        if(resourceManager.isUsing(resourceId)){
            Resource resource = resourceManager.removeResource(resourceId);
            if(resource!=null){
                List<Message> messageList = resource.getMessageList();
                if(messageList!=null && messageList.size()>0){
                    for(Message message : messageList){
                        sendACK(message, ACK.ACK);
                    }
                }
            }
            System.out.println("[Log] O recurso "+resourceId+" foi liberado.");
        } else {
            System.out.println("O recurso "+resourceId+" não está sendo utilizado!");
        }
    }
}
