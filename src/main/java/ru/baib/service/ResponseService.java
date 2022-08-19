package ru.baib.service;

import ru.baib.model.*;
import ru.baib.store.Store;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

public class ResponseService {

    private final Store store;

    public ResponseService(Store store) {
        this.store = store;
    }

    public ServerResponse createTask(ClientMessage clientMessage) {
        ServerResponse response;
        Task task = clientMessage.getTask();
        if (clientMessage.getApplicationState().getCurrentUser() != null) {
            task.setCreated(LocalDateTime.now());
            task.setTimeToRenderSec(new Random().nextInt(240) + 60);
            task.setDone(task.getCreated().plusSeconds(task.getTimeToRenderSec()));
            task.setTaskStatus(TaskStatus.RENDERING);
            response = store.createTask(clientMessage);
            //TODO run to render
        } else {
            response = new ServerResponse();
            response.setStatus("First you must log in");
        }
        return response;
    }

    public ServerResponse showAllTasks(ClientMessage clientMessage) {
        ServerResponse response;
        if (clientMessage.getApplicationState().getCurrentUser() != null) {
            response = store.showAllTasks(clientMessage);
        } else {
            response = new ServerResponse();
            response.setStatus("First you must log in");
            response.setTaskList(new ArrayList<>());
        }
        return response;
    }

    public ServerResponse login(ClientMessage clientMessage) {
        return store.login(clientMessage);
    }

    public ServerResponse register(ClientMessage clientMessage) {
        ServerResponse response;
        if (!isUserExist(clientMessage.getApplicationState().getLogRegUser().getUsername())) {
            response = store.register(clientMessage);
        } else {
            response = new ServerResponse();
            response.setStatus("User with this username already exist. Please try again with a different name");
        }
        return response;
    }

    private boolean isUserExist(String username) {
        return store.isExist(username.toLowerCase());
    }

}
