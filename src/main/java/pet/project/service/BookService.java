package pet.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pet.project.entity.Book;
import pet.project.exception.NotFoundBookException;
import pet.project.repository.BookRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;
    private final PeopleService peopleService;

    @Autowired
    public BookService(BookRepository bookRepository, PeopleService peopleService) {
        this.bookRepository = bookRepository;
        this.peopleService = peopleService;
    }

    public List<Book> findByPersonId(int personId){
        return bookRepository.findByPersonId(personId);
    }

    public List<Book> findAll(){
        return bookRepository.findAll();
    }

    public Book findById(int id){
        return bookRepository.findById(id).orElseThrow(() -> new NotFoundBookException(id));
    }

    @Transactional
    public void save(Book book){
        bookRepository.save(book);
    }
    @Transactional
    public void update(int id, Book updatedBook){
        Book book = findById(id);

        book.setName(updatedBook.getName());
        book.setAuthor(updatedBook.getAuthor());
        book.setYear(updatedBook.getYear());
        book.setPerson(updatedBook.getPerson());

        save(book);
    }
    @Transactional
    public void delete(int id){
        bookRepository.deleteById(id);
    }
    @Transactional
    public void setPerson(int id, int personId){
        Book book = findById(id);
        book.setPerson(peopleService.findById(personId));
        save(book);
    }
}
