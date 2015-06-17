package workshop.dao;

import java.util.List;

import workshop.model.Klant;

/**
 * Implementors of this class will be able to store, find, update and delete
 * clients in the underlying storage.
 * 
 * @author olga
 *
 */
public interface KlantDAO {
    /**
     * 
     * @param k
     *            the client to create
     * @return identifier of the newly created client
     */
    int create(Klant k);
    
    /**
     * Searches for clients filtered by search criteria fields. All fields in
     * the client criteria are optional, which means all clients will be
     * returned.
     * 
     * @param searchCriteria
     * @return list of clients or empty list
     */
    List<Klant> find(Klant searchCriteria);
    
    
    /**
     * Updates all of the fields of client. Klant_id field must be present.
     * @param k the client to be updated.
     */
    void update(Klant k);
    
    /**
     * Deletes client by id.
     * @param k the client to be deleted.
     * @return <code>true</code> in case client was deleted
     */
    boolean delete(Klant k);
    
}
