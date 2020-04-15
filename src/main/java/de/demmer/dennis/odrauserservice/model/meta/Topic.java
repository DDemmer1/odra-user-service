package de.demmer.dennis.odrauserservice.model.meta;

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
public class Topic {

    @Id
    @GeneratedValue
    private long id;

    @NonNull
    @Column
    private String topicName;

    @Column
    private long userid;

    @Column
    private boolean isSetByUser;

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
        Topic topic = (Topic) o;
        return Objects.equals(topicName, topic.topicName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(topicName);
    }
}
