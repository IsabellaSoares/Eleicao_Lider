/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools;

import Model.ACK;
import Model.Message;

/**
 *
 * @author Marcelo
 */
public class Convert {
    
    public static String MessageToJson(Message message){
        String json = message.getMessageId()
                +"-"+message.getRequestedResource()
                +"-"+message.getSenderPid()
                +"-"+message.getLogicalClock()
                +"-"+message.getType()
                +"-"+message.getSenderPort()
                +"-"+message.getElectionId()
                +"-"+message.getElectedNode()
                +"-"+message.getElectedNodeCapacity();
        return json;
    }
    
    //Obtém o Id da mensagem e seu clock
    public static Message JsonToMessage(String json){
        String[] str = json.split("-");
        Message message = new Message();       
        message.setMessageId(Integer.parseInt(str[0]));
        message.setRequestedResource(Integer.parseInt(str[1]));
        message.setSenderPid(Integer.parseInt(str[2]));
        message.setLogicalClock(Integer.parseInt(str[3]));
        message.setType(Integer.parseInt(str[4]));
        message.setSenderPort(Integer.parseInt(str[5]));
        message.setElectionId(Integer.parseInt(str[6]));
        message.setElectedNode(Integer.parseInt(str[7]));
        message.setElectedNodeCapacity(Integer.parseInt(str[8]));
        return message;
    }
    
    //Cria a mensagem de ACK
    public static String ACKToJson(ACK ack){
        String json = "a-"+ack.getMessageId()
                +"-"+ack.getSenderPid()
                +"-"+ack.getDestinationPid()
                +"-"+ack.getLogicalClock()
                +"-"+ack.getRequestedResource()
                +"-"+ack.getType()
                +"-"+ack.getElectionId()
                +"-"+ack.getProcessName()
                +"-"+ack.getProcessCapacity();
        return json;
    }
    
    //Obtém Id, clock e processo que enviou o ACK
    public static ACK JsonToACK(String json){
        String[] str = json.split("-");
        ACK ack = new ACK();
        ack.setMessageId(Integer.parseInt(str[1]));
        ack.setSenderPid(Integer.parseInt(str[2]));
        ack.setDestinationPid(Integer.parseInt(str[3]));
        ack.setLogicalClock(Integer.parseInt(str[4]));
        ack.setRequestedResource(Integer.parseInt(str[5]));
        ack.setType(Integer.parseInt(str[6]));
        ack.setElectionId(Integer.parseInt(str[7]));
        String name = str[8];
        ack.setProcessName(name.charAt(0));
        ack.setProcessCapacity(Integer.parseInt(str[9]));
        return ack;
    }
    
    //Identifica se é uma mensagem de ACK
    public static boolean isACK(String json){
//        String[] str = json.split("-");
//        if(str!=null && str.length>0 && str[0].equals("a")){
//            return true;
//        }
//        return false;
        
        
//        if(json!=null && json.length()>0){
//            char c = json.charAt(0);
//            if(c=='a'){
//                return true;
//            }
//        }
//        return false;
        
        return false;
    }
}
