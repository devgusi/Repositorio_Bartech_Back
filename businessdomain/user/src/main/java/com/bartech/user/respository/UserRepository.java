/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bartech.user.respository;

import com.bartech.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author sotobotero
 */
public interface UserRepository extends JpaRepository<User, Long> {

}
