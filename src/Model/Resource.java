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
 * @author Isabella
 */
public class Resource {
    private int resourceId; //Identificador do recurso
    private List<Message> messageList;
    private boolean reserved;
    
    public Resource () {
        this.messageList = new ArrayList<>();
        this.reserved = false;
    }
    
    public Resource (int resourceId) {
        this.messageList = new ArrayList<>();
        this.reserved = false;
        this.resourceId = resourceId;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    public boolean isReserved() {
        return reserved;
    }
    
    public void putMessage (Message m) {
        messageList.add(m);
    }
    
    public void removeMessage () {
        messageList.remove(0);
    }

    public List<Message> getMessageList() {
        return messageList;
    }
}
