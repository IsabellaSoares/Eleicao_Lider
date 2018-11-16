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
        String json = "m-"+message.getMessageId()+"-"+message.getRequestedResource()+"-"+message.getSenderPid()+"-"+message.getLogicalClock()+"-"+message.getType()+"-"+message.getSenderPort();
        return json;
    }
    
    //Obtém o Id da mensagem e seu clock
    public static Message JsonToMessage(String json){
        String[] str = json.split("-");
        Message message = new Message();       
        message.setMessageId(Integer.parseInt(str[1]));
        message.setRequestedResource(Integer.parseInt(str[2]));
        message.setSenderPid(Integer.parseInt(str[3]));
        message.setLogicalClock(Integer.parseInt(str[4]));
        message.setType(Integer.parseInt(str[5]));
        message.setSenderPort(Integer.parseInt(str[6]));
        return message;
    }
    
    //Cria a mensagem de ACK
    public static String ACKToJson(ACK ack){
        String json = "a-"+ack.getMessageId()+"-"+ack.getSenderPid()+"-"+ack.getDestinationPid()+"-"+ack.getLogicalClock()+"-"+ack.getRequestedResource()+"-"+ack.getType();
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
        return ack;
    }
    
    //Identifica se é uma mensagem de ACK
    public static boolean isACK(String json){
        String[] str = json.split("-");
        if(str!=null && str.length>0 && str[0].equals("a")){
            return true;
        }
        return false;
    }
}
