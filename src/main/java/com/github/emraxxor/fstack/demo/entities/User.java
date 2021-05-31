/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.emraxxor.fstack.demo.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import com.github.emraxxor.fstack.demo.config.ApplicationUserRole;
import com.google.common.collect.Lists;
import com.github.emraxxor.fstack.demo.data.type.IgnoreField;
import lombok.*;

/**
 *
 * @author attila
 */
@Entity
@Table(
        name = "users" ,
        uniqueConstraints = {
                @UniqueConstraint(name = "users_email_unique", columnNames = "email"),
                @UniqueConstraint(name = "users_name_unique", columnNames = "user_name")
        },
        indexes = {
                @Index(name = "idx_is_active", columnList = "is_active"),
                @Index(name = "idx_first_last", columnList = "first_name,last_name")
        }
)
@XmlRootElement
@Builder
@NamedQueries({})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

	@IgnoreField
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "uid", nullable = false)
    @IgnoreField
	private Long userId;
    
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "user_name", unique = true, nullable = false)
    @IgnoreField
    private String userName;
    
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "password",nullable = false)
    @IgnoreField
    private String userPassword;
    
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "email", unique = true)
    private String userMail;
    
    @Column(name="first_name")
    private String firstName;
    
    @Column(name="last_name")
    private String lastName;

    private String address;

    private String state;

    private String city;

    private Long zip;
    
    @IgnoreField
    private String image;

    @Column(name="last_seen")  @IgnoreField
    private LocalDateTime lastSeen;
   
    @Column(name="created_on") @IgnoreField
    private LocalDateTime createdOn;
    
    @NotNull
    @Column(name="is_active") @IgnoreField
    private Boolean isActive;
    
    @Column(nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    @IgnoreField
    private ApplicationUserRole role;
   
    @ManyToMany(mappedBy = "users")
    @IgnoreField
    private Set<Album> albums;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private final List<UserLog> logs = Lists.newArrayList();

    @PrePersist
    public void prePersist() {
        createdOn = LocalDateTime.now();
        role = ApplicationUserRole.USER;
        isActive = true;
        address = "";
        city = "";
        state = "";
        zip = null;
    }

}
