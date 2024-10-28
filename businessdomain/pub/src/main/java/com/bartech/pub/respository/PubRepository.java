/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bartech.pub.respository;

import com.bartech.pub.entities.Pub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 *
 * @author sotobotero
 */
public interface PubRepository extends JpaRepository<Pub, Long> {

    @Query("SELECT p FROM Pub p WHERE p.UserId = :userId")
    List<Pub> getPubListByUserId(@Param("userId") Long userId);

}
