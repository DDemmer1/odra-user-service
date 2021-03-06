package de.demmer.dennis.odrauserservice.model.meta.tag;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.Objects;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
public class Star extends Tag{

    @Id
    @GeneratedValue
    private long id;

    @NonNull
    @Column
    private long userid;

    @NonNull
    @Column
    private Date timestamp;

    @NonNull
    @Column
    private long mediaId;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Star star = (Star) o;
        return userid == star.userid &&
                mediaId == star.mediaId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userid, mediaId);
    }
}
