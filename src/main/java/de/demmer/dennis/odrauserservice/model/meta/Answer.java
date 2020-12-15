package de.demmer.dennis.odrauserservice.model.meta;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Answer {

    @Id
    @GeneratedValue
    private long id;

    @Column(columnDefinition="TEXT")
    private String text;

    @Column
    private long mediaId;

    @Column
    private long userId;

    @Column
    private Date timestamp;
}
