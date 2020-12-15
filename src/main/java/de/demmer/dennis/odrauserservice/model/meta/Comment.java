package de.demmer.dennis.odrauserservice.model.meta;

import de.demmer.dennis.odrauserservice.model.User;
import de.demmer.dennis.odrauserservice.security.UserPrincipal;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Comment {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private long mediaId;

    @Column
    private long userId;

    @Column(columnDefinition="TEXT")
    private String commentText;

    @Column
    private String color;

    @Column
    private int start;

    @Column
    private int end;

    @Column
    private Date timestamp;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Answer> answers = new ArrayList<>();

    @Column(columnDefinition="TEXT")
    private String selectedText;

}
