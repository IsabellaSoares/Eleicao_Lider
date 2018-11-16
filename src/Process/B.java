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
public class B {
    public static void main(String[] args) {
        int serverPort = 1201;
        int[] connectionPorts = {1200, 1203}; //A, D
        Process p = new Process('B', 5, 2, serverPort, connectionPorts);
        p.exec();
        //exec(pid do processo, porta do servidor, vetor de conexões)
    }
}
