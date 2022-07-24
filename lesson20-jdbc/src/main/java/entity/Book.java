package entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Book extends AbstractEntity {
    private String title;
    private Genre genre;
    private Author author;

    public Book(Integer id, String title, Genre genre, Author author) {
        super(id);
        this.title = title;
        this.genre = genre;
        this.author = author;
    }
}
