/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marcelo
 */
public class Message {
    
    public final static int REQUEST_ELECTION = 0;
    public final static int START_CONNECTIONS = 1;
    public final static int ACK = 2;
    public final static int NACK = 3;
    
    private int messageId; //Id da mensagem (<logicalClock><senderPid>)
    private int requestedResource; //Recurso solicitado
    private int senderPid; //Pid do processo que enviou a mensagem
    private int logicalClock; //Refere-se ao clock da mensagem
    private int type; // 0: requisitar um recurso; 1: liberar um recurso; 2: iniciar conexões
    private int senderPort;
    private int electionId;
    private int electedNode;
    private int electedNodeCapacity;
    private int destinationPid; //Processo que pediu o recurso

    public Message(){}
    
    public Message(int type, int senderPid, int logicalClock, int senderPort){
        this.type = type;
        this.senderPid = senderPid;
        this.logicalClock = logicalClock;
        this.messageId = (logicalClock*10)+senderPid;
        this.senderPort = senderPort;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getLogicalClock() {
        return logicalClock;
    }

    public void setLogicalClock(int logicalClock) {
        this.logicalClock = logicalClock;
    }

    public int getRequestedResource() {
        return requestedResource;
    }

    public void setRequestedResource(int requestedResource) {
        this.requestedResource = requestedResource;
    }

    public int getSenderPid() {
        return senderPid;
    }

    public void setSenderPid(int senderPid) {
        this.senderPid = senderPid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSenderPort() {
        return senderPort;
    }

    public void setSenderPort(int senderPort) {
        this.senderPort = senderPort;
    }

    public int getElectionId() {
        return electionId;
    }

    public void setElectionId(int electionId) {
        this.electionId = electionId;
    }

    public int getElectedNode() {
        return electedNode;
    }
    
    public char getElectedNodeName() {
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
    
    public char getSenderPidName() {
        switch(senderPid) {
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

    public void setElectedNode(int electedNode) {
        this.electedNode = electedNode;
    }

    public int getElectedNodeCapacity() {
        return electedNodeCapacity;
    }

    public void setElectedNodeCapacity(int electedNodeCapacity) {
        this.electedNodeCapacity = electedNodeCapacity;
    }

    public int getDestinationPid() {
        return destinationPid;
    }

    public void setDestinationPid(int destinationPid) {
        this.destinationPid = destinationPid;
    }
}
