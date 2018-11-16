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
public class J {
    public static void main(String[] args) {
        int serverPort = 1209;
        int[] connectionPorts = {1206, 1208}; // G, I
        Process p = new Process('J', 9, 10, serverPort, connectionPorts);
        p.exec();
        //exec(pid do processo, porta do servidor, vetor de conexões)
    }
}
