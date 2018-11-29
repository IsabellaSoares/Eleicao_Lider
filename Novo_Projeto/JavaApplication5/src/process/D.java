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
public class D {
    public static void main (String[] args){
        try{
            int serverPort = 1203;
            int[] connectionPorts = {1201, 1202, 1204}; // B, C, E
            Process process = new Process(4, 8, serverPort, connectionPorts);
            process.exec();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
