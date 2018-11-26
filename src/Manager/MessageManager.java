/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manager;

import Listener.MessageManagerListener;
import Model.ACK;
import Model.Message;
import Model.Requisition;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Isabella
 */
public class MessageManager {
    
    private int pid;
    private MessageManagerListener listener;
    private List<Requisition> requisitionList; //Lista de mensagens
    
    public MessageManager(int pid){
        this.pid = pid;
        this.requisitionList = new ArrayList<Requisition>();
    }

    public void setListener(MessageManagerListener listener) {
        this.listener = listener;
    }
    
    public synchronized void addMessage(Message message, int sended){
        boolean addedToList = false;
        
        for(int i=0; i<requisitionList.size() && !addedToList; i++){
            Requisition currentStructure = requisitionList.get(i);
            if(message.getMessageId()==currentStructure.getMessageId()){
                // atualiza o item
                currentStructure.setMessage(message);
                currentStructure.setSizeOfWaitedAnswer(sended);
                addedToList = true;
            } else if (message.getMessageId()<currentStructure.getMessageId()){
                // cria um novo item no meio
                Requisition newStructure = new Requisition();
                newStructure.setMessage(message);
                newStructure.setSizeOfWaitedAnswer(sended);
                requisitionList.add(i, newStructure);
                addedToList = true;
            }
        }
        
        if(!addedToList){
            // cria um novo item no final
            Requisition e = new Requisition();
            e.setMessage(message);
            e.setSizeOfWaitedAnswer(sended);
            requisitionList.add(e);
        }
        
        updateList();
    }
    
    public synchronized void addAnswer(Message message){
        boolean addedToList = false;
        for(int i=0; i<requisitionList.size() && !addedToList; i++){
            Requisition currentStructure = requisitionList.get(i);
            if(message.getMessageId()==currentStructure.getMessageId()){
                // atualiza o item
                currentStructure.addAnswer(message);
                addedToList = true;
            } else if (message.getMessageId()<currentStructure.getMessageId()){
                // cria um novo item no meio
                Requisition newStructure = new Requisition();
                newStructure.addAnswer(message);
                requisitionList.add(i, newStructure);
                addedToList = true;
            }
        }
        
        if(!addedToList){
            // cria um novo item no final
            Requisition e = new Requisition();
            e.addAnswer(message);
            requisitionList.add(e);
        }
        
        updateList();
    }
    
    private synchronized void updateList(){
        try{
            boolean removed = true;
            for(int i=0; i<requisitionList.size() && removed; i++){
                Requisition currentStructure = requisitionList.get(i);
                if(currentStructure.getMessage()!=null && currentStructure.getNumberOfReceivedAnswers()==currentStructure.getSizeOfWaitedAnswer()){
                    notifyProcess(currentStructure);
                    requisitionList.remove(i--);
                } else {
                    removed = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Deu erro updateList()!");
        }
    }
    
    public synchronized void notifyProcess(Requisition structure){
        if(listener!=null){
            listener.notifyProcess(structure);
        }
    }
    
    public synchronized void printList(){
        for(int i=0; i<requisitionList.size(); i++){
            Requisition structure = requisitionList.get(i);
            System.out.println("["+i+"] messageId: "+structure.getMessageId()+ " - recurso: " + structure.getRequestedResource());
        }
    }
    
    public boolean isEmpty(){
        return requisitionList.isEmpty();
    }

    public List<Requisition> getRequisitionList() {
        return requisitionList;
    }
    
    public Message getMessage(){
        if(!isEmpty()){
            if(requisitionList.get(0).getMessage()!=null){
                return requisitionList.get(0).getMessage();
            }
        }
        
        return null;
    }
    
    public Message getMessage(int position){
        if(!isEmpty()){
            if(requisitionList.size()>position){
                if(requisitionList.get(position).getMessage()!=null){
                    return requisitionList.get(position).getMessage();
                }
            }
        }
        
        return null;
    }
    
    public boolean isRequestingFor(int resourceId){
        for(Requisition structure : requisitionList){
            if(structure.getRequestedResource()==resourceId){
                return true;
            }
        }
        return false;
    }
    
    public boolean existRequisitionFor(Message message){
        for(Requisition structure : requisitionList){
            if(structure.getElectionId()==message.getElectionId()){
                return true;
            }
        }
        return false;
    }
}
