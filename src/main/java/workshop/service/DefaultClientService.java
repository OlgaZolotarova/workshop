package workshop.service;

import java.util.List;

import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;
import workshop.dao.KlantDAO;
import workshop.model.Klant;

public class DefaultClientService implements ClientService {
    
    private KlantDAO dao;
    
    public DefaultClientService(KlantDAO dao) {
        this.dao = dao;
    }
    
    @Override
    public void create(Klant klant) {
        validate(klant);
        int id = dao.create(klant);
        klant.setKlantId(id);
    }
    
    void validate(Klant klant, String... profiles) {
        Validator validator = new Validator();
        
        List<ConstraintViolation> errors;
        
        if (profiles.length == 0) {
            errors = validator.validate(klant, "default");
            
        }
        else {
            errors = validator.validate(klant, profiles);
        }
        
        if (!errors.isEmpty()) { throw new ValidationException(errors); }
    }
    
    @Override
    public void delete(Klant klant) {
        if(klant.getKlantId()==null) {
            throw new IllegalArgumentException("Client id is required");
        }
        if(dao.get(klant.getKlantId())==null) {
            throw new IllegalArgumentException("Client is not in de database");
        }
        dao.delete(klant);
    }
    
    @Override
    public void update(Klant klant) {
        validate(klant, "default", "update");
        dao.update(klant);
    }
    
    @Override
    public List<Klant> search(Klant klant) {
        List<Klant> list = dao.find(klant);
        return list;
    }
    
}
