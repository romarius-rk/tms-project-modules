import entity.Author;
import entity.Book;
import entity.Genre;
import lombok.Getter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Repository {
    private Connection connection;

    public Repository(Connection connection) {
        this.connection = connection;
    }

    private int executeUpdate(String query) throws SQLException {
        Statement statement = connection.createStatement();
        // Для Insert, Update, Delete
        int result = statement.executeUpdate(query);
        return result;
    }

    private void createAuthorsTable() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS authors\n" +
                "(\n" +
                "    id       SERIAL PRIMARY KEY,\n" +
                "    fullName VARCHAR(100) NOT NULL,\n" +
                "    dob      DATE         NOT NULL\n" +
                ");";
        executeUpdate(query);
    }

    private void createGenresTable() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS genres\n" +
                "(\n" +
                "    id        SERIAL PRIMARY KEY,\n" +
                "    genreName VARCHAR(100) NOT NULL\n" +
                ");";
        executeUpdate(query);
    }

    private void createBooksTable() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS books\n" +
                "(\n" +
                "    id       SERIAL PRIMARY KEY,\n" +
                "    title    VARCHAR(264) NOT NULL,\n" +
                "    genreId  SERIAL       NOT NULL,\n" +
                "    authorId SERIAL       NOT NULL\n" +
                ");";
        executeUpdate(query);
    }

    private void setBooksTableForeignKeys() throws SQLException {
        String query = "ALTER TABLE books\n" +
                "    ADD FOREIGN KEY (genreId) REFERENCES genres (id),\n" +
                "    ADD FOREIGN KEY (authorId) REFERENCES authors (id);";
        executeUpdate(query);
    }

    public void createTables() throws SQLException {
        createBooksTable();
        createGenresTable();
        createAuthorsTable();
        setBooksTableForeignKeys();
    }

    public void fillDB() throws SQLException {
        String queryAuthors = "INSERT INTO authors (fullname, dob)\n" +
                "VALUES ('Author1', '01-01-1987'),\n" +
                "       ('Author2', '02-02-1988'),\n" +
                "       ('Author3', '03-03-1989');";
        String queryGenres = "INSERT INTO genres (genreName)\n" +
                "VALUES ('Adventure'),\n" +
                "       ('Detective');";
        String queryBooks = "INSERT INTO books (title, genreid, authorid)\n" +
                "VALUES ('Fight club',\n" +
                "        (SELECT id FROM genres WHERE genrename = 'Adventure'),\n" +
                "        (SELECT id FROM authors WHERE fullname = 'Author1')),\n" +
                "       ('Arrive',\n" +
                "        (SELECT id FROM genres WHERE genrename = 'Detective'),\n" +
                "        (SELECT id FROM authors WHERE fullname = 'Author2')),\n" +
                "       ('Sherlock Holmes',\n" +
                "        (SELECT id FROM genres WHERE genrename = 'Detective'),\n" +
                "        (SELECT id FROM authors WHERE fullname = 'Author3'));";
        executeUpdate(queryAuthors);
        executeUpdate(queryGenres);
        executeUpdate(queryBooks);
    }

    public List<Book> getAllBooks() throws SQLException {
        String query = "SELECT b.id as bookId, b.title as title, g.id as genreId,\n" +
                "g.genrename as genreName, a.id as authorId, a.fullname as authorName, a.dob as authorDOB\n" +
                " FROM books b INNER JOIN genres g on b.genreid = g.id INNER JOIN authors a on a.id = b.authorid;";
        PreparedStatement statement = connection.prepareStatement(query);
        boolean hasResult = statement.execute();
        ResultSet resultSet = statement.getResultSet();
        List<Book> books = new ArrayList<>();
        while (resultSet.next()) {
            Genre genre = new Genre(resultSet.getInt("genreId"),resultSet.getString("genreName"));
            Author author = new Author(resultSet.getInt("authorId"),resultSet.getString("authorName"),
            resultSet.getDate("authorDOB"));
            Book book = new Book(resultSet.getInt("bookId"),resultSet.getString("title"),
                    genre,author);
            books.add(book);
        }
        return books;
    }
}
