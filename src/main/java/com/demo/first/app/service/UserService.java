package com.demo.first.app.service;

import com.demo.first.app.controller.UserController;
import com.demo.first.app.exceptions.UserNotFoundException;
import com.demo.first.app.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    private Map<Integer, User> userDb = new HashMap<>();
    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    public User createUser(User user) {
        logger.info("Creating user   info");
        logger.debug("Creating user   debug");
        logger.trace("Creating user   trace");
        logger.error("Creating user   error");
        logger.warn("Creating user   warn");
        userDb.putIfAbsent(user.getId(), user);
        return user;
    }

    public User updateUser(User user) {
        if(!userDb.containsKey(user.getId())) {
            logger.error("Error when finding user id "+ user.getId());
            throw new UserNotFoundException("User with id does not exist : "+user.getId());
        }else{
            userDb.put(user.getId(), user);
            return user;
        }
    }

    public Boolean deleteUser(int id) {
        if(!userDb.containsKey(id)) {
            return false;
        }else {
            userDb.remove(id);
            return true;
        }
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(userDb.values());
    }

    public User getUser(int id, int orderId) {
        if(userDb.containsKey(id)) {
            return userDb.get(id);
        }
        return null;
    }

    public List<User> searchUser(String name, String email) {
        System.out.println(name +" "+email);

        return userDb.values().stream()
                .filter(n -> n.getName().equalsIgnoreCase(name))
                .filter(n -> n.getEmail().equalsIgnoreCase(email))
                .toList();
    }

    public User getUserById(int id) {
        if(userDb.containsKey(id))
            return userDb.get(id);
        return null;
    }
}
