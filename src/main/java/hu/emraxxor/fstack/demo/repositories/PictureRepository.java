package hu.emraxxor.fstack.demo.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import hu.emraxxor.fstack.demo.mapping.Picture;

public interface PictureRepository extends ElasticsearchRepository<Picture, String> {

	public Page<Picture> findByAlbumId(Long albumId, Pageable pageable);

	public Page<Picture> findAll(Pageable pageable);
}
