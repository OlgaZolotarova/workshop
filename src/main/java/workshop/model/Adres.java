package workshop.model;

import net.sf.oval.constraint.Email;
import net.sf.oval.constraint.MatchPattern;
import net.sf.oval.constraint.MaxLength;

public class Adres {
    @Email
    private String email;
    @MaxLength(26)
    private String straatnaam;
    @MatchPattern(pattern="^\\d{4}[A-Z]{2}$")
    private String postcode;
    @MaxLength(6)
    private String toevoeging;
    private Integer huisnummer;
    @MaxLength(26)
    private String woonplaats;
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getStraatnaam() {
        return straatnaam;
    }
    public void setStraatnaam(String straatnaam) {
        this.straatnaam = straatnaam;
    }
    public String getPostcode() {
        return postcode;
    }
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
    public String getToevoeging() {
        return toevoeging;
    }
    public void setToevoeging(String toevoeging) {
        this.toevoeging = toevoeging;
    }
    public Integer getHuisnummer() {
        return huisnummer;
    }
    public void setHuisnummer(Integer huisnummer) {
        this.huisnummer = huisnummer;
    }
    public String getWoonplaats() {
        return woonplaats;
    }
    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }
    
    
    
}
