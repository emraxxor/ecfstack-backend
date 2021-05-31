package com.github.emraxxor.fstack.demo.controllers;

import com.github.emraxxor.fstack.demo.core.web.scroll.DefaultScrollResponse;
import com.github.emraxxor.fstack.demo.core.web.scroll.ScrollResponseGenerator;
import com.github.emraxxor.fstack.demo.core.exceptions.UnsupportedTypeException;
import com.github.emraxxor.fstack.demo.data.type.PhotoFormElement;
import com.github.emraxxor.fstack.demo.data.type.response.StatusResponse;
import com.github.emraxxor.fstack.demo.data.type.source.PhotoWaterfallSource;
import com.github.emraxxor.fstack.demo.service.PictureService;
import com.github.emraxxor.fstack.demo.service.PictureStorageService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/photo")
@AllArgsConstructor
public class PhotoController {

    private final WebApplicationContext context;

    private final PictureService pictureService;

    private final PictureStorageService pictureStorageService;

    @GetMapping
    public ResponseEntity<DefaultScrollResponse<PhotoFormElement,?>> list(
            @RequestParam(name = "token", defaultValue = "", required = false) String token,
            HttpServletRequest request,
            HttpServletResponse response
            ) {
        PhotoWaterfallSource source = new PhotoWaterfallSource(context, token);

        try {
            return ResponseEntity.ok( ScrollResponseGenerator.generate(
                        new DefaultScrollResponse<PhotoFormElement, WebApplicationContext>(), source, request, response
            ));
        } catch (UnsupportedTypeException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/image/{id}")
    @SneakyThrows
    public ResponseEntity<?> picture(@PathVariable String id) {
        var item = pictureService.find(id);
        if ( item.isPresent() ) {
            return ResponseEntity.ok(StatusResponse.success(pictureStorageService.file(item.get().getFileName())));
        }
        return ResponseEntity.notFound().build();
    }

}
