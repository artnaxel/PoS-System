package com.demo.PoS.controller;

import com.demo.PoS.model.entity.User;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    @GetMapping
    public ResponseEntity<?> getAllUsers() {
         return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable UUID userId) {
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable UUID userId, @RequestBody User user) {
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable UUID userId) {
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }
}
