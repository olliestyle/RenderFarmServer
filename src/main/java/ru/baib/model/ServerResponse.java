package ru.baib.model;

import java.io.Serializable;
import java.util.List;

public class ServerResponse implements Serializable {

    private static final long serialVersionUID = 2127946645687925112L;
    private String status;
    private Task task;
    private List<Task> taskList;
    private User user;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
