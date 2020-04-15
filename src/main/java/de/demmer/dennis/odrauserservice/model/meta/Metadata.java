package de.demmer.dennis.odrauserservice.model.meta;

import de.demmer.dennis.odrauserservice.model.meta.tag.Flag;
import de.demmer.dennis.odrauserservice.model.meta.tag.Star;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@RequiredArgsConstructor
@NoArgsConstructor
public class Metadata {

    @Id
    @GeneratedValue
    private long id;

    @NonNull
    @Column
    private Long mediaId;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Topic> topics = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<Flag> flags = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<Star> stars = new ArrayList<>();

    @Column
    private Float trustScore;



}
