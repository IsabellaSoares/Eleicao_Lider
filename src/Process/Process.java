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
import Model.Requisition;
import Model.Message;
import Model.ACK;
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
    private static boolean flagConnections = false; //Indica se o nó já criou conexões
    
    // gerenciadores
    private static ConnectionManager manager;
    private static MessageManager sendedRequest;
    private static MessageManager receivedRequest;
    private static MessageManager receivedResult;
    
    private static Scanner keyboard;
    private static Integer father;
    
    public Process(char name, int capacity, int pid, int serverPort, int[] connectionPorts){
        
        this.name = name;
        this.capacity = capacity;
        
        // inicializa as variaveis
        this.father = null;
        this.connectionPorts = connectionPorts;
        this.serverPort = serverPort;
        this.pid = pid;
        this.keyboard = new Scanner(System.in);
        this.manager = new ConnectionManager(serverPort); //Gerenciador de conexão
        this.sendedRequest = new MessageManager(pid);
        this.sendedRequest.setCanRemoveRequisition(true);
        this.receivedRequest = new MessageManager(pid);
        this.receivedResult = new MessageManager(pid);
        
        // inicializa os listeners
        // quando uma mensagem enviada recebe todos os acks, esse listener eh chamado
        this.sendedRequest.setListener(new MessageManagerListener() {
            @Override
            public void notifyProcess(Requisition requisition) {
                notificaProcesso(requisition);
            }
        });
        
        this.receivedResult.setListener(new MessageManagerListener() {
            @Override
            public void notifyProcess(Requisition requisition) {
                System.out.println("[Process] Todos receberam a informação do nó eleito");
            }
        });
        
        // quando esse processo recebe uma mensagem ou um ack, esse listener eh chamado
        this.manager.setServerManagerListener(new ServerManagerListener() {
            @Override
            public void messageReceived(Message message) {
                mensagemRecebida(message);
            }

            @Override
            public void ACKReceived(ACK ack) {
//                ackRecebido(ack);
            }
        });
        
        System.out.println("[Process] Iniciou nó "+name+" na porta "+serverPort);
    }
    
    public void exec() {
        
        if(this.pid == 1) {
            System.out.print("Pressione 1 para iniciar as conexões:\n>> ");
            keyboard.next();

            //Cria conexão com os outros processos
            for(int i=0; i<connectionPorts.length; i++){
                System.out.println("[Process] Criou conexão com a porta "+ manager.addConnection(connectionPorts[i]));
            }
            initConnections();
            flagConnections = true;
        }
        
        System.out.print("\nMenu:\n 0 - Finaliza processo\n 1 - Requisitar eleição\n 2 - Alterar Capacidade\n");
        
        int option;
        do{
            option = keyboard.nextInt();
            if(option!=0){
                switch(option){
                    case 1 :
                        requestElection();
                        break;
                    case 2:
                        changeCapacity();
                        break;
                    case 3:
//                        Message m = new Message(Message.RESULT_MESSAGE, pid, ++currentLogicalClock, serverPort);
//                        m.setElectionId(m.getMessageId());
//                        m.setElectedNode(5);
//                        m.setElectedNodeCapacity(5);
//                        try{
//                            manager.sendMessageToAll(m);
//                            receivedResult.addMessage(m, connectionPorts.length);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
                        break;
                }
            }
        } while(option!=0);
        
        manager.close();
        System.out.println("[Process] Finalizou nó "+name);
    }
    
    public static void sendACK(Message message, int type){
        try {
            Message ack = new Message();            
            ack.setMessageId(message.getMessageId());
            ack.setSenderPid(pid);
            ack.setDestinationPid(message.getSenderPid());
            ack.setLogicalClock(message.getLogicalClock());
            ack.setRequestedResource(message.getRequestedResource());
            ack.setType(type);
            ack.setElectionId(message.getElectionId());
            
            //manager.sendACKToServer(ack, message.getSenderPort());
            manager.sendMessageToPort(ack, message.getSenderPort());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void requestElection() {
        try{
            Message m = new Message(Message.REQUEST_ELECTION, pid, ++currentLogicalClock, serverPort);
            m.setElectionId(m.getMessageId());
            int sended = manager.sendMessageToAll(m);
            if(sended!=0){
                sendedRequest.addMessage(m, sended);
                System.out.println("[Process] Enviou requisição e está aguardando por "+sended+" respostas");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    
    private void initConnections() {
        try{
            Message m = new Message(Message.START_CONNECTIONS, pid, ++currentLogicalClock, serverPort);
            manager.sendMessageToAll(m);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void changeCapacity() {
        try{
            capacity = keyboard.nextInt();
            System.out.println("[Process] A capacidade foi alterada para: " + capacity);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    
    private synchronized void mensagemRecebida(Message message) {
        switch(message.getType()){
            case Message.REQUEST_ELECTION:
                System.out.println("[Process] Recebeu requisição de eleição do nó "+message.getSenderPidName()+" com Id de eleição: "+message.getElectionId());
                if(receivedRequest.existRequisitionFor(message)){
                    // jah foi recebida uma requisicao para essa eleicao e foi definido o pai desse processo para essa requisicao
                    // enviar um nack ou ok
                    System.out.println("[Process] Já foi recebida uma requisição para essa eleição, retorna NACK para o nó " + message.getSenderPidName());
                    sendACK(message, Message.NACK);
                } else {
                    // nova requisicao de eleicao
                    // salvar essa mensagem na lista de mensagens recebidas
                    // enviar outra mensagem para as demais conexoes
                    receivedRequest.addMessage(message, 1);
                    Message m = new Message(Message.REQUEST_ELECTION, pid, ++currentLogicalClock, serverPort);
                    m.setElectionId(message.getElectionId());
                    int sended;
                    try{
                        sended = manager.sendMessageExceptToPort(m, message.getSenderPort());
                    } catch (Exception e){
                        sended = 0;
                        e.printStackTrace();
                    }
                    if(sended!=0){
                        sendedRequest.addMessage(m, sended);
                        System.out.println("[Process] Propagou a eleição e está esperando por "+sended+" respostas");
                    } else {
                        System.out.println("[Process] Não propagou a eleição!");
                    }
                }
                
                break;
            case Message.START_CONNECTIONS:
                if(!flagConnections){
                    for(int i=0; i<connectionPorts.length; i++){
                        System.out.println("[Process] Criou conexão com a porta "+ manager.addConnection(connectionPorts[i]));
                    }
                    initConnections();
                    flagConnections = true;
                }
                break;
            case Message.ACK:
                System.out.println("[Process] Recebeu um ACK do nó "+message.getSenderPidName());
                sendedRequest.addAnswer(message);
                break;
            case Message.NACK:
                System.out.println("[Process] Recebeu um NACK do nó "+message.getSenderPidName());
                sendedRequest.addAnswer(message);
                break;
            case Message.RESULT_MESSAGE:
                //System.out.println("[Process] Recebeu um resultado do nó "+message.getSenderPidName());
                if(!receivedResult.existRequisitionFor(message)){
                    System.out.println("[Process] O nó "+message.getElectedNodeName()+" foi eleito o lider, com a capacidade " + message.getElectedNodeCapacity());
                    receivedResult.addMessage(message, (connectionPorts.length-1));
                    sendResultMessage(message);
                } else {
                    receivedResult.addAnswer(message);
                }
                break;
        }
    }
    
    private void notificaProcesso(Requisition structure) {
        List<Requisition> lista = receivedRequest.getRequisitionList();
        boolean flag = false;
        for(Requisition s : lista){
            if(s.getElectionId()==structure.getElectionId()){
                Integer electedNode = pid;
                Integer electedNodeCapacity = capacity;
                
                for(Message m : structure.getAnswerList()){
                    if(m.getType()==Message.ACK){
                        if(electedNodeCapacity==null || (electedNodeCapacity<m.getElectedNodeCapacity())){
                            electedNode = m.getElectedNode();
                            electedNodeCapacity = m.getElectedNodeCapacity();
                        }
                    }
                }
                
                Message message = s.getMessage();
                Message ack = new Message();            
                ack.setMessageId(message.getMessageId());
                ack.setSenderPid(pid);
                ack.setDestinationPid(message.getSenderPid());
                ack.setLogicalClock(message.getLogicalClock());
                ack.setRequestedResource(message.getRequestedResource());
                ack.setType(Message.ACK);
                ack.setElectionId(message.getElectionId());
                ack.setElectedNode(electedNode);
                ack.setElectedNodeCapacity(electedNodeCapacity);
                try{
                    manager.sendMessageToPort(ack, message.getSenderPort());
                } catch (Exception e){
                    e.printStackTrace();
                }
                System.out.println("[Process] Recebeu todas as respostas, retorna um ACK para o nó "+message.getSenderPidName());
                flag = true;
            }
        }
        
        if(!flag){
            List<Requisition> sendedRequisitions = sendedRequest.getRequisitionList();
            for(Requisition req : sendedRequisitions){
                if(req.getElectionId()==structure.getElectionId()){
                    Integer electedNode = pid;
                    Integer electedNodeCapacity = capacity;

                    for(Message m : structure.getAnswerList()){
                        if(m.getType()==Message.ACK){
                            if(electedNodeCapacity==null || (electedNodeCapacity<m.getElectedNodeCapacity())){
                                electedNode = m.getElectedNode();
                                electedNodeCapacity = m.getElectedNodeCapacity();
                            }
                        }
                    }
                    
                    System.out.println("[Process] O nó "+getElectedNodeName(electedNode)+" foi eleito o lider, com a capacidade " + electedNodeCapacity);
                    Message m = new Message(Message.RESULT_MESSAGE, pid, ++currentLogicalClock, serverPort);
                    m.setElectionId(m.getMessageId());
                    m.setElectedNode(electedNode);
                    m.setElectedNodeCapacity(electedNodeCapacity);
                    try{
                        manager.sendMessageToAll(m);
                        receivedResult.addMessage(m, connectionPorts.length);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    
    public char getElectedNodeName(int electedNode) {
        switch(electedNode) {
            case 1: return 'A';
            case 2: return 'B';
            case 3: return 'C';
            case 4: return 'D';
            case 5: return 'E';
            case 6: return 'F';
            case 7: return 'G';
            case 8: return 'H';
            case 9: return 'I';
            case 10: return 'J';
        }
        return ' ';
    }

    public void sendResultMessage(Message message){
        Message m = new Message(Message.RESULT_MESSAGE, pid, ++currentLogicalClock, serverPort);
        m.setElectionId(message.getElectionId());
        m.setElectedNode(message.getElectedNode());
        m.setElectedNodeCapacity(message.getElectedNodeCapacity());
        try{
            //manager.sendMessageToAll(m);
            manager.sendMessageExceptToPort(m, message.getSenderPort());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
