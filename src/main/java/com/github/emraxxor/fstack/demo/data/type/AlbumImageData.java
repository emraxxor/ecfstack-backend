package com.github.emraxxor.fstack.demo.data.type;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AlbumImageData extends ImageData {

	@NotNull
	String name;
	
	Long albumId;
	
	@NotNull
	String description;
}
