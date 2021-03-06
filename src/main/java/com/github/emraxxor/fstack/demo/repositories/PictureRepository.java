package com.github.emraxxor.fstack.demo.repositories;

import com.github.emraxxor.fstack.demo.data.type.Picture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PictureRepository extends ElasticsearchRepository<Picture, String> {

	Page<Picture> findByAlbumId(Long albumId, Pageable pageable);

}
