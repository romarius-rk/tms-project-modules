package entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Author extends AbstractEntity {
    private String fullName;
    private Date dob;

    public Author(int id, String fullName, Date dob) {
        super(id);
        this.fullName = fullName;
        this.dob = dob;
    }
}
