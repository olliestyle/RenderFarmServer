package ru.baib.model;

import java.io.Serializable;

public class ApplicationState implements Serializable {

    private static final long serialVersionUID = 1127946645687925112L;
    private User currentUser;
    private User logRegUser;

    public ApplicationState() {

    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public User getLogRegUser() {
        return logRegUser;
    }

    public void setLogRegUser(User logRegUser) {
        this.logRegUser = logRegUser;
    }
}
