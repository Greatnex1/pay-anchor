package com.payu.payly.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Setter
@Getter
@Table(name = "merchants")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Merchant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
   private String name;
   @Column(unique = true)
   @Email
   private String email;
   @Column(nullable = false)
    private String address;
    @Column(nullable = false)
   private String phoneNumber;
    @CreationTimestamp
    private String dateCreated;
    private String dateUpdated;
}
