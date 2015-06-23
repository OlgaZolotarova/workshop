package workshop.service;

import java.util.List;

import workshop.dao.KlantDAO;
import workshop.model.Klant;

public class DefaultClientService implements ClientService{

    private KlantDAO dao;
    
    public DefaultClientService(KlantDAO dao) {
        this.dao = dao;
    }
    
    
    @Override
    public void create(Klant klant) {
        validate(klant);
       dao.create(klant);
    }

    private void validate(Klant klant) {
        // TODO Auto-generated method stub
        
    }


    @Override
    public void delete(Klant klant) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void update(Klant klant) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public List<Klant> search(Klant klant) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
