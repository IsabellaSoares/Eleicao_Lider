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
public class H {
    public static void main (String[] args){
        try{
            int serverPort = 1207;
            int[] connectionPorts = {1206, 1208}; // G, I
            Process process = new Process(8, 6, serverPort, connectionPorts);
            process.exec();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
