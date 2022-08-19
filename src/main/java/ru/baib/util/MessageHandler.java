package ru.baib.util;

import ru.baib.model.ClientMessage;
import ru.baib.model.ServerResponse;
import ru.baib.service.ResponseService;

public class MessageHandler {

    private final ResponseService responseService;

    public MessageHandler(ResponseService responseService) {
        this.responseService = responseService;
    }

    public ServerResponse handleMessage(ClientMessage clientMessage) {
        ServerResponse response;
        String action = clientMessage.getAction();
        switch (action) {
            case "ru.baib.action.CreateAction":
                response = responseService.createTask(clientMessage);
            break;
            case "ru.baib.action.ShowAllAction":
                response = responseService.showAllTasks(clientMessage);
            break;
            case "ru.baib.action.LoginAction":
                response = responseService.login(clientMessage);
            break;
            case "ru.baib.action.RegisterAction":
                response = responseService.register(clientMessage);
            break;
            default:
                response = new ServerResponse();
                response.setStatus("Undefined action");
        }
        return response;
    }

}
