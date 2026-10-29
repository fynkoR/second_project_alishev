package pet.project.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pet.project.dao.BookDao;
import pet.project.dao.PersonDao;
import pet.project.entity.Book;
import pet.project.entity.Person;
import pet.project.exception.NotFoundBookException;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookDao bookDao;
    private final PersonDao personDao;

    @Autowired
    public BookController(BookDao bookDao, PersonDao personDao){
        this.bookDao = bookDao;
        this.personDao = personDao;
    }

    @GetMapping
    public String index(Model model){
        model.addAttribute("books", bookDao.findAll());
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person")Person person){
        model.addAttribute("book", bookDao.find(id).orElseThrow(() -> new NotFoundBookException(id)));
        model.addAttribute("people", personDao.findAll());
        return "books/show";
    }
    @GetMapping("/new")
    public String newBook(Model model){
        model.addAttribute("book", new Book());
        return "books/new";
    }
    @PostMapping
    public String create(@ModelAttribute @Valid Book book,
                       BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "books/new";
        }
        bookDao.save(book);
        return "redirect:/books";
    }
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("book", bookDao.find(id).orElseThrow(() -> new NotFoundBookException(id)));
        return "books/edit";
    }
    @PatchMapping("/{id}/edit")
    public String update(@ModelAttribute @Valid Book book,
                         BindingResult bindingResult, @PathVariable("id") int id){
        if(bindingResult.hasErrors()){
            return "books/edit";
        }
        bookDao.update(id, book);
        return "redirect:/books";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        bookDao.delete(id);
        return "redirect:/books";
    }
    @PatchMapping("/{id}")
    public String updateSetPerson(@PathVariable("id") int id,
                                  @RequestParam(value = "personId", required = false) Integer personId){
        bookDao.setPerson(id, personId);
        return "redirect:/books";
    }
}
