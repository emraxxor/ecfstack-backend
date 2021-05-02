package hu.emraxxor.fstack.demo.repositories;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hu.emraxxor.fstack.demo.entities.Album;
import hu.emraxxor.fstack.demo.entities.User;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {

	@Query("from Album")
	List<Album> findAllAlbums(Pageable pg);
	
	@Query("select e.id, e.albumName from Album e ")
	List<Object[]> findAllAlbumsPartialData();

	Optional<Album> findAlbumByUsers_userIdAndId(Long userId, Long id);
	
	List<Album> findByOrderByAlbumNameAsc(Pageable pg);
	
	List<Album> findByUsers_userIdOrderByAlbumNameAsc(Pageable pg, @Param("userId") Long userId);

	long countByUsers_userId(@Param("userId") Long userId);
	
	long countByUsers(User user);
	
	void deleteByAlbumName(String albumName);
}

