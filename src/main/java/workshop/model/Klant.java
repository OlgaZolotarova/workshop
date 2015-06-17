package workshop.model;

public class Klant {
    
    private Integer klantId;
    private String voornaam;
    private String achternaam;
    private String tussenvoegsel;
    private Adres adres;
    
    public Integer getKlantId() {
        return klantId;
    }
    public void setKlantId(Integer klantId) {
        this.klantId = klantId;
    }
    public String getVoornaam() {
        return voornaam;
    }
    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }
    public String getAchternaam() {
        return achternaam;
    }
    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }
    public String getTussenvoegsel() {
        return tussenvoegsel;
    }
    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }
    public Adres getAdres() {
        return adres;
    }
    public void setAdres(Adres adres) {
        this.adres = adres;
    }
    
    
}
