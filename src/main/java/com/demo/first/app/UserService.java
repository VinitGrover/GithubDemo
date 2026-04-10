package com.demo.first.app;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    private Map<Integer, User> userDb = new HashMap<>();

    public User createUser(User user) {
        System.out.println("User created");
        userDb.putIfAbsent(user.getId(), user);
        return user;
    }

    public User updateUser(User user) {
        if(!userDb.containsKey(user.getId())) {
            throw new IllegalArgumentException("User with id does not exist : "+user.getId());
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
