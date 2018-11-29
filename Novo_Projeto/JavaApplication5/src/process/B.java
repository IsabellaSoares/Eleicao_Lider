package process;

public class B {
    public static void main (String[] args){
        try{
            int serverPort = 1201;
            int[] connectionPorts = {1200, 1203}; //A, D
            Process process = new Process(2, 5, serverPort, connectionPorts);
            process.exec();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
