/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bartech.pub.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 *
 * @author sotobotero
 */
@Entity
@Data
@Table(name = "pubs")
public class Pub {
   @Id
   @GeneratedValue(strategy=GenerationType.AUTO)
   private Long id;
   private Long UserId;
   private String address;
   private String name;

}
