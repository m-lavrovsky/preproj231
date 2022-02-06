package web.dao;

import web.model.User;

import java.util.List;

public interface UserDAO {
    void addUser(String name, String lastName, int age);

    void addUser(User user);

    void removeUser(User user);

    void editUser(User user);

    List<User> getAllUsers();

    User getById(Long id);

    boolean hasId (Long id);
}