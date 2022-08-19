package ru.baib;

import ru.baib.model.ClientMessage;
import ru.baib.model.ServerResponse;
import ru.baib.model.Task;
import ru.baib.util.MessageHandler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread {

    private ServerSocket serverSocket;
    private MessageHandler messageHandler;

    public ServerThread(ServerSocket serverSocket, MessageHandler messageHandler) {
        this.serverSocket = serverSocket;
        this.messageHandler = messageHandler;
    }

    @Override
    public void run() {
        try {
            Socket socket = serverSocket.accept();
            while (true) {
                try {
                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    ClientMessage clientMessage = (ClientMessage) ois.readObject();
                    ServerResponse serverResponse = messageHandler.handleMessage(clientMessage);
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(serverResponse);
                    oos.flush();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
