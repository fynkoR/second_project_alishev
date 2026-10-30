package pet.project.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pet.project.entity.Book;
import pet.project.entity.Person;
import pet.project.service.BookService;
import pet.project.service.PeopleService;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final PeopleService peopleService;

    @Autowired
    public BookController(BookService bookService, PeopleService peopleService){
        this.bookService = bookService;
        this.peopleService = peopleService;
    }

    @GetMapping
    public String index(Model model, @RequestParam(name = "page", required = false) Integer page,
                        @RequestParam(name = "books_per_page", required = false) Integer booksPerPage,
                        @RequestParam(name = "sort_by_year", required = false) Boolean sortByYear){
        model.addAttribute("books", bookService.findAll(page, booksPerPage, sortByYear));
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person")Person person){
        model.addAttribute("book", bookService.findById(id));
        model.addAttribute("people", peopleService.findAll());
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
        bookService.save(book);
        return "redirect:/books";
    }
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("book", bookService.findById(id));
        return "books/edit";
    }
    @PatchMapping("/{id}/edit")
    public String update(@ModelAttribute @Valid Book book,
                         BindingResult bindingResult, @PathVariable("id") int id){
        if(bindingResult.hasErrors()){
            return "books/edit";
        }
        bookService.update(id, book);
        return "redirect:/books";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        bookService.delete(id);
        return "redirect:/books";
    }
    @PatchMapping("/{id}")
    public String updateSetPerson(@PathVariable("id") int id,
                                  @RequestParam(value = "personId", required = false) Integer personId){
        bookService.setPerson(id, personId);
        return "redirect:/books";
    }
}
