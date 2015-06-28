package workshop.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import workshop.dao.KlantDAO;
import workshop.model.Klant;

public class DefaultClientServiceTest {
    private KlantDAO dao;
    private DefaultClientService service;
    private Klant klant;
    
    @Before
    public void prepare() {
        dao = mock(KlantDAO.class);
        service = new DefaultClientService(dao);
        klant = new Klant("Olga", "Zolotarova");
    }
    
    @Test
    public void testCreate() throws Exception {
        when(dao.create(klant)).thenReturn(10);
        
        service.create(klant);
        
        assertEquals(10, klant.getKlantId().intValue());
        
    }
    
    @Test(expected = ValidationException.class)
    public void testUpdate() {
        service.update(klant);
    }
    
    @Test
    public void testUpdateOk() {
        klant.setKlantId(9);
        service.update(klant);
        
        verify(dao, only()).update(klant);
    }
    
    @Test
    public void testValidateTussenvoegsel() {
        klant.setTussenvoegsel("van");
        service.validate(klant);
    }
    
    @Test(expected = ValidationException.class)
    public void testWrongTussenvoegsel() {
        klant.setTussenvoegsel("ibn");
        service.validate(klant);
    }
    
}
