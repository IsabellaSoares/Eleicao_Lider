package process;

import connectionfactory.Client;
import connectionfactory.Server;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import listener.ElectionManagerListener;
import listener.ServerListener;
import manager.ConnectionManager;
import manager.ElectionManager;
import model.Message;

public class Process {
    
    private Scanner keyboard;
    private int pid;
    private ConnectionManager connectionManager;
    private int serverPort;
    private int currentLogicalClock = 1;
    private ElectionManager electionManager;
    private ElectionManager result;
    private int capacity;
    private boolean pauseProcess;
    
    public Process(int pid, int capacity, int serverPort, int[] connectionPorts){
        this.keyboard = new Scanner(System.in);
        this.pid = pid;
        this.pauseProcess = false;
        this.capacity = capacity;
        this.serverPort = serverPort;        
        this.result = new ElectionManager();        
        this.electionManager = new ElectionManager();
        this.electionManager.setListener(new ElectionManagerListener() {
            @Override
            public void notifyProcess() {
                receivedAllAnswers();
            }
        });
        this.connectionManager = new ConnectionManager(serverPort, connectionPorts);
        this.connectionManager.startServer(new ServerListener() {
            @Override
            public void messageReceived(Message message) {
                //System.out.println("recebeu mensagem: " + message.toString());
                received(message);
            }
        });
        System.out.println("[Process] Iniciou o nó "+getProcessName(pid));
    }
    
    public void exec() throws IOException{        
        int option;
        do{
            try{
                option = keyboard.nextInt();
            } catch (Exception e){
                e.printStackTrace();
                option = 20;
            }
            
            switch(option){
                case 1:
                    requestElection();
                    break;
                case 2:
                    changeCapacity();
                    break;
                case 3:
                    pauseProcess = !pauseProcess;
                    if(pauseProcess){
                        System.out.println("[Process] Processo pausado!");
                    } else {
                        System.out.println("[Process] Processo continuado!");
                    }
                    break;
            }
            
        } while(option!=0);
        connectionManager.closeServer();
    }
    
    private void changeCapacity(){
        try{
            capacity = keyboard.nextInt();
            System.out.println("[Process] Capacidade alterada!");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    
    private void requestElection(){
        Message m = new Message(Message.REQUEST_ELECTION, pid, ++currentLogicalClock, serverPort);
        m.setElectionId(m.getMessageId());
        try{
            int expectedAnswers = connectionManager.getNumberOfConnections();
            electionManager.setRequisition(m, expectedAnswers);
            connectionManager.sendMessageToAll(m);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    
    private synchronized void received(Message message){
        if(message==null || message.getMessageId()==0){
            return;
        }
        
        switch(message.getType()){
            case Message.REQUEST_ELECTION:
                
                if(electionManager.getElectionId()!=null){
                    
                    if(message.getElectionId()<electionManager.getElectionId()){
                        return;
                    } else if (message.getElectionId()==electionManager.getElectionId()){
                        // envia nack
                        Message m = new Message(Message.NACK, pid, ++currentLogicalClock, serverPort);
                        m.setElectionId(message.getElectionId());
                        try{
                            connectionManager.sendMessageToPort(m, message.getSenderPort());
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                        return;
                    }
                }
                System.out.println("\n[Process] Requição "+getProcessName(message.getSenderPid())+" -> "+message.getElectionId());
                Message m = new Message(Message.REQUEST_ELECTION, pid, ++currentLogicalClock, serverPort);
                m.setElectionId(message.getElectionId());
                int expectedAnswers = connectionManager.getNumberOfConnections()-1;
                electionManager.setRequisition(message, expectedAnswers);
                try{
                    connectionManager.sendMessageExceptToPort(m, message.getSenderPort());
                } catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case Message.NACK:
                if(electionManager.getElectionId()!=null){
                    if(electionManager.getElectionId()==message.getElectionId()){
                        System.out.println("[Process] NACK "+getProcessName(message.getSenderPid())+" -> "+message.getElectionId());
                        electionManager.addAnswer(message);
                    }
                }
                break;
            case Message.ACK:
                if(electionManager.getElectionId()!=null){
                    if(electionManager.getElectionId()==message.getElectionId()){
                        System.out.println("[Process] ACK "+getProcessName(message.getSenderPid())+" -> "+message.getElectionId());
                        electionManager.addAnswer(message);
                    }
                }
                break;
            case Message.RESULT_MESSAGE:
                if(result.getElectionId()!=null){
                    if(message.getElectionId()<=result.getElectionId()){
                        return;
                    }
                }
                System.out.println("[Process] Nó eleito "+getProcessName(message.getElectedNode())+" com a capacidade "+message.getElectedNodeCapacity()+"!");
                Message mResult = new Message(Message.RESULT_MESSAGE, pid, ++currentLogicalClock, serverPort);
                mResult.setElectionId(message.getElectionId());
                mResult.setElectedNode(message.getElectedNode());
                mResult.setElectedNodeCapacity(message.getElectedNodeCapacity());
                result.setRequisition(mResult, (connectionManager.getNumberOfConnections()-1));
                try{
                    connectionManager.sendMessageToAll(mResult);
                } catch (Exception e){
                    e.printStackTrace();
                }
                break;
        }
    }
    
    private void receivedAllAnswers(){
        Message requisition = electionManager.getRequisition();
        List<Message> answers = electionManager.getAnswers();
        int electedPid = pid;
            int electedCapacity = capacity;
            for(Message m : answers){
                if(m.getType()==Message.ACK){
                    if(m.getElectedNodeCapacity()>electedCapacity){
                        electedPid = m.getElectedNode();
                        electedCapacity = m.getElectedNodeCapacity();
                    } else if (m.getElectedNodeCapacity()==electedCapacity) {
                        if(m.getElectedNode()>electedPid){
                            electedPid = m.getElectedNode();
                            electedCapacity = m.getElectedNodeCapacity();
                        }
                    }
                }
            }
        if(requisition.getSenderPid()==pid){
            // exibir o no eleito
            System.out.println("[Process] Nó eleito "+getProcessName(electedPid)+" com a capacidade "+electedCapacity+"!");
            Message m = new Message(Message.RESULT_MESSAGE, pid, ++currentLogicalClock, serverPort);
            m.setElectionId(requisition.getElectionId());
            m.setElectedNode(electedPid);
            m.setElectedNodeCapacity(electedCapacity);
            result.setRequisition(m, connectionManager.getNumberOfConnections());
            try{
                connectionManager.sendMessageToAll(m);
            } catch (Exception e){
                e.printStackTrace();
            }
        } else {
            Message m = new Message(Message.ACK, pid, ++currentLogicalClock, serverPort);
            m.setElectionId(requisition.getElectionId());
            m.setElectedNode(electedPid);
            m.setElectedNodeCapacity(electedCapacity);
            try{
                connectionManager.sendMessageToPort(m, requisition.getSenderPort());
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    
    private char getProcessName(int pid) {
        switch(pid) {
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
}
