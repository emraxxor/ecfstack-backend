package hu.emraxxor.fstack.demo.entities;

import hu.emraxxor.fstack.demo.data.type.AlbumType;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Entity
@XmlRootElement
@NamedQueries({})
@Getter
@Setter
@ToString
@RequiredArgsConstructor
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
    @ManyToMany
    @JoinTable(name = "albums_users",
    		joinColumns = 
    				@JoinColumn(name="album_id",referencedColumnName = "id"), 
    				inverseJoinColumns = @JoinColumn(name="user_id", referencedColumnName = "uid") )
    @ToString.Exclude
    private Set<User> users = new HashSet<>();
    
   
    /**
     * This attribute should not be confused with the attribute that identifies users.
     * This attribute represents the users who have permission to view it.
     * Of course, this is only possible for a private album.
     * If the album is public, then this entity does not have any 'member'.
     */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
				name = "albums_private_members",
				joinColumns = 
					@JoinColumn(name="album_id",referencedColumnName = "id"), 
					inverseJoinColumns = @JoinColumn(name="user_id", referencedColumnName = "uid") )
    @ToString.Exclude
	private Set<User> privateMembers = new HashSet<>();
	
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Album album = (Album) o;

        return id != null && id.equals(album.id);
    }

    @Override
    public int hashCode() {
        return 113065996;
    }
}
