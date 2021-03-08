package ar.com.ada.second.tdvr.controller;


import ar.com.ada.second.tdvr.model.dto.SongDTO;
import ar.com.ada.second.tdvr.service.SongService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class SongController {

    private SongService songService;

    public ResponseEntity postAlbumMethod(
            @Valid @RequestBody SongDTO dto,
            @PathVariable Long albumId) throws URISyntaxException{

        SongDTO songSaved = songService.createNew(dto, albumId);
        URI uri = new URI("/album/" + songSaved.getId());

        return ResponseEntity
                .created(uri)
                .body(songSaved);

    }
    @GetMapping({ "/songs", "songs" })
    public ResponseEntity getSongsMethod(){
        // se llama al servicio y se le pide el listado de songs
        List<SongDTO> songs = songService.getAll();


        // se crea el response request
        return ResponseEntity
                .ok()
                .body(songs);
    }

    @GetMapping({ "/songs/{id}", "/songs/{id}/" })
    public ResponseEntity getSongByIdMethod(@PathVariable Long id) {

        SongDTO byId = songService.getById(id);

        return ResponseEntity
                .ok()
                .body(byId);
    }

    @DeleteMapping({ "/songs/{id}", "/songs/{id}/" })
    public ResponseEntity deleteSongByIdMethod(@PathVariable Long id) {

        songService.remove(id);

        return ResponseEntity
                .noContent()
                .build();
    }

    public ResponseEntity patchAlbumByIdMethod(
            @RequestBody SongDTO dto,
            @PathVariable Long albumId,
            @PathVariable Long songId) {

        SongDTO songUpdated = songService.update(dto, albumId, songId);

        return ResponseEntity
                .ok()
                .body(songUpdated);
    }
}
