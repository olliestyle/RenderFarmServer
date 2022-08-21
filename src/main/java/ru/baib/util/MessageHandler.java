package ru.baib.util;

import ru.baib.model.ClientMessage;
import ru.baib.model.ServerResponse;
import ru.baib.service.ResponseService;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class MessageHandler {

    private final ResponseService responseService;
    private final Map<String, Function<ClientMessage, ServerResponse>> functionMap = new HashMap<>();

    public MessageHandler(ResponseService responseService) {
        this.responseService = responseService;
        functionMap.put("ru.baib.action.CreateAction", responseService::createTask);
        functionMap.put("ru.baib.action.ShowAllAction", responseService::showAllTasks);
        functionMap.put("ru.baib.action.LoginAction", responseService::login);
        functionMap.put("ru.baib.action.RegisterAction", responseService::register);
    }

    public ServerResponse handleMessage(ClientMessage clientMessage) {
        ServerResponse response = new ServerResponse();
        response.setStatus("Undefined action");
        String action = clientMessage.getAction();
        response = functionMap.get(action).apply(clientMessage);
        return response;
    }

}
