/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Listener;

import Model.ACK;
import Model.Message;

/**
 *
 * @author Marcelo
 */
public interface ServerManagerListener {
    void messageReceived(Message message);
    void ACKReceived(ACK ack);
}
