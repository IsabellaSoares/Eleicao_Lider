/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;

import java.util.ArrayList;
import java.util.List;
import listener.ElectionManagerListener;
import model.Message;

/**
 *
 * @author Marcelo
 */
public class ElectionManager {
    private Message requisition;
    private List<Message> answers;
    private int expectedAnswers; //Quant de filhos do nó
    private ElectionManagerListener listener;
    private boolean flag;
    
    public ElectionManager(){
        answers = new ArrayList<>();
    }

    public void setListener(ElectionManagerListener listener) {
        this.listener = listener;
    }

    public void setRequisition(Message requisition, int expectedAnswers) {
        this.requisition = requisition;
        this.expectedAnswers = expectedAnswers;
        answers.clear();
        flag = true;
    }
    
    public void addAnswer(Message message){
        answers.add(message);
        if(answers.size()==expectedAnswers){
            if(listener!=null){
                listener.notifyProcess();
                flag = false;
            }
        }
    }
    
    public Integer getElectionId(){
        if(requisition!=null){
            return requisition.getElectionId();
        }
        return null;
    }

    public Message getRequisition() {
        return requisition;
    }

    public List<Message> getAnswers() {
        return answers;
    }
    
    public boolean isRequesting(){
        return flag;
    }
}
