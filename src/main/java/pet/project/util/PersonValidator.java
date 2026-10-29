package pet.project.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pet.project.dao.PersonDao;
import pet.project.entity.Person;

@Component
public class PersonValidator implements Validator {
    private final PersonDao personDao;
    @Autowired
    public PersonValidator(PersonDao personDao){
        this.personDao = personDao;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if(personDao.find(person.getName()).isPresent()){
            errors.rejectValue("name", "", "This name is already exists !");
        }
    }
}
