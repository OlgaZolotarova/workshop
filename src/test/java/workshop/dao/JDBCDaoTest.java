package workshop.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import workshop.model.Adres;
import workshop.model.Klant;

public class JDBCDaoTest {
    
    private JDBCDao dao;
    
    @Before
    public void initialize() throws SQLException {
        dao = new JDBCDao("jdbc:mysql://localhost:3306/test", "test", "test");
    }
    
    @After
    public void tearDown() throws SQLException {
        try (Statement statement = dao.connection.createStatement()) {
            statement.execute("delete from klant");
        }
        dao.close();
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testDelete_NoClientId() throws Exception {
        dao.delete(new Klant());
    }
    
    @Test
    public void testDelete() throws Exception {
        createTestUsers();
        
        Klant klant = new Klant();
        klant.setKlantId(2);
        boolean deleted = dao.delete(klant);
        assertTrue(deleted);
        
        try (Statement s = dao.connection.createStatement()) {
            ResultSet result = s
                    .executeQuery("select count(*) mycount from klant where voornaam = 'Joe' and achternaam = 'Smith'");
            result.next();
            int numberOfJoes = result.getInt(1);
            assertEquals(0, numberOfJoes);
            ResultSet result2 = s.executeQuery("select count(*) from klant");
            result2.next();
            int numbersOfClients = result2.getInt(1);
            assertEquals(2, numbersOfClients);
        }
    }
    
    private void createTestUsers() throws SQLException {
        try (Statement statement = dao.connection.createStatement()) {
            statement.execute("insert into klant (klant_id, voornaam, achternaam) values (1, 'John', 'Smith')");
            statement.execute("insert into klant (klant_id, voornaam, achternaam) values (2, 'Joe', 'Smith')");
            statement
                    .execute("insert into klant (klant_id, voornaam, achternaam, tussenvoegsel, email, postcode, huisnummer, straatnaam, woonplaats, toevoeging) "
                            + "values (3, 'Samuel', 'Johnes', 'de', 'samuel@email.com', '1234FD','45','Street','London','c')");
        }
    }
    
    @Test
    public void testDeleteNonExistingClient() {
        Klant klant = new Klant();
        klant.setKlantId(10);
        boolean deleted = dao.delete(klant);
        assertFalse(deleted);
    }
    
    @Test
    public void testCreateWithoutAdres() throws SQLException {
        Klant klant = new Klant();
        klant.setAchternaam("Ivanov");
        klant.setVoornaam("Ivan");
        klant.setTussenvoegsel("Ivanovich");
        
        int id = dao.create(klant);
        
        try (Statement s = dao.connection.createStatement()) {
            ResultSet result = s.executeQuery("select * from klant where klant_id = " + id);
            assertTrue("record was not created", result.next());
            
            assertEquals("Ivanov", result.getString("achternaam"));
            assertEquals("Ivan", result.getString("voornaam"));
            assertEquals("Ivanovich", result.getString("tussenvoegsel"));
        }
    }
    
    @Test
    public void testCreateWithAdres() throws SQLException {
        Klant klant = createTestClient();
        assertClientIsInDatabase(klant);
    }
    
    private void assertClientIsInDatabase(Klant klant) throws SQLException {
        try (Statement s = dao.connection.createStatement()) {
            ResultSet result = s.executeQuery("select * from klant where klant_id = " + klant.getKlantId());
            assertTrue("record was not created", result.next());
            
            assertEquals(klant.getAchternaam(), result.getString("achternaam"));
            assertEquals(klant.getVoornaam(), result.getString("voornaam"));
            assertEquals(klant.getTussenvoegsel(), result.getString("tussenvoegsel"));
            
            assertEquals(klant.getAdres().getEmail(), result.getString("email"));
            
            int huisnummer = result.getInt("huisnummer");
            assertEquals(klant.getAdres().getHuisnummer(), result.wasNull() ? null : Integer.valueOf(huisnummer));
            assertEquals(klant.getAdres().getPostcode(), result.getString("postcode"));
            assertEquals(klant.getAdres().getStraatnaam(), result.getString("straatnaam"));
            assertEquals(klant.getAdres().getToevoeging(), result.getString("toevoeging"));
            assertEquals(klant.getAdres().getWoonplaats(), result.getString("woonplaats"));
        }
    }
    
    private Klant createTestClient() {
        Klant klant = new Klant();
        klant.setAchternaam("Ivanov");
        klant.setVoornaam("Ivan");
        klant.setTussenvoegsel("Ivanovich");
        Adres adres = new Adres();
        adres.setEmail("klant@gmail.com");
        adres.setHuisnummer(2);
        adres.setPostcode("0101AB");
        adres.setStraatnaam("Straat");
        adres.setToevoeging("a");
        adres.setWoonplaats("Amsterdam");
        klant.setAdres(adres);
        
        int id = dao.create(klant);
        assertNull(klant.getKlantId());
        klant.setKlantId(id);
        return klant;
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testUpdate() {
        Klant klant = new Klant();
        klant.setAchternaam("Ivanov");
        klant.setVoornaam("Ivan");
        klant.setTussenvoegsel("Ivanovich");
        dao.update(klant);
    }
    
    @Test
    public void testUpdateWithoutAdres() throws SQLException {
        Klant klant = createTestClient();
        Adres adres = klant.getAdres();
        klant.setAdres(null);
        klant.setAchternaam("Ivanov2");
        klant.setVoornaam("Ivan2");
        klant.setTussenvoegsel("I");
        
        dao.update(klant);
        klant.setAdres(adres);
        
        assertClientIsInDatabase(klant);
    }
    
    @Test
    public void testUpdateWithAdres() throws SQLException {
        Klant klant = createTestClient();
        Adres adres = klant.getAdres();
        klant.setAchternaam("Ivanov2");
        klant.setVoornaam("Ivan2");
        klant.setTussenvoegsel("I");
        
        adres.setEmail("another@email.com");
        adres.setHuisnummer(3);
        adres.setPostcode("1212ER");
        adres.setStraatnaam("AnotherStraat");
        adres.setToevoeging("b");
        adres.setWoonplaats("Rotterdam");
        
        dao.update(klant);
        
        assertClientIsInDatabase(klant);
    }
    
    @Test
    public void testUpdateWithNulls() throws SQLException {
        Klant klant = createTestClient();
        klant.setTussenvoegsel(null);
        klant.getAdres().setEmail(null);
        klant.getAdres().setHuisnummer(null);
        klant.getAdres().setPostcode(null);
        klant.getAdres().setStraatnaam(null);
        klant.getAdres().setToevoeging(null);
        klant.getAdres().setWoonplaats(null);
        
        dao.update(klant);
        
        assertClientIsInDatabase(klant);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testFindClientRequired() {
        dao.find(null);
    }
    
    @Test
    public void testFindByField() throws SQLException {
        createTestUsers();
        Klant klant = new Klant();
        klant.setAchternaam("Johnes");
        
        List<Klant> results = dao.find(klant);
        
        assertNotNull("result is null",results);
        assertEquals(1, results.size());
        assertEquals(Integer.valueOf(3), results.get(0).getKlantId());
    }
    
}
