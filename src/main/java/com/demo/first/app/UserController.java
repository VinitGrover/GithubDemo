package com.demo.first.app;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    private Map<Integer, User> userDb = new HashMap<>();

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        userDb.putIfAbsent(user.getId(), user);
        System.out.println("yipee user");
//        return ResponseEntity.status(HttpStatus.CREATED).body("User is created");
        return new ResponseEntity<>(user,HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user){
        if(!userDb.containsKey(user.getId())) {
//            return ResponseEntity.notFound().build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else{
            userDb.put(user.getId(), user);
//            return ResponseEntity.status(HttpStatus.ACCEPTED).body(user);
            return ResponseEntity.ok(user);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id){
        if(!userDb.containsKey(id)) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User does not exist");
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else {
            userDb.remove(id);
            return ResponseEntity.ok("successfully removed");
        }
    }

    @GetMapping
    public List<User> getUsers(){
        return new ArrayList<>(userDb.values());
    }

    @GetMapping("/{userid}")
    public ResponseEntity<User> getUsers(@PathVariable(value = "userid", required = false) int id){
        if(!userDb.containsKey(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(userDb.get(id));
    }

    @GetMapping("/{userid}/orders/{orderId}")
    public ResponseEntity<User> getUserOrder(@PathVariable("userid") int id,
                                             @PathVariable int orderId){
        if(!userDb.containsKey(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(userDb.get(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsers(
            @RequestParam(required = false, defaultValue = "alice") String name,
            @RequestParam(required = false, defaultValue = "email") String email){
        System.out.println(name +" "+email);
        List<User> users = userDb.values().stream()
                .filter(n -> n.getName().equalsIgnoreCase(name))
                .filter(n -> n.getEmail().equalsIgnoreCase(email))
                .toList();
        return ResponseEntity.ok(new ArrayList<>(users));



    }

}
