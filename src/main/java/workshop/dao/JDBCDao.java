package workshop.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import workshop.model.Adres;
import workshop.model.Klant;

public class JDBCDao implements KlantDAO {
    
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * visible for testing
     */
    Connection connection;
    
    public JDBCDao(String connectionURL, String userName, String password) throws SQLException {
        connection = DriverManager.getConnection(connectionURL, userName, password);
        connection.setAutoCommit(true);
    }
    
    public int create(Klant k) {
        try (Statement s = connection.createStatement()) {
            if (k.getAdres() != null) {
                s.executeUpdate(
                        "insert into klant (voornaam, achternaam, tussenvoegsel,email, postcode, huisnummer, "
                                + "toevoeging, straatnaam, woonplaats) values (" + wrapInBrackets(k.getVoornaam()) + ","
                                + wrapInBrackets(k.getAchternaam()) + "," + wrapInBrackets(k.getTussenvoegsel()) + ","
                                + wrapInBrackets(k.getAdres().getEmail()) + "," + wrapInBrackets(k.getAdres().getPostcode()) + ","
                                + wrapInBrackets(k.getAdres().getHuisnummer()) + "," + wrapInBrackets(k.getAdres().getToevoeging())
                                + "," + wrapInBrackets(k.getAdres().getStraatnaam()) + ","
                                + wrapInBrackets(k.getAdres().getWoonplaats()) + ")", Statement.RETURN_GENERATED_KEYS);
            }
            else {
                s.executeUpdate(
                        "insert into klant (voornaam, achternaam, tussenvoegsel) values (" + wrapInBrackets(k.getVoornaam())
                                + "," + wrapInBrackets(k.getAchternaam()) + "," + wrapInBrackets(k.getTussenvoegsel()) + ")",
                        Statement.RETURN_GENERATED_KEYS);
            }
            ResultSet keys = s.getGeneratedKeys();
            keys.next();
            return keys.getInt(1);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public List<Klant> find(Klant searchCriteria) {
        if (searchCriteria == null) { throw new IllegalArgumentException("Please specify search criteria"); }
        
        StringBuilder sql = new StringBuilder("select * from klant where true");
        addCriterion(searchCriteria.getAchternaam(), "achternaam", sql);
        addCriterion(searchCriteria.getVoornaam(), "voornaam", sql);
        addCriterion(searchCriteria.getTussenvoegsel(), "tussenvoegsel", sql);
        
        if (searchCriteria.getAdres() != null) {
            Adres adres = searchCriteria.getAdres();
            addCriterion(adres.getEmail(), "email", sql);
            addCriterion(adres.getPostcode(), "postcode", sql);
            addCriterion(adres.getHuisnummer(), "huisnummer", sql);
            addCriterion(adres.getStraatnaam(), "straatnaam", sql);
            addCriterion(adres.getToevoeging(), "toevoeging", sql);
            addCriterion(adres.getWoonplaats(), "woonplaats", sql);
        }
        
        ArrayList<Klant> list = fetchClients(sql.toString());
        return list;
    }

    private ArrayList<Klant> fetchClients(String sql) {
        ArrayList<Klant> list = new ArrayList<>();
        try (Statement s = connection.createStatement()) {
            ResultSet results = s.executeQuery(sql);
            while (results.next()) {
                Klant klant = new Klant();
                klant.setAdres(new Adres());
                klant.setKlantId(results.getInt("klant_id"));
                klant.setAchternaam(results.getString("achternaam"));
                klant.setTussenvoegsel(results.getString("tussenvoegsel"));
                klant.setVoornaam(results.getString("voornaam"));
                klant.getAdres().setEmail(results.getString("email"));
                klant.getAdres().setPostcode(results.getString("postcode"));
                int huisnummer = results.getInt("huisnummer");
                klant.getAdres().setHuisnummer(results.wasNull() ? null : huisnummer);
                klant.getAdres().setStraatnaam(results.getString("straatnaam"));
                klant.getAdres().setToevoeging(results.getString("toevoeging"));
                klant.getAdres().setWoonplaats(results.getString("woonplaats"));
                list.add(klant);
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    
    private void addCriterion(Object value, String column, StringBuilder sql) {
        if (value != null) {
            sql.append(" and ").append(column).append(" = '").append(value).append("'");
        }
    }
    
    public void update(Klant k) {
        if (k.getKlantId() == null)
            throw new IllegalArgumentException("Client must have id.");
        try (Statement s = connection.createStatement()) {
            if (k.getAdres() != null) {
                s.executeUpdate("update klant set voornaam = " + wrapInBrackets(k.getVoornaam()) + ", achternaam = "
                        + wrapInBrackets(k.getAchternaam()) + ", tussenvoegsel = " + wrapInBrackets(k.getTussenvoegsel())
                        + ", email = " + wrapInBrackets(k.getAdres().getEmail()) + ", postcode = "
                        + wrapInBrackets(k.getAdres().getPostcode()) + ", huisnummer = "
                        + wrapInBrackets(k.getAdres().getHuisnummer()) + ", toevoeging = "
                        + wrapInBrackets(k.getAdres().getToevoeging()) + ", woonplaats = "
                        + wrapInBrackets(k.getAdres().getWoonplaats()) + ", straatnaam = "
                        + wrapInBrackets(k.getAdres().getStraatnaam()) + " where klant_id = " + k.getKlantId());
            }
            else {
                s.executeUpdate("update klant set voornaam = " + wrapInBrackets(k.getVoornaam()) + ", achternaam = "
                        + wrapInBrackets(k.getAchternaam()) + ", tussenvoegsel = " + wrapInBrackets(k.getTussenvoegsel())
                        + " where klant_id = " + k.getKlantId());
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    private String wrapInBrackets(Object value) {
        
        return value == null ? "null" : "'" + value + "'";
    }
    
    public boolean delete(Klant k) {
        if (k.getKlantId() == null)
            throw new IllegalArgumentException("Client must have id.");
        try (Statement statement = connection.createStatement()) {
            int rowsUpdated = statement.executeUpdate("delete from Klant where klant_id = " + k.getKlantId());
            return rowsUpdated != 0;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void close() {
        try {
            connection.close();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        
    }
    
    public Klant get(int id) {
        String sql = "select * from klant where klant_id = " + id;
        ArrayList<Klant> list = fetchClients(sql);
        
      return list.isEmpty()?null:list.get(0);  
        
    }
}
