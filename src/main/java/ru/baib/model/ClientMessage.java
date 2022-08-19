package ru.baib.model;

import java.io.Serializable;

public class ClientMessage implements Serializable {

    private static final long serialVersionUID = 3327946645687925112L;
    private String action;
    private ApplicationState applicationState;
    private Task task;

    public ClientMessage(String action, ApplicationState applicationState) {
        this.action = action;
        this.applicationState = applicationState;
    }

    public ClientMessage(String action, ApplicationState applicationState, Task task) {
        this.action = action;
        this.applicationState = applicationState;
        this.task = task;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public ApplicationState getApplicationState() {
        return applicationState;
    }

    public void setApplicationState(ApplicationState applicationState) {
        this.applicationState = applicationState;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @Override
    public String toString() {
        return "ClientMessage{" + "action='" + action + '\'' + ", applicationState=" + applicationState
                + ", task=" + task + '}';
    }
}
