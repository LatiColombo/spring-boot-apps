package ar.com.ada.second.tdvr.model.mapper;

import ar.com.ada.second.tdvr.model.dto.SongDTO;
import ar.com.ada.second.tdvr.model.entity.Song;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SongMapper extends DataMapper<SongDTO, Song> {

    SongMapper MAPPER = Mappers.getMapper(SongMapper.class);
}
