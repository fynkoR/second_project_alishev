package pet.project.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import pet.project.entity.Book;
import pet.project.exception.NotFoundPersonException;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class BookRowMapper implements RowMapper<Book> {

    private final PersonDao personDao;

    @Autowired
    public BookRowMapper(PersonDao personDao){
        this.personDao = personDao;
    }

    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book book = new Book();
        book.setId(rs.getInt("id"));
        book.setName(rs.getString("name"));
        book.setAuthor(rs.getString("author"));
        book.setYear(rs.getInt("year"));
        Integer personId = rs.getObject("person_id", Integer.class);
        if(personId == null){
            book.setPerson(null);
        }
        else{
            book.setPerson(personDao.find(personId).orElseThrow(() -> new NotFoundPersonException(personId)));
        }
        return book;
    }
}
