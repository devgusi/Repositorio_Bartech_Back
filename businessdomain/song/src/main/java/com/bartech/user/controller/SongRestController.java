package com.bartech.user.controller;

import com.bartech.user.entities.Song;
import com.bartech.user.respository.SongRepository;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/playlist/V1")
public class SongRestController {

    @Autowired
    SongRepository songRepository;

    @GetMapping
    public ResponseEntity<?> listAll() {
        List<Song> songList = songRepository.findAll();
        if(songList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(songList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listByPubId(@PathVariable(name = "id") long id) {
        List<Song> songList = songRepository.findAllByPubId(id);
        if(songList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(songList);
    }

    @GetMapping("/notplayed/{id}")
    public ResponseEntity<?> listNotPlayedByPubId(@PathVariable(name = "id") long id) {
        List<Song> songList = songRepository.findNotPlayed(id);
        if(songList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(songList);
    }

    @PutMapping("/play/{id}")
    public ResponseEntity<?> playSong(@PathVariable(name = "id") long id) {
        Optional<Song> optionalSong = songRepository.findById(id);
        if(optionalSong.isPresent()) {
            optionalSong.get().setIsPlayed(true);
            Song save = songRepository.save(optionalSong.get());
            return ResponseEntity.ok(save);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/user/song")
    public ResponseEntity<?> post(@RequestBody Song input) {
        Song save = songRepository.save(input);
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(save);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") long id) {
        Optional<Song> findById = songRepository.findById(id);
        if(findById.isPresent()) {
            songRepository.delete(findById.get());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
