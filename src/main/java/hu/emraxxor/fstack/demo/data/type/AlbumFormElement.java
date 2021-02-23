package hu.emraxxor.fstack.demo.data.type;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import hu.emraxxor.fstack.demo.entities.Album;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
	
	public AlbumFormElement(Album e) {
		super(e);
	}
}
