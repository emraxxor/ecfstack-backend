package hu.emraxxor.fstack.demo.data.mapper;

import hu.emraxxor.fstack.demo.data.type.AlbumFormElement;
import hu.emraxxor.fstack.demo.entities.Album;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author Attila Barna
 */
@Mapper(componentModel = "spring")
public interface AlbumFormElementMapper {

    @Mapping(source = "albumName", target = "albumName")
    @Mapping(source = "albumType", target = "albumType")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "id", target = "id")
    AlbumFormElement toAlbumFormElement(Album album);
}
