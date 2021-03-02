package hu.emraxxor.fstack.demo.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
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

import hu.emraxxor.fstack.demo.data.type.AlbumType;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@XmlRootElement
@NamedQueries({})
@Data
@NoArgsConstructor
@Table(
        name = "album" ,
        indexes = {
                @Index(name = "idx_album_name", columnList = "album_name"),
        }
)
public class Album implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(nullable = false)
	private Long id;
   
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "album_name", nullable = false)
    private String albumName;
   
    @NotNull
    @Enumerated
    private AlbumType albumType;
    
    @NotNull
    private String description;
    
    /**
     * This attribute determines who has permission to edit the album.
     */
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "albums_users", 
    		joinColumns = 
    				@JoinColumn(name="album_id",referencedColumnName = "id"), 
    				inverseJoinColumns = @JoinColumn(name="user_id", referencedColumnName = "uid") )
    private Set<User> users = new HashSet<User>();
    
   
    /**
     * This attribute should not be confused with the attribute that identifies users.
     * This attribute represents the users who have permission to view it.
     * Of course, this is only possible for a private album.
     * If the album is public, then this entity does not have any 'member'.
     */
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(
				name = "albums_private_members",
				joinColumns = 
					@JoinColumn(name="album_id",referencedColumnName = "id"), 
					inverseJoinColumns = @JoinColumn(name="user_id", referencedColumnName = "uid") )
	private Set<User> privateMembers = new HashSet<User>();
	
	public void addPrivateMember(User e) {
		this.privateMembers.add(e);
	}
	
	public void removePrivateMember(User e) {
		this.privateMembers.remove(e);
	}
    
    public void addUser(User e) {
    	users.add(e);
    }
    
    public void removeUser(User e) {
    	users.remove(e);
    }
}