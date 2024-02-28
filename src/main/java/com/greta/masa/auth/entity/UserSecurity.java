package com.greta.masa.auth.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_security")
@Getter
@Setter
public class UserSecurity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "user_security_id")
    private Long userSecurityId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "hashed_password", length = 100)
    private String hashedPassword;

    @Column(name = "personal_salt", length = 100)
    private String personalSalt;

}
