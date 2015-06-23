package workshop.service;

import java.util.List;

import workshop.model.Klant;

/**
 * This service allows creation, validation, deletion of clients.
 * @author olga
 *
 */
public interface ClientService {
    void create(Klant klant);
    void delete(Klant klant);
    void update(Klant klant);
    List<Klant> search(Klant klant);    
}
