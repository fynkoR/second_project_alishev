package pet.project.entity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Book {
    private int id;

    @NotEmpty
    @Size(min = 2, max = 100, message = "Name should be between 2 and 100 characters")
    private String name;

    @NotEmpty
    @Size(min = 2, max = 100, message = "Name author should be between 2 and 100 characters")
    private String author;

    @Min(value = 1900, message = "Year book should be from 1900")
    private int year;
    private Person person;

    public Book(){}

    public Book(int id, String name, String author, int year, Person person){
        this.id = id;
        this.name = name;
        this.author = author;
        this.year = year;
        this.person = person;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                ", person=" + person +
                '}';
    }
}
