package listener;

import model.Message;

public interface ServerListener {
    void messageReceived(Message message);
}
