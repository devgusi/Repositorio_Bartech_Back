package com.bartech.user.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "songs")
public class Song {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long pubId;

    @Column(name="song_name")
    private String songName;
    private String author;
    private String genre;

    @Column(name="played")
    private Boolean isPlayed;

}
