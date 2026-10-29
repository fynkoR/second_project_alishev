package pet.project.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import pet.project.entity.Book;
import pet.project.entity.Person;

import java.util.List;
import java.util.Optional;

@Component
public class BookDao {
    private final JdbcTemplate jdbcTemplate;
    private final PersonDao personDao;

    @Autowired
    public BookDao(JdbcTemplate jdbcTemplate, PersonDao personDao){
        this.jdbcTemplate = jdbcTemplate;
        this.personDao = personDao;
    }

    public List<Book> findAll(){
        return jdbcTemplate.query("SELECT * FROM book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Optional<Book> find(int id){
        return jdbcTemplate.query("SELECT * FROM book WHERE id = ?", new BookRowMapper(personDao), id)
                .stream().findAny();
    }

    public List<Book> findAllBooksByPerson(int person_id){
        return jdbcTemplate.query("SELECT * FROM book WHERE person_id = ?", new BeanPropertyRowMapper<>(Book.class), person_id);
    }

    public void save(Book book){
        jdbcTemplate.update("INSERT INTO book (name, author, year) VALUES (?,?,?)",
                book.getName(), book.getAuthor(), book.getYear());
    }


    public void update(int id, Book book){
        jdbcTemplate.update("UPDATE book SET name = ?, author = ?, year = ? WHERE id = ?",
                book.getName(), book.getAuthor(), book.getYear(), id);
    }

    public void setPerson(int id, Integer person_id){
        jdbcTemplate.update("UPDATE book SET person_id = ? WHERE id = ?",
                person_id, id);
    }

    public void delete(int id){
        jdbcTemplate.update("DELETE FROM book WHERE id = ?", id);
    }
}
