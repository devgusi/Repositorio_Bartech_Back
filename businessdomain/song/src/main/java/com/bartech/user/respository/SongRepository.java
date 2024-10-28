/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bartech.user.respository;

import com.bartech.user.entities.Song;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author sotobotero
 */
public interface SongRepository extends JpaRepository<Song, Long> {

    @Query("SELECT s FROM Song s WHERE s.pubId = :pubId")
    List<Song> findAllByPubId(@Param("pubId") Long pubId);

    @Query("SELECT s FROM Song s WHERE s.isPlayed = false AND s.pubId = :pubId")
    List<Song> findNotPlayed(@Param("pubId") Long pubId);

}
