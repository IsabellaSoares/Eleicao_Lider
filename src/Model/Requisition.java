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
public class Requisition {
    private Integer messageId = null; //Id da mensagem e do ACK
    private Integer logicalClock = null; //Clock da mensagem e do ACK
    private Integer requestedResource = null;
    private Integer electionId = null;
    private Message message; //Mensagem
    private List<Message> answerList; //Lista de ACKs da mensagem
    private int sizeOfWaitedAnswer;
    
    public Requisition(){
        this.answerList = new ArrayList<>();
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
        if(this.electionId==null){
            this.electionId = message.getElectionId();
        }
    }
    
    public void addAnswer(Message message){
        this.answerList.add(message);
        if(this.messageId==null){
            this.messageId = message.getMessageId();
        }
        if(this.logicalClock==null){
            this.logicalClock = message.getLogicalClock();
        }
        if(this.requestedResource==null){
            this.requestedResource = message.getRequestedResource();
        }
        if(this.electionId==null){
            this.electionId = message.getElectionId();
        }
    }
    
    public int getNumberOfReceivedAnswers(){
        return this.answerList.size();
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

    public Integer getElectionId() {
        return electionId;
    }

    public void setElectionId(Integer electionId) {
        this.electionId = electionId;
    }

    public int getSizeOfWaitedAnswer() {
        return sizeOfWaitedAnswer;
    }

    public void setSizeOfWaitedAnswer(int sizeOfWaitedAnswer) {
        this.sizeOfWaitedAnswer = sizeOfWaitedAnswer;
    }

    public List<Message> getAnswerList() {
        return answerList;
    }
}
