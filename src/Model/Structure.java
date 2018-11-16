/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marcelo
 */
public class Structure {
    private Integer messageId = null; //Id da mensagem e do ACK
    private Integer logicalClock = null; //Clock da mensagem e do ACK
    private Integer requestedResource = null;
    private Message message; //Mensagem
    private List<ACK> ackList; //Lista de ACKs da mensagem
    
    public Structure(){
        this.ackList = new ArrayList<>();
    }

    public Message getMessage() {
        return message;
    }
    
    public void setMessage(Message message) {
        this.message = message;
        if(this.messageId==null){
            this.messageId = message.getMessageId();
        }
        if(this.logicalClock==null){
            this.logicalClock = message.getLogicalClock();
        }
        if(this.requestedResource==null){
            this.requestedResource = message.getRequestedResource();
        }
    }
    
    public void addACK(ACK ack){
        this.ackList.add(ack);
        if(this.messageId==null){
            this.messageId = ack.getMessageId();
        }
        if(this.logicalClock==null){
            this.logicalClock = ack.getLogicalClock();
        }
        if(this.requestedResource==null){
            this.requestedResource = ack.getRequestedResource();
        }
    }
    
    public int getNumbersOfACK(){
        return this.ackList.size();
    }

    public Integer getMessageId() {
        return messageId;
    }

    public Integer getLogicalClock() {
        return logicalClock;
    }

    public Integer getRequestedResource() {
        return requestedResource;
    }
}
