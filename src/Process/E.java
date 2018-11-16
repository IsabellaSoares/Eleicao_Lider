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
public class E {
    public static void main(String[] args) {
        int serverPort = 1204;
        int[] connectionPorts = {1203, 1205};
        Process p = new Process('E', 3, 5, serverPort, connectionPorts);
        p.exec();
        //exec(pid do processo, porta do servidor, vetor de conexões)
    }
}
