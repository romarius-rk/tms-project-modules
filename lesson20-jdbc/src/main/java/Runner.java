import entity.Book;

import java.sql.Connection;
import java.sql.DriverManager;

public class Runner {
    public static void main(String[] args) throws Exception {
        Connection con = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/library", "postgres", "root");
        Repository rep = new Repository(con);
        rep.createTables();
//        rep.fillDB();
        System.out.println(rep.getAllBooks());
    }
}
