package de.demmer.dennis.odrauserservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
public class NewsColumn {

    @Id
    @GeneratedValue
    private long id;

    @NotBlank
    @Column
    private String type;

    @NotBlank
    @Column
    private String source;

    @Column
    private String query;

    @JsonIgnore
    @ManyToOne
    private User user;


}
