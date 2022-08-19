package ru.baib;

import ru.baib.service.ResponseService;
import ru.baib.store.PostgresStore;
import ru.baib.util.MessageHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class StartServer {

    public static void main(String[] args) throws IOException {
        int cores = Runtime.getRuntime().availableProcessors();
        ResponseService responseService = new ResponseService(PostgresStore.instOf());
        MessageHandler messageHandler = new MessageHandler(responseService);
        List<ServerThread> list = new ArrayList<>();
        ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[0]));
        for (int i = 0; i < cores; i++) {
            list.add(new ServerThread(serverSocket, messageHandler));
        }
        for (Thread thread: list) {
            thread.start();
        }
        System.out.println("Server started and can handle " + cores + " clients at the same time");
    }

}


