package ar.com.ada.second.tdvr.service;

import ar.com.ada.second.tdvr.component.BusinessLogicExceptionComponent;
import ar.com.ada.second.tdvr.model.dto.ArtistDTO;
import ar.com.ada.second.tdvr.model.dto.SongDTO;
import ar.com.ada.second.tdvr.model.entity.Artist;
import ar.com.ada.second.tdvr.model.entity.Song;
import ar.com.ada.second.tdvr.model.mapper.AvoidingMappingContext;
import ar.com.ada.second.tdvr.model.mapper.SongMapper;
import ar.com.ada.second.tdvr.model.repository.ArtistRepository;
import ar.com.ada.second.tdvr.model.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SongService implements Services <SongDTO, Song> {

    private SongMapper songMapper = SongMapper.MAPPER;

    @Autowired
    private BusinessLogicExceptionComponent logicExceptionComponent;

    @Autowired
    private AvoidingMappingContext context;

    @Autowired
    private SongRepository songRepository;

    @Override
    public SongDTO createNew(SongDTO dto) {

        Song song = songMapper.toEntity(dto, context);

        songRepository.save(song);

        SongDTO songSaved = songMapper.toDTO(song, context);

        return songSaved;
    }

    @Override
    public List<SongDTO> getAll() {

        List<Song> songList = songRepository.findAll();

        List<SongDTO> songDTOS = songMapper.toDTO(songList, context);

        return songDTOS;
    }

    @Override
    public SongDTO getById(Long  id) {

        Optional<Song> songOptional = songRepository.findById(id);

        if (songOptional.isPresent()) {
            Song song = songOptional.get();

            SongDTO songByIdDTO = songMapper.toDTO(song, context);

            return songByIdDTO;
        } else {
            throw logicExceptionComponent.getExceptionEntityNotFound("Song", id);
        }
    }

    @Override
    public SongDTO update(SongDTO dto, Long id) {

        Optional<Song> songOptional = songRepository.findById(id);

        Song songById = songOptional
                .orElseThrow(() -> logicExceptionComponent.getExceptionEntityNotFound("Song", id));

        mergeData(songById,dto);

        songRepository.save(songById);

        SongDTO songUpdated = songMapper.toDTO(songById, context);

        return songUpdated;
    }

    @Override
    public void remove(Long id) {

        Optional<Song> songsByIdToDeleteOptional = songRepository.findById(id);

        Song song = songsByIdToDeleteOptional
                .orElseThrow(() -> logicExceptionComponent.getExceptionEntityNotFound("Song", id));

        songRepository.deleteById(id);
    }

    @Override
    public void mergeData(Song entity, SongDTO dto) {
        if (dto.hasNullOrEmptyAttributes())
            throw logicExceptionComponent.getExceptionEntityEmptyValues("Song");

        if (!entity.getName().equals(dto.getName()))
            entity.setName(dto.getName());

    }


}