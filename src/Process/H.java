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
public class H {
    public static void main(String[] args) {
        int serverPort = 1207;
        int[] connectionPorts = {1206, 1208}; // G, I
        Process p = new Process('H', 6, 8, serverPort, connectionPorts);
        p.exec();
        //exec(pid do processo, porta do servidor, vetor de conexões)
    }
}
