package hu.emraxxor.fstack.demo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import hu.emraxxor.fstack.demo.entities.Album;
import hu.emraxxor.fstack.demo.repositories.AlbumRepository;

@Service @Transactional
public class AlbumService extends BasicServiceAdapter<Album, Long, AlbumRepository> {

	
	private final AlbumRepository repository;

	@Autowired
	public AlbumService(AlbumRepository repository) {
		this.repository = repository;
	}

	public List<Album> findAll(Pageable pg, Long uid) {
		return this.repository.findByUsers_userIdOrderByAlbumNameAsc(pg, uid);
	}


	@Override
	public AlbumRepository repository() {
		return repository;
	}
}
