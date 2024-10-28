package com.bartech.user.controller;

import com.bartech.user.entities.User;
import com.bartech.user.respository.UserRepository;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/user/V1")
public class UserRestController {

    @Autowired
    UserRepository userRepository;

    @GetMapping()
    public ResponseEntity<?> list() {
        List<User> userList = userRepository.findAll();
        if(userList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(userList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable(name = "id") long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()) {
            return ResponseEntity.ok().body(optionalUser.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable(name = "id") long id, @RequestBody User input) {
        Optional<User> find = userRepository.findById(id);
        if(find.isPresent()) {
            find.get().setName(input.getName());
            find.get().setSurname(input.getSurname());
            find.get().setUserName(input.getUserName());
            find.get().setPassword(input.getPassword());
            User save = userRepository.save(find.get());
            return ResponseEntity.ok(save);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody User input) {
        User save = userRepository.save(input);
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(save);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") long id) {
        Optional<User> findById = userRepository.findById(id);
        if(findById.isPresent()) {
            userRepository.delete(findById.get());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
