package ru.baib.store;

import ru.baib.model.ClientMessage;
import ru.baib.model.ServerResponse;

public interface Store {

    ServerResponse createTask(ClientMessage clientMessage);
    ServerResponse showAllTasks(ClientMessage clientMessage);
    ServerResponse login(ClientMessage clientMessage);
    ServerResponse register(ClientMessage clientMessage);
    boolean isExist(String username);
}
