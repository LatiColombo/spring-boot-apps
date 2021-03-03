package ar.com.ada.second.tdvr.service;


import ar.com.ada.second.tdvr.component.BusinessLogicExceptionComponent;
import ar.com.ada.second.tdvr.model.dto.ArtistDTO;
import ar.com.ada.second.tdvr.model.entity.Artist;
import ar.com.ada.second.tdvr.model.mapper.ArtistMapper;
import ar.com.ada.second.tdvr.model.mapper.AvoidingMappingContext;
import ar.com.ada.second.tdvr.model.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// esta capa consulta, crea, modifica y borra registros en el sistema

@Service
public class ArtistService implements Services<ArtistDTO, Artist> {

    private ArtistMapper artistMapper = ArtistMapper.MAPPER;

    @Autowired
    private BusinessLogicExceptionComponent logicExceptionComponent;

    @Autowired
    private AvoidingMappingContext context;

    @Autowired
    private ArtistRepository artistRepository;

    @Override
    public ArtistDTO createNew(ArtistDTO dto) {
        // debo hacer la conversión de dto a entity
        Artist artist = artistMapper.toEntity(dto, context);

        // se le pide al repository que guarde la entidad
        artistRepository.save(artist);

        // convierte a dto la instancia artist con el id que le asignó MySQL
        ArtistDTO artistSaved = artistMapper.toDTO(artist, context);

        // le entrego al controlador el dto con el id
        return artistSaved;
    }

    @Override
    public List<ArtistDTO> getAll() {
        // llamar al repositorio y pedirle que haga la consulta de la BD de todos los registros de la entidad
        List<Artist> artistList = artistRepository.findAll();// el findall es un "select * from"

        // convertir esa lista de DAO a una lista de DTO
        List<ArtistDTO> artistDTOS = artistMapper.toDTO(artistList, context);

        /*
        ArrayList<ArtistDTO> artistDtoList = new ArrayList<>();

        artistList
                .stream()
                .peek(artist -> {
                     ArtistDTO artistDTO = new ArtistDTO(artist.getId(), artist.getName());
                     artistDtoList.add(artistDTO);
        });
        */

        //retorno la lissta resultante de la conversión
        return artistDTOS;
    }

    @Override
    public ArtistDTO getById(Long id) {

        Optional<Artist> artistOptional = artistRepository.findById(id);

        if (artistOptional.isPresent()){
            Artist artist = artistOptional.get();

            ArtistDTO artistById = artistMapper.toDTO(artist, context);

            return artistById;
        } else {
            throw logicExceptionComponent.getExceptionEntityNotFound("Artist", id);
        }
    }

    @Override
    public ArtistDTO update(ArtistDTO dto, Long id) {

        //verifico si el id existe en la base de datos
        Optional<Artist> artistOptional = artistRepository.findById(id);

        Artist artistById = artistOptional
                .orElseThrow(() -> logicExceptionComponent.getExceptionEntityNotFound("Artist", id));

        mergeData(artistById, dto);

        artistRepository.save(artistById);

        ArtistDTO artistUpdated = artistMapper.toDTO(artistById, context); // acá lo estoy convirtiendo de artist a dto

        return artistUpdated;
    }

    @Override
    public void remove(Long id) {
        Optional<Artist> artistByIdToDeleteOptional = artistRepository.findById(id);

        Artist artist = artistByIdToDeleteOptional
                .orElseThrow(() -> logicExceptionComponent.getExceptionEntityNotFound("Artist", id));

        artistRepository.deleteById(id);

    }

    @Override
    public void mergeData(Artist entity, ArtistDTO dto) {
        if (dto.hasNullOrEmptyAttributes()) // lógica para ver si tiene campos nulos
            throw logicExceptionComponent.getExceptionEntityEmptyValues("Artist");

        if (!entity.getName().equals(dto.getName()))
            entity.setName(dto.getName()); // acá sustituyo el valor del campo name en la entidad del campo name (si hubiera más campos habría que hacer lo mismo por cada uno)

    }


    /* OTRA MANERA DE HACERLO (ESTA LA HIZO VLADI): (OJO, HAY QUE MODIFICAR UN POCO OTRAS COSAS)
    public ArtistDTO removeById(Long id) {
        Optional<Artist> artistByIdToDeleteOptional = artistRepository.findById(id);

        Artist artist = artistByIdToDeleteOptional
                .orElseThrow(() -> logicExceptionComponent.getExceptionEntityNotFound("Artist", id));

        artistRepository.delete(artist);

        ArtistDTO artistDeleted = artistMapper.toDTO(artist, context);

        return artistDeleted;

     */

}
