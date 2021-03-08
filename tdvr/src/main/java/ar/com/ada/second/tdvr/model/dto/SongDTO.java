package ar.com.ada.second.tdvr.model.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SongDTO implements Serializable {

    private Long id;

    @NotBlank(message = "is required")
    private String title;

    // Patern es la validación con expresiíon regular
    @NotBlank(message = "is required")
    @Pattern(regexp = "^(?:[01]\\d|2[0123]):(?:[012345]\\d):(?:[012345]\\d)$", message = "wrong format, should be HH:MM:SS")
    private  String songDuration;

    @JsonIgnoreProperties({ "songs" })
    private AlbumDTO album;

    public Boolean hasNullOrEmptyAttributes() {
        return title == null || title.trim().isEmpty()
                || songDuration == null || songDuration.trim().isEmpty();
    }
}
