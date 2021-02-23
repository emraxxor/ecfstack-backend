package hu.emraxxor.fstack.demo.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@XmlRootElement
@NamedQueries({})
@Getter
@Setter
@NoArgsConstructor
@Table(
        name = "album" ,
        indexes = {
                @Index(name = "idx_album_name", columnList = "album_name")
        }
)
public class Album implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(nullable = false)
	private Long id;
   
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "album_name", unique = true, nullable = false)
    private String albumName;
    
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "albums_users", 
    		joinColumns = 
    				@JoinColumn(name="album_id",referencedColumnName = "id"), 
    				inverseJoinColumns = @JoinColumn(name="user_id", referencedColumnName = "uid") )
    private Set<User> users = new HashSet<User>();
    
    
    public void addUser(User e) {
    	users.add(e);
    }
    
    public void removeUser(User e) {
    	users.remove(e);
    }
}
