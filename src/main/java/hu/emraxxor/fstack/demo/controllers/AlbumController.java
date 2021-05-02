package hu.emraxxor.fstack.demo.controllers;

import hu.emraxxor.fstack.demo.data.mapper.AlbumFormElementMapper;
import hu.emraxxor.fstack.demo.data.type.AlbumFormElement;
import hu.emraxxor.fstack.demo.data.type.AlbumImageData;
import hu.emraxxor.fstack.demo.data.type.AlbumType;
import hu.emraxxor.fstack.demo.data.type.Picture;
import hu.emraxxor.fstack.demo.data.type.response.StatusResponse;
import hu.emraxxor.fstack.demo.entities.Album;
import hu.emraxxor.fstack.demo.entities.User;
import hu.emraxxor.fstack.demo.service.AlbumService;
import hu.emraxxor.fstack.demo.service.PictureService;
import hu.emraxxor.fstack.demo.service.PictureStorageService;
import hu.emraxxor.fstack.demo.service.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/album")
public class AlbumController {

	private final AlbumService albumService;
	
	private final UserService userService;
	
	private final PictureService pictureService;
	
	private final PictureStorageService pictureStorageService;

	private final AlbumFormElementMapper albumFormElementMapper;

	public AlbumController(
			AlbumService albumService,
			UserService userService,
			PictureService pictureService,
			PictureStorageService pictureStorageService,
			AlbumFormElementMapper albumFormElementMapper
	) {
		this.albumService = albumService;
		this.userService = userService;
		this.pictureService = pictureService;
		this.pictureStorageService = pictureStorageService;
		this.albumFormElementMapper = albumFormElementMapper;
	}

	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody AlbumFormElement element) {
		var o = userService.current();
		if ( o.isPresent() ) {
			Album album = element.toDataElement(Album.class);
			album.addUser(o.get());
			album.addPrivateMember(o.get());
			return ResponseEntity.ok(StatusResponse.success(new AlbumFormElement(albumService.save(album))));
		}
		return ResponseEntity.notFound().build();
	}

	@PutMapping
	public ResponseEntity<?> update(@Valid @RequestBody AlbumFormElement element) {
		var o = userService.current();
		if ( o.isPresent() && element.getId() != null ) {
			var user = o.get();
			var optionalAlbum = albumService.repository().findAlbumByUsers_userIdAndId(user.getUserId(), element.getId());
			if ( optionalAlbum.isPresent() ) {
				var album = optionalAlbum.get();
				album.setAlbumType(AlbumType.valueOf(element.getAlbumType()));
				album.setAlbumName(element.getAlbumName());
				album.setDescription(element.getDescription());
				return ResponseEntity.ok(StatusResponse.success(new AlbumFormElement(albumService.save(album))));
			}
		}
		return ResponseEntity.ok(StatusResponse.error(element));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		var optionalUser = userService.current();
		if ( optionalUser.isPresent() ) {
			var user = optionalUser.get();
			var optionalAlbum = albumService.repository().findAlbumByUsers_userIdAndId(user.getUserId(), id);
			if ( optionalAlbum.isPresent()) {
				var album = optionalAlbum.get();
				var pictures = pictureService.repository().findByAlbumId(album.getId(),PageRequest.of(0,10000));
				pictures.stream().forEach( e -> pictureStorageService.deleteFile(e.getFileName()));
				pictureService.deleteAll(pictures);
				albumService.delete(album);
				return ResponseEntity.ok(StatusResponse.success(album));
			} else {
				return ResponseEntity.ok(StatusResponse.error());
			}
		}
		return ResponseEntity.notFound().build();
	}


	@PostMapping("/{id}")
	public ResponseEntity<?> picture(@PathVariable Long id, @Valid @RequestBody AlbumImageData albumImageDataElement) {
		var albumElement = albumService.find(albumImageDataElement.getAlbumId());
		if ( albumElement.isPresent() ) {
			var album = albumElement.get();
			var file = pictureStorageService.storeFile(albumImageDataElement.getData());
			
			var picture = Picture
							.builder()
							.albumId(album.getId())
							.authors(album.getUsers().stream().map(User::getUserId).collect(Collectors.toList()))
							.path(file.path())
							.fileName(file.name())
							.creationTime(LocalDateTime.now())
							.name(albumImageDataElement.getName())
							.description(albumImageDataElement.getDescription())
							.build();

						
			return ResponseEntity.ok(StatusResponse.success(pictureService.save(picture)));
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/{id}/{page}")
	public ResponseEntity<?> picture(@PathVariable Long id, @PathVariable Integer page) {
		return ResponseEntity.ok(StatusResponse.success( pictureService.repository().findByAlbumId(id, PageRequest.of(page, 12)) ));
	}
	
	@GetMapping
	public ResponseEntity<?> list() {
		var o = userService.current();
		if ( o.isPresent() ) {
			var user = o.get();
			return ResponseEntity.ok(
					  StatusResponse.success(
								user
								.getAlbums()
								.stream()
								.map(AlbumFormElement::new)
								.collect(Collectors.toList())
					  )
				   );
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/total")
	public ResponseEntity<?> total() {
		var o = userService.current();
		if ( o.isPresent() ) {
			var user = o.get();
			return ResponseEntity.ok(
					  StatusResponse.success(
							  albumService.getRepository().countByUsers(user)
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
							  .map(AlbumFormElement::new).collect(Collectors.toList())
					  )
				   );
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/{id}")
    public ResponseEntity<StatusResponse> get(@PathVariable Long id) {
        var album = albumService.find(id);
        if (album.isPresent() && userService.current().isPresent()) {
        	if ( album.get().getUsers().contains( userService.current().get() ) )  {
        		return ResponseEntity.ok( StatusResponse.success( albumFormElementMapper.toAlbumFormElement(album.get()) ));
        	} else {
        		return ResponseEntity.notFound().build();
        	}
        } else {
            return ResponseEntity.notFound().build();
        }
    }
	

}
