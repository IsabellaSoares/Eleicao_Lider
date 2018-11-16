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
public class F {
    public static void main(String[] args) {
        int serverPort = 1205;
        int[] connectionPorts = {1204, 1202, 1208}; // E, C, I
        Process p = new Process('F', 10, 6, serverPort, connectionPorts);
        p.exec();
        //exec(pid do processo, porta do servidor, vetor de conexões)
    }
}
