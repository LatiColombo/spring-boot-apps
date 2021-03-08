package ar.com.ada.second.tdvr.service;

import ar.com.ada.second.tdvr.component.BusinessLogicExceptionComponent;
import ar.com.ada.second.tdvr.model.dto.SongDTO;
import ar.com.ada.second.tdvr.model.entity.Album;
import ar.com.ada.second.tdvr.model.entity.Song;
import ar.com.ada.second.tdvr.model.mapper.AvoidingMappingContext;
import ar.com.ada.second.tdvr.model.mapper.SongMapper;
import ar.com.ada.second.tdvr.model.repository.AlbumRepository;
import ar.com.ada.second.tdvr.model.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongService implements Services <SongDTO, Song> {
    private SongMapper songMapper = SongMapper.MAPPER;

    @Autowired
    private BusinessLogicExceptionComponent logicExceptionComponent;

    @Autowired
    private AvoidingMappingContext context;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Override
    public SongDTO createNew(SongDTO dto) {
        return null;
    }

    public SongDTO createNew(SongDTO dto, Long id) {

        /**
         * Este metodo es especial por el tema de las entidades relacionadas, ademas de
         * recibir el dto (datos a guardar en la DB) se recibe el id de la entodad a buscar en la
         * DB para hacer la relacion.
         */

        /**
         * Se busca el album por id (parametro id del metodo) en la base de datos a travez del repositorio.
         * Este album será a que se le asocie la cancion a guardar.
         */
        Album album = albumRepository
                .findById(id)
                .orElseThrow(() -> logicExceptionComponent.getExceptionEntityNotFound("Album", id));

        /**
         * Se comvierte los datos de la cancion del dto a entity y se guarda esos datos en la
         * variable songToSave
         */
        Song songToSave = songMapper.toEntity(dto, context);

        /**
         * Se le asocia el album de la base de datos a objeto songToSave
         */
        songToSave.setAlbum(album);

        /**
         * Se guarda la cancion en la base de datos a travez del repositorio.
         */
        songRepository.save(songToSave);

        /**
         * Luego del guardado, se convierte el objeto trackToSave a DTO para ser entregago
         */
        SongDTO songSaved = songMapper.toDTO(songToSave, context);

        return songSaved;
    }

    @Override
    public List<SongDTO> getAll() {

        /**
         * Se llama al repositorio y se le pede que haga la consulta a la BD de todos
         * los registro de de esa entidad y se guarda en la variable songList
         *
         * Basicamente el metodo .findAll() hace la query select * from
         */
        List<Song> songList = songRepository.findAll();

        /**
         * Se convierte la lista de canciones (songList) a una lista de tipo DTO
         * y se guarda en la variable tracks para luego ser el retorno del metodo.
         */
        List<SongDTO> songs = songMapper.toDTO(songList, context);

        return songs;
    }

    @Override
    public SongDTO getById(Long  id) {
        /**
         * Busco el Track en la base de datos por id (parametro del metodo).
         * En caso que no exista, lanzara una  ExceptionEntityNotFound
         */
        Song songByIdFromDB = songRepository
                .findById(id)
                .orElseThrow(() -> logicExceptionComponent.getExceptionEntityNotFound("Song", id));

        /**
         * Se convierte la cancion (trackByIdFromDB) a un tipo DTO
         * y se guarda en la variable trackById para luego ser el retorno del metodo.
         */
        SongDTO songById = songMapper.toDTO(songByIdFromDB, context);

        return songById;
    }

    @Override
    public SongDTO update(SongDTO dto, Long id) {

        /**
         * Busco el Track en la base de datos por id (parametro del metodo).
         * En caso que no exista, lanzara una  ExceptionEntityNotFound
         */
        Song songByIdFromDB = songRepository
                .findById(id)
                .orElseThrow(() -> logicExceptionComponent.getExceptionEntityNotFound("Song", id));

        /**
         * Este metodo hace la validacion de datos en el dto y los sustituye en el entity
         */
        mergeData(songByIdFromDB, dto);

        /**
         * Se guarda los nuevos datos de la cancion en la base de datos a través del repositorio.
         */
        songRepository.save(songByIdFromDB);

        /**
         * Se convierte la cancion (songkByIdFromDB) a un tipo DTO con los nuevos datos
         * y se guarda en la variable songUpdated para luego ser el retorno del metodo.
         */
        SongDTO songUpdated = songMapper.toDTO(songByIdFromDB, context);

        return songUpdated;
    }

    @Override
    public void remove(Long id) {

        /**
         * Busco el Song en la base de datos por id (parametro del metodo).
         * En caso que no exista, lanzara una  ExceptionEntityNotFound
         */
        Song songToDelete = songRepository
                .findById(id)
                .orElseThrow(() -> logicExceptionComponent.getExceptionEntityNotFound("Song", id));

        /**
         * Con la entidad encontrada en la DB (trackToDelete) se le indica el repository que debe hacer el borrado
         * de este objeto.
         */
        songRepository.delete(songToDelete);
    }

    @Override
    public void mergeData(Song entity, SongDTO dto) {
        if (dto.hasNullOrEmptyAttributes())
            throw logicExceptionComponent.getExceptionEntityEmptyValues("Song");

        if (!entity.getTitle().equals(dto.getTitle()))
            entity.setTitle(dto.getTitle());

        if (!entity.getSongDuration().equals(dto.getSongDuration()))
            entity.setSongDuration(dto.getSongDuration());

    }

    public SongDTO update(SongDTO dto, Long albumId, Long songId) {

        // Busco el Album en la base de datos, si no existe lanzará una ExceptionEntityNotFound
        Album albumByIdFromDB = albumRepository
                .findById(albumId)
                .orElseThrow(() -> logicExceptionComponent.getExceptionEntityNotFound("Album",songId));

        // Busco el Song en la base de datos, si no existe lanzará una ExceptionEntityNotFound
        Song songByIdFromDB = songRepository
                .findById(songId)
                .orElseThrow(() -> logicExceptionComponent.getExceptionEntityNotFound("Song", songId));

        // le seteo el album de la db a la song de la db
        songByIdFromDB.setAlbum(albumByIdFromDB);

        // se mergean los datos del dto a los que se trajo en la db
        mergeData(songByIdFromDB,dto);

        // se guardan los cambios en la db
        songRepository.save(songByIdFromDB);

        // se convierte la entity a dto
        SongDTO songUpdated = songMapper.toDTO(songByIdFromDB, context);

        // se entrega al controlador el dto con los cambios
        return songUpdated;

    }

}