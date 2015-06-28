package workshop.model;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import net.sf.oval.constraint.AssertValid;
import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.MaxLength;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.constraint.ValidateWithMethod;

public class Klant {
    
    @NotNull(profiles="update")
    private Integer klantId;
    
    @NotNull
    @NotBlank
    @MaxLength(50)
    private String voornaam;
   
    @NotNull
    @NotBlank
    @MaxLength(51)
    private String achternaam;
    
    @ValidateWithMethod(methodName="validateTussenvoegsel", parameterType=String.class)
    private String tussenvoegsel;
    
    @AssertValid
    private Adres adres;
    
    public Klant(String voornaam, String achternaam) {
        this.voornaam=voornaam;
        this.achternaam=achternaam;
    }
    public Klant() {
    }
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
    
    public boolean validateTussenvoegsel(String tussenvoegsel) throws IOException, URISyntaxException {
        Path validTussenvoegsels = Paths.get(getClass().getResource("/tussenvoegsels.txt").toURI());
        return Files.lines(validTussenvoegsels).anyMatch(line->line.equals(tussenvoegsel));
       
    }
    
}
