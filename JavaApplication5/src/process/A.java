package process;

import connectionfactory.Server;

public class A {
    public static void main (String[] args){
        try{
            int serverPort = 1200;
        int[] connectionPorts = {1201, 1202}; //B, C
            Process process = new Process(1, 1, serverPort, connectionPorts);
            process.exec();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
