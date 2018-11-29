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
public class E {
    public static void main (String[] args){
        try{
            int serverPort = 1204;
            int[] connectionPorts = {1203, 1205};
            Process process = new Process(5, 3, serverPort, connectionPorts);
            process.exec();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
