package model;

public class Message {
    public final static int REQUEST_ELECTION = 0;
    public final static int START_CONNECTIONS = 1;
    public final static int ACK = 2;
    public final static int NACK = 3;
    public final static int RESULT_MESSAGE = 4;
    
    private int messageId; //Id da mensagem (<logicalClock><senderPid>)
    private int senderPid; //Pid do processo que enviou a mensagem
    private int logicalClock; //Refere-se ao clock da mensagem
    private int type; // 0: requisitar um recurso; 1: liberar um recurso; 2: iniciar conexões
    private int senderPort;
    private int electionId;
    private int electedNode;
    private int electedNodeCapacity;
    private int destinationPid; //Processo que pediu o recurso

    public Message(){}
    
    public Message(int type, int senderPid, int logicalClock, int senderPort){
        this.type = type;
        this.senderPid = senderPid;
        this.logicalClock = logicalClock;
        this.messageId = (logicalClock*10)+senderPid;
        this.senderPort = senderPort;
    }
    
    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getSenderPid() {
        return senderPid;
    }

    public void setSenderPid(int senderPid) {
        this.senderPid = senderPid;
    }

    public int getLogicalClock() {
        return logicalClock;
    }

    public void setLogicalClock(int logicalClock) {
        this.logicalClock = logicalClock;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSenderPort() {
        return senderPort;
    }

    public void setSenderPort(int senderPort) {
        this.senderPort = senderPort;
    }

    public int getElectionId() {
        return electionId;
    }

    public void setElectionId(int electionId) {
        this.electionId = electionId;
    }

    public int getElectedNode() {
        return electedNode;
    }

    public void setElectedNode(int electedNode) {
        this.electedNode = electedNode;
    }

    public int getElectedNodeCapacity() {
        return electedNodeCapacity;
    }

    public void setElectedNodeCapacity(int electedNodeCapacity) {
        this.electedNodeCapacity = electedNodeCapacity;
    }

    public int getDestinationPid() {
        return destinationPid;
    }

    public void setDestinationPid(int destinationPid) {
        this.destinationPid = destinationPid;
    }

    @Override
    public String toString() {
        String json = getMessageId()
                +"-"+getSenderPid()
                +"-"+getLogicalClock()
                +"-"+getType()
                +"-"+getSenderPort()
                +"-"+getElectionId()
                +"-"+getElectedNode()
                +"-"+getElectedNodeCapacity()
                +"-";
        return json;
    }
    
    public void fromString(String string){
        String[] str = string.split("-");
        setMessageId(Integer.parseInt(str[0]));
        setSenderPid(Integer.parseInt(str[1]));
        setLogicalClock(Integer.parseInt(str[2]));
        setType(Integer.parseInt(str[3]));
        setSenderPort(Integer.parseInt(str[4]));
        setElectionId(Integer.parseInt(str[5]));
        setElectedNode(Integer.parseInt(str[6]));
        setElectedNodeCapacity(Integer.parseInt(str[7]));
    }
}
