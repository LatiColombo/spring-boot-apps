package ar.com.ada.second.tdvr.model.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "song")
public class SongDTO implements Serializable {

    private Long id;
    private String title;
    private  String trackDuration;
    private AlbumDTO albumDTO;
}
