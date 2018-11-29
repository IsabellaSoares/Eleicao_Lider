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
public class J {
    public static void main (String[] args){
        try{
            int serverPort = 1209;
            int[] connectionPorts = {1206, 1208}; // G, I
            Process process = new Process(10, 4, serverPort, connectionPorts);
            process.exec();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
