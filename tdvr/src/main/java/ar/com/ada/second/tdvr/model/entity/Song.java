package ar.com.ada.second.tdvr.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "Song")
public class Song implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(name = "songDuration", nullable = false)
    private String songDuration;

    @ManyToOne
    @JoinColumn(name = "Album_id", nullable = false, foreignKey = @ForeignKey(name = "fk_Song_Album"))
    private Album album;

}
