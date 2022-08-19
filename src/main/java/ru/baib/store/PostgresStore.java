package ru.baib.store;

import org.apache.commons.dbcp2.BasicDataSource;
import ru.baib.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PostgresStore implements Store {

    private final BasicDataSource pool = new BasicDataSource();

    private PostgresStore() {
        Properties cfg = new Properties();
        try {
            cfg.load(PostgresStore.class.getClassLoader().getResourceAsStream("app.properties"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        try {
            Class.forName(cfg.getProperty("driver"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        pool.setDriverClassName(cfg.getProperty("driver"));
        pool.setUrl(cfg.getProperty("url"));
        pool.setUsername(cfg.getProperty("username"));
        pool.setPassword(cfg.getProperty("password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
    }

    private static final class Holder {
        private static final PostgresStore POSTGRES_STORE = new PostgresStore();
    }

    public static PostgresStore instOf() {
        return Holder.POSTGRES_STORE;
    }

    public ServerResponse createTask(ClientMessage clientMessage) {
        ServerResponse response = new ServerResponse();
        Task task = clientMessage.getTask();
        String sql = "INSERT INTO tasks(timetorender, name, created, done, task_status_id, user_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = pool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sql, PreparedStatement.RETURN_GENERATED_KEYS
             )) {
            preparedStatement.setInt(1, task.getTimeToRenderSec());
            preparedStatement.setString(2, task.getName());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(task.getCreated()));
            preparedStatement.setTimestamp(4, Timestamp.valueOf(task.getDone()));
            preparedStatement.setInt(5, task.getTaskStatus().getId());
            preparedStatement.setInt(6, clientMessage.getApplicationState().getCurrentUser().getId());
            preparedStatement.execute();
            try (ResultSet id = preparedStatement.getGeneratedKeys()) {
                if (id.next()) {
                    task.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.setTask(task);
        response.setUser(clientMessage.getApplicationState().getCurrentUser());
        response.setStatus("The task has been successfully entered into the database and started for execution");
        return response;
    }

    @Override
    public ServerResponse showAllTasks(ClientMessage clientMessage) {
        ServerResponse response = new ServerResponse();
        List<Task> taskList = new ArrayList<>();
        String sql = "SELECT * FROM tasks WHERE user_id = ?";
        try (Connection connection = pool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, clientMessage.getApplicationState().getCurrentUser().getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Task task = new Task();
                task.setId(resultSet.getInt("id"));
                task.setTimeToRenderSec(resultSet.getInt("timetorender"));
                task.setName(resultSet.getString("name"));
                task.setTaskStatus(TaskStatus.getById(resultSet.getInt("task_status_id")));
                task.setCreated(resultSet.getTimestamp("created").toLocalDateTime());
                task.setDone(resultSet.getTimestamp("done").toLocalDateTime());
                taskList.add(task);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.setTaskList(taskList);
        return response;
    }

    @Override
    public ServerResponse login(ClientMessage clientMessage) {
        ServerResponse response = new ServerResponse();
        User toLog = clientMessage.getApplicationState().getLogRegUser();
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection connection = pool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, toLog.getUsername().toLowerCase());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String password = resultSet.getString("password");
                if (password.equals(toLog.getPassword())) {
                    User user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setUsername(resultSet.getString("username"));
                    response.setStatus("Successfully logged in");
                    response.setUser(user);
                } else {
                    response.setStatus("Password for this user is incorrect");
                }
            } else {
                response.setStatus("Username or password is incorrect");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public ServerResponse register(ClientMessage clientMessage) {
        ServerResponse response = new ServerResponse();
        User toReg = clientMessage.getApplicationState().getLogRegUser();
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (Connection connection = pool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, toReg.getUsername().toLowerCase());
            preparedStatement.setString(2, toReg.getPassword());
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.setStatus("User successfully added");
        return response;
    }

    @Override
    public boolean isExist(String username) {
        boolean isExist = false;
        String sql = "SELECT * FROM users where username = ?";
        try (Connection connection = pool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("username");
                if (name.equals(username)) {
                    isExist = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isExist;
    }

}
