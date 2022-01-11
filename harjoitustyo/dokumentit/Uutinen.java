package harjoitustyo.dokumentit;
import java.time.*;

/**
 * Konkreettinen luokka dokumenteille
 * <p>
 * Olio-ohjelmoinnin perusteet II, kevät 2020
 * 
 * @version 1.0
 * @author Oskari Kansanen, oskari.kansanen@tuni.fi
 */

public class Uutinen extends Dokumentti {
    /*
     * Attribuutit
     */
    
    /** Uutisen päivämäärä */
    private LocalDate päivämäärä;
    
    /*
     * Rakentajat
     */
    public Uutinen(int uusiTunniste,LocalDate uusiPvm,String uusiTeksti)
    throws IllegalArgumentException {
        super(uusiTunniste,uusiTeksti);
        if (uusiPvm != null) {
            päivämäärä(uusiPvm);
        }
        else {
            throw new IllegalArgumentException();
        }
    }
    
    /*
     * Aksessorit
     */
    public LocalDate päivämäärä() {
        return päivämäärä;
    }
    
    public void päivämäärä(LocalDate uusiPäivämäärä) {
        if (uusiPäivämäärä != null) {
            päivämäärä = uusiPäivämäärä;
        }
    }
    
    /*
     * Metodit
     */
     
    /**
     * Muodostaa dokumentin merkkijonoesityksen, joka koostuu tunnisteesta,
     * erottimesta, ja tekstistä.
     * 
     * @return dokumentin merkkijonoesitys.
     */
    public String toString() {
        return tunniste() + EROTIN + päivämäärä.getDayOfMonth() + "." + päivämäärä.getMonthValue() + "." +
        päivämäärä.getYear() + EROTIN + teksti();
    }
}