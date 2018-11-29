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
public class G {
    public static void main (String[] args){
        try{
            int serverPort = 1206;
            int[] connectionPorts = {1202, 1207, 1209}; // C, H, J
            Process process = new Process(7, 2, serverPort, connectionPorts);
            process.exec();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
