/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manager;

import Listener.MessageManagerListener;
import Model.ACK;
import Model.Message;
import Model.Structure;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Isabella
 */
public class MessageManager {
    
    private int pid;
    private MessageManagerListener listener;
    private List<Structure> messageList; //Lista de mensagens
    
    public MessageManager(int pid){
        this.pid = pid;
        this.messageList = new ArrayList<Structure>();
    }

    public void setListener(MessageManagerListener listener) {
        this.listener = listener;
    }
    
    public synchronized void addMessage(Message message){
        boolean addedToList = false;
        
        for(int i=0; i<messageList.size() && !addedToList; i++){
            Structure currentStructure = messageList.get(i);
            if(message.getMessageId()==currentStructure.getMessageId()){
                // atualiza o item
                currentStructure.setMessage(message);
                addedToList = true;
            } else if (message.getMessageId()<currentStructure.getMessageId()){
                // cria um novo item no meio
                Structure newStructure = new Structure();
                newStructure.setMessage(message);
                messageList.add(i, newStructure);
                addedToList = true;
            }
        }
        
        if(!addedToList){
            // cria um novo item no final
            Structure e = new Structure();
            e.setMessage(message);
            messageList.add(e);
        }
    }
    
    public synchronized void addAck(ACK ack){
        boolean addedToList = false;
        for(int i=0; i<messageList.size() && !addedToList; i++){
            Structure currentStructure = messageList.get(i);
            if(ack.getMessageId()==currentStructure.getMessageId()){
                // atualiza o item
                currentStructure.addACK(ack);
                addedToList = true;
            } else if (ack.getMessageId()<currentStructure.getMessageId()){
                // cria um novo item no meio
                Structure newStructure = new Structure();
                newStructure.addACK(ack);
                messageList.add(i, newStructure);
                addedToList = true;
            }
        }
        
        if(!addedToList){
            // cria um novo item no final
            Structure e = new Structure();
            e.addACK(ack);
            messageList.add(e);
        }
        
        updateList();
    }
    
    private synchronized void updateList(){
        try{
            boolean removed = true;
            for(int i=0; i<messageList.size() && removed; i++){
                Structure currentStructure = messageList.get(i);
                if(currentStructure.getMessage()!=null && currentStructure.getNumbersOfACK()==2){
                    notifyProcess(currentStructure);
                    messageList.remove(i--);
                } else {
                    removed = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Deu erro updateList()!");
        }
    }
    
    public synchronized void notifyProcess(Structure structure){
        if(listener!=null){
            listener.notifyProcess(structure);
        }
    }
    
    public synchronized void printList(){
        for(int i=0; i<messageList.size(); i++){
            Structure structure = messageList.get(i);
            System.out.println("["+i+"] messageId: "+structure.getMessageId()+ " - recurso: " + structure.getRequestedResource());
        }
    }
    
    public boolean isEmpty(){
        return messageList.isEmpty();
    }

    public List<Structure> getMessageList() {
        return messageList;
    }
    
    public Message getMessage(){
        if(!isEmpty()){
            if(messageList.get(0).getMessage()!=null){
                return messageList.get(0).getMessage();
            }
        }
        
        return null;
    }
    
    public Message getMessage(int position){
        if(!isEmpty()){
            if(messageList.size()>position){
                if(messageList.get(position).getMessage()!=null){
                    return messageList.get(position).getMessage();
                }
            }
        }
        
        return null;
    }
    
    public boolean isRequestingFor(int resourceId){
        for(Structure structure : messageList){
            if(structure.getRequestedResource()==resourceId){
                return true;
            }
        }
        return false;
    }
}
