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
public class D {
    public static void main(String[] args) {
        int serverPort = 1203;
        int[] connectionPorts = {1201, 1202, 1204}; // B, C, E
        Process p = new Process('D', 8, 4, serverPort, connectionPorts);
        p.exec();
        //exec(pid do processo, porta do servidor, vetor de conexões)
    }
}
