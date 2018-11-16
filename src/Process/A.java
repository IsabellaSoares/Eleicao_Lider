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
public class A {

    public static void main(String[] args) {
        int serverPort = 1200;
        int[] connectionPorts = {1201, 1202}; //B, C
        Process p = new Process('A', 1, 1, serverPort, connectionPorts);
        p.exec();
    }
}
