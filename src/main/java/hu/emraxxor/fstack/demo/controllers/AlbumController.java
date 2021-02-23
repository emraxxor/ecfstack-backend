package hu.emraxxor.fstack.demo.controllers;

import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hu.emraxxor.fstack.demo.data.type.AlbumFormElement;
import hu.emraxxor.fstack.demo.data.type.response.StatusResponse;
import hu.emraxxor.fstack.demo.entities.Album;
import hu.emraxxor.fstack.demo.service.AlbumService;
import hu.emraxxor.fstack.demo.service.UserService;

@RestController
@RequestMapping("/api/album")
public class AlbumController {

	@Autowired
	private AlbumService albumService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("")
	public ResponseEntity<?> store(@Valid @RequestBody AlbumFormElement element) {
		var o = userService.current();
		if ( o.isPresent() ) {
			Album album = element.toDataElement(Album.class);
			album.addUser(o.get());
			albumService.save(album);
			return ResponseEntity.ok(StatusResponse.success(new AlbumFormElement(album)));
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping
	public ResponseEntity<?> list() {
		var o = userService.current();
		if ( o.isPresent() ) {
			var user = o.get();
			return ResponseEntity.ok(
					  StatusResponse.success(
						user.getAlbums()
							.stream()
							.map(e -> new AlbumFormElement(e)).collect(Collectors.toList())
					  )
				   );
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/ordered")
	public ResponseEntity<?> listAll(@RequestParam("page") int page) {
		var o = userService.current();
		if ( o.isPresent() ) {
			var user = o.get();
			return ResponseEntity.ok(
					  StatusResponse.success(
							  albumService.findAll(PageRequest.of(page, 20), user.getUserId())
							  .stream()
							  .map(e -> new AlbumFormElement(e)).collect(Collectors.toList())
					  )
				   );
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/{id}")
    public ResponseEntity<StatusResponse> get(@PathVariable Long id) {
        var album = albumService.find(id);
        if (album.isPresent()) {
        	if ( album.get().getUsers().contains( userService.current().get() ) )  {
        		return ResponseEntity.ok( StatusResponse.success(new AlbumFormElement(album.get())) );
        	} else {
        		return ResponseEntity.notFound().build();
        	}
        } else {
            return ResponseEntity.notFound().build();
        }
    }
	

}
