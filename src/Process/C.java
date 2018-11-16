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
public class C {
    public static void main(String[] args) {
        int serverPort = 1202;
        int[] connectionPorts = {1200, 1203, 1205, 1206}; //A, D, F, G
        Process p = new Process('C', 7, 3, serverPort, connectionPorts);
        p.exec();
        //exec(pid do processo, porta do servidor, vetor de conexões)
    }
}
