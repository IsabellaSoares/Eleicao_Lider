/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package process;

/**
 *
 * @author Marcelo
 */
public class I {
    public static void main (String[] args){
        try{
            int serverPort = 1208;
            int[] connectionPorts = {1205, 1207, 1209}; // F, H, J
            Process process = new Process(9, 4, serverPort, connectionPorts);
            process.exec();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
