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
public class G {
    public static void main(String[] args) {
        int serverPort = 1206;
        int[] connectionPorts = {1202, 1207, 1209}; // C, H, J
        Process p = new Process('G', 2, 7, serverPort, connectionPorts);
        p.exec();
        //exec(pid do processo, porta do servidor, vetor de conexões)
    }
}
