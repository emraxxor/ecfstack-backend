package com.github.emraxxor.fstack.demo.data.mapper;

import com.github.emraxxor.fstack.demo.data.type.PhotoFormElement;
import com.github.emraxxor.fstack.demo.data.type.Picture;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author Attila Barna
 */
@Mapper(componentModel = "spring")
public interface PhotoMapper {

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "creationTime", target = "creationTime")
    @Mapping(source = "albumId", target = "albumId")
    PhotoFormElement toPhotoFormElement(Picture picture);
}
