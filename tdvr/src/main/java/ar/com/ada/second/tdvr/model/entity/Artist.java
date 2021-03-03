package ar.com.ada.second.tdvr.model.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name= "Artist")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Artist implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    // En el código de Vladi no está esto así que lo saco
    //@OneToMany(mappedBy = "artist") //acá defino la relación entre album y artista
    //private List<Album> albums;
}
