package ru.baib.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task implements Serializable {

    private static final long serialVersionUID = 2227946645687925112L;
    private int id;
    private int timeToRenderSec;
    private String name;
    private TaskStatus taskStatus;
    private LocalDateTime created;
    private LocalDateTime done;

    public Task() {

    }

    public Task(String name) {
        this.name = name;
    }

    public Task(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public int getTimeToRenderSec() {
        return timeToRenderSec;
    }

    public void setTimeToRenderSec(int timeToRenderSec) {
        this.timeToRenderSec = timeToRenderSec;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getDone() {
        return done;
    }

    public void setDone(LocalDateTime done) {
        this.done = done;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Task task = (Task) o;
        return id == task.id && Objects.equals(name, task.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Task{" + "id=" + id + ", timeToRenderSec=" + timeToRenderSec + ", name='" + name + '\''
                + ", taskStatus=" + taskStatus + ", created=" + created + ", done=" + done + '}';
    }

}
