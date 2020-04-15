package de.demmer.dennis.odrauserservice.model.meta;

import de.demmer.dennis.odrauserservice.model.User;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Comment {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private int user_id;

    @Column
    private String text;

    @Column
    private int start;

    @Column
    private int end;

    @Column
    private Date timestamp;
}
