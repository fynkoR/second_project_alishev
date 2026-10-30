package pet.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pet.project.entity.Book;
import pet.project.entity.Person;
import pet.project.exception.NotFoundBookException;
import pet.project.repository.BookRepository;
import pet.project.util.Validation;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

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
        List<Book> books = bookRepository.findByPersonId(personId);
        checkStatusOverdue(books);
        return books;
    }

    public List<Book> findAll(Integer page, Integer booksPerPage, Boolean sortByYear){
        if(Validation.isNonNegative(page) && Validation.isPositive(booksPerPage) && Validation.isSort(sortByYear)){
            return bookRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("year"))).getContent();
        }
        else if(Validation.isSort(sortByYear)){
            return bookRepository.findAll(Sort.by("year"));
        }
        else{
            return bookRepository.findAll();
        }
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
    public void setPerson(int id, Integer personId){
        Book book = findById(id);

        if(personId == null){
            book.setPerson(null);
            book.setOverdue(false);
        }else{
            Person person = peopleService.findById(personId);
            book.setPerson(person);
            book.setTakenAt(LocalDate.now());
            book.setOverdue(false);
        }
        save(book);
    }

    public Book findByName(String name){
        Optional<Book> optionalBook = Optional.empty();
        if(name != null && !name.isBlank()){
            optionalBook = bookRepository.findByNameStartingWith(name);
        }
        if(optionalBook.isPresent()){
            return optionalBook.get();
        }
        else{
            throw new NotFoundBookException(name);
        }
    }

    private void checkStatusOverdue(List<Book> books){
        LocalDate dateNow = LocalDate.now();

        for(Book book : books){
            LocalDate dateBook = book.getTakenAt();

            long daysBetween = ChronoUnit.DAYS.between(dateBook, dateNow);

            if(daysBetween > 10){
                book.setOverdue(true);
            }
        }
    }
}
