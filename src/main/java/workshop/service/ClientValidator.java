package workshop.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Predicate;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import workshop.model.Klant;

public class ClientValidator implements Validator {
    
    @Override
    public boolean supports(Class clazz) {
        return Klant.class.isAssignableFrom(clazz);
    }
    
    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "voornaam", "field.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "achternaam", "field.empty");
        
        Klant k = (Klant) target;
        if (k.getTussenvoegsel() != null) {
            try {
                Path validTussenvoegsels = Paths.get(getClass().getResource("/tussenvoegsels.txt").toURI());
                boolean tussenvoegselValid = Files.lines(validTussenvoegsels).anyMatch(line->line.equals(k.getTussenvoegsel()));
                if(!tussenvoegselValid) {
                    errors.rejectValue("tussenvoegsel", "invalid.tussenvoegsel");
                }
            }
            catch (IOException | URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
        if(k.getAdres()!=null) {
            
        }
    }
    
}
