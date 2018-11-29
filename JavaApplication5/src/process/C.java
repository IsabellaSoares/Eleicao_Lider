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
public class C {
    public static void main (String[] args){
        try{
            int serverPort = 1202;
            int[] connectionPorts = {1200, 1203, 1205, 1206}; //A, D, F, G
            Process process = new Process(3, 7, serverPort, connectionPorts);
            process.exec();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
