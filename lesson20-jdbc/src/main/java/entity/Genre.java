package entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Genre extends AbstractEntity {
    private String genreName;

    public Genre(int id, String genreName) {
        super(id);
        this.genreName = genreName;
    }
}
