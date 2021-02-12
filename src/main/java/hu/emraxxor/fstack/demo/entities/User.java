/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.emraxxor.fstack.demo.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import hu.emraxxor.fstack.demo.config.ApplicationUserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author attila
 */
@Entity
@Table(name = "users")
@XmlRootElement
@NamedQueries({})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "uid")
	private Long userId;
    
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "user_name")
    private String userName;
    
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "password")
    private String userPassword;
    
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "email")
    private String userMail;
    
    @Column(name="first_name")
    private String firstName;
    
    @Column(name="last_name")
    private String lastName;

    @Column(name="last_seen")
    private LocalDateTime lastSeen;
   
    @Column(name="created_on")
    private LocalDateTime createdOn;
    
    @NotNull
    @Column(name="is_active")
    private Boolean isActive;
    
    @Column(nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    private ApplicationUserRole role;
    
    @PrePersist
    public void prePersist() {
        createdOn = LocalDateTime.now();
        role = ApplicationUserRole.USER;
        isActive = true;
    }

}
