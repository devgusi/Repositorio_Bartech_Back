package com.bartech.pub.controller;

import com.bartech.pub.respository.PubRepository;
import com.bartech.pub.entities.Pub;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.Optional;

@RestController
@RequestMapping("/pub/V1")
public class PubRestController {

    @Autowired
    PubRepository pubRepository;

    @GetMapping()
    public ResponseEntity<?> list() {
        List<Pub> pubs = pubRepository.findAll();
        if(pubs.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(pubs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable(name = "id") long id) {
        Optional<List<Pub>> optionalPub = Optional.ofNullable(pubRepository.getPubListByUserId(id));
        if(optionalPub.isPresent()) {
            return ResponseEntity.ok().body(optionalPub.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable(name = "id") long id, @RequestBody Pub input) {
        Optional<Pub> find = pubRepository.findById(id);
        if(find.isPresent()) {
            find.get().setName(input.getName());
            find.get().setAddress(input.getAddress());
            Pub save = pubRepository.save(find.get());
            return ResponseEntity.ok(save);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody Pub input) {
        Pub save = pubRepository.save(input);
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(save);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") long id) {
        Optional<Pub> findById = pubRepository.findById(id);
        if(findById.isPresent()) {
            pubRepository.delete(findById.get());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
