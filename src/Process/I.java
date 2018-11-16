/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Process;

/**
 *
 * @author Marcelo
 */
public class I {
    public static void main(String[] args) {
        int serverPort = 1208;
        int[] connectionPorts = {1205, 1207, 1209}; // F, H, J
        Process p = new Process('I', 4, 9, serverPort, connectionPorts);
        p.exec();
    }
}
