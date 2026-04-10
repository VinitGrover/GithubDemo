package com.demo.first.app;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;
    private Map<Integer, User> userDb = new HashMap<>();

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        User createdUser = userService.createUser(user);
//        return ResponseEntity.status(HttpStatus.CREATED).body("User is created");
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user){
        User updatedUser = userService.updateUser(user);
        if(updatedUser == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id){
        Boolean isUserDeleted = userService.deleteUser(id);
        if(isUserDeleted) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User does not exist");
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else {
            return ResponseEntity.ok("successfully removed");
        }
    }

    @GetMapping
    public List<User> getUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{userid}")
    public ResponseEntity<User> getUsers(@PathVariable(value = "userid", required = false) int id){
        User user = userService.getUserById(id);
        if(user == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{userid}/orders/{orderId}")
    public ResponseEntity<User> getUserById(@PathVariable("userid") int id,
                                             @PathVariable int orderId){
        User user = userService.getUser(id, orderId);
        if(user == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsers(
            @RequestParam(required = false, defaultValue = "alice") String name,
            @RequestParam(required = false, defaultValue = "email") String email){
        List<User> users = userService.searchUser(name, email);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/info/{id}")
    public String getInfo(
            @PathVariable int id,
            @RequestParam String name,
            @RequestHeader("User-Agent") String userAgent) {
        return "User agent: " + userAgent +" "+id+" "+ name;
    }

    // Exception Handling method
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException exception){
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timeStamp", LocalDateTime.now());
        errorResponse.put("status", HttpStatus.BAD_REQUEST.value());
        errorResponse.put("error", "Bad request");
        errorResponse.put("message", exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
