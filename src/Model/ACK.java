/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Marcelo
 */
public class ACK {
    
    public static final int ACK = 0;
    public static final int NACK = 1;
    
    private int messageId; // id da mensagem que este se refere
    private int senderPid; // PID do processo que está enviando o ACK
    private int destinationPid; //Processo que pediu o recurso
    private int logicalClock; // Clock
    private int requestedResource; //Recurso que o processo quer utilizar
    private int type;
    private int electionId;
    private char processName;
    private int processCapacity;
    
    public ACK(){
        this.processName = ' ';
    }
    
    public ACK(int senderPid, int destinationPid, int messageId, int type){
        this.senderPid = senderPid;
        this.destinationPid = destinationPid;
        this.messageId = messageId;
        this.type = type;
        this.processName = ' ';
    }

    public int getDestinationPid() {
        return destinationPid;
    }

    public void setDestinationPid(int destinationPid) {
        this.destinationPid = destinationPid;
    }

    public int getLogicalClock() {
        return logicalClock;
    }

    public void setLogicalClock(int logicalClock) {
        this.logicalClock = logicalClock;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
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

    public int getElectionId() {
        return electionId;
    }

    public void setElectionId(int electionId) {
        this.electionId = electionId;
    }

    public char getProcessName() {
        return processName;
    }

    public void setProcessName(char processName) {
        this.processName = processName;
    }

    public int getProcessCapacity() {
        return processCapacity;
    }

    public void setProcessCapacity(int processCapacity) {
        this.processCapacity = processCapacity;
    }
}
