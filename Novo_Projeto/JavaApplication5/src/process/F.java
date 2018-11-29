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
public class F {
    public static void main (String[] args){
        try{
            int serverPort = 1205;
            int[] connectionPorts = {1204, 1202, 1208}; // E, C, I
            Process process = new Process(6, 10, serverPort, connectionPorts);
            process.exec();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
