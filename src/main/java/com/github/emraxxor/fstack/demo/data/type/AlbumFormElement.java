package com.github.emraxxor.fstack.demo.data.type;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.github.emraxxor.fstack.demo.entities.Album;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * 
 * @author attila
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlbumFormElement extends FormElement<Album> {
	
	private Long id;
	
    @NotBlank(message = "Name is mandatory")
    @NotNull
	private String albumName;
    
    @NotNull
    private AlbumType albumType;
    
    @NotNull
    @NotBlank(message = "Description is mandatory")
    private String description;
	
	public AlbumFormElement(Album e) {
		super(e);
	}
}
