package harjoitustyo.dokumentit;

/**
 * Konkreettinen luokka vitseille
 * <p>
 * Olio-ohjelmoinnin perusteet II, kevät 2020.
 * 
 * @version 1.0
 * @author Oskari Kansanen, oskari.kansanen@tuni.fi
 */

public class Vitsi extends Dokumentti {
    /*
     * Attribuutit
     */
    
    /** Vitsin lajityyppi */
    private String laji;
    
    /*
     * Rakentajat
     */
    public Vitsi(int uusiTunniste,String uusiLaji, String uusiTeksti)
    throws IllegalArgumentException {
        super(uusiTunniste,uusiTeksti);
        try {
            laji(uusiLaji);
        }
        catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }
    
    /*
     * Aksessorit
     */
    public String laji() {
        return laji;
    }
    
    public void laji(String uusiLaji)
    throws IllegalArgumentException {
        if (uusiLaji != null && uusiLaji.length() > 0) {
            laji = uusiLaji;
        }
        else {
            throw new IllegalArgumentException();
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
        return tunniste() + EROTIN + laji + EROTIN + teksti();
    }
}