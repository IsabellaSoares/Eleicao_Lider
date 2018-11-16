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
    
//    public final static int REQUEST_RESOURCE = 0;
//    public final static int FREE_RESOURCE = 1;
    
    private int messageId; //Id da mensagem (<logicalClock><senderPid>)
    private int requestedResource; //Recurso solicitado
    private int senderPid; //Pid do processo que enviou a mensagem
    private int logicalClock; //Refere-se ao clock da mensagem
    private int type; // 0: requisitar um recurso; 1: liberar um recurso; 2: iniciar conexões
    private int senderPort;

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
}
