package harjoitustyo.dokumentit;
import harjoitustyo.apulaiset.*;
import java.util.*;

/**
 * Abstrakti juuriluokka dokumenteille
 * <p>
 * Olio-ohjelmoinnin perusteet II, kevät 2020.
 * 
 * @version 1.0
 * @author Oskari Kansanen, oskari.kansanen@tuni.fi
 */

public abstract class Dokumentti implements Comparable<Dokumentti>,Tietoinen<Dokumentti> {
    /*
     * Julkiset luokkavakiot
     */
     
    /** Tietojen erotin kaikille saatavilla olevana vakiona. */
    public static final String EROTIN = "///";
     
    /*
     * Attribuutit
     */
     
    /** Dokumentin yksilöivä kokonaisluku. */
    private int tunniste;
    /** Dokumentin teksti */
    private String teksti;
    
    /*
     * Rakentajat
     */
    public Dokumentti(int uusiTunniste,String uusiTeksti)
    throws IllegalArgumentException {
        try {
            tunniste(uusiTunniste);
            teksti(uusiTeksti);
        }
        catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }
    
    /*
     * Aksessosrit
     */
    public int tunniste() {
        return tunniste;
    }
    
    public void tunniste(int uusiTunniste)
    throws IllegalArgumentException {
        if (uusiTunniste > 0) {
            tunniste = uusiTunniste;
        }
        else {
            throw new IllegalArgumentException();
        }
    }
    
    public String teksti() {
        return teksti;
    }
    
    public void teksti(String uusiTeksti)
    throws IllegalArgumentException {
        if (uusiTeksti != null && uusiTeksti.length() > 0) {
            teksti = uusiTeksti;
        }
        else {
            throw new IllegalArgumentException();
        }
    }
    
    /*
     * Object-luokan metodien korvaukset
     */
    
    /**
     * Vertailee kahden dokumentin tunnisteita
     * 
     * @param Vertailtava objekti
     * @return false tai true, riippuen siitä, onko tunnisteet samat
     */
    @Override
    public boolean equals(Object obj) {
        try {
            Dokumentti toinen = (Dokumentti)obj;
            return (tunniste == toinen.tunniste());
        }
        catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Muodostaa dokumentin merkkijonoesityksen, joka koostuu tunnisteesta,
     * erottimesta, ja tekstistä.
     * 
     * @return dokumentin merkkijonoesitys.
     */
    @Override
    public String toString() {
        // Hyödynnetään vakiota, jotta ohjelma olisi joustavampi
        return tunniste + EROTIN + teksti;
    }
    
    /**
     * Vertailee kahden dokumentin tunnisteita
     * 
     * @param toinen vertailtava dokumentti
     * @return -1 jos toisen tunniste on isompi, 0 jos tunnisteet samat, 1 jos oma tunniste isompi
     */
    @Override
    public int compareTo(Dokumentti toinen) {
       // Tämä olio < parametrina saatu olio.
       if (tunniste < toinen.tunniste()) {
          return -1;
       }
       // Tämä olio == parametrina saatu olio.
       else if (tunniste == toinen.tunniste()) {
          return 0;
       }
       // Tämä olio > parametrina saatu olio.
       else {
          return 1;
       }
    }
    
    /*
     * Tietoinen-rajapinnan metodien korvaukset
     */
    
    /**
     * Vertailee tekstien sanoja haettaviin sanoihin
     * 
     * @param linkitetty lista hakusanoja
     * @return true, jos kaikki haetut sanat löytyy, false jos ei
     * @throws IllegalArgumentException jos hakusanoja ei ole, tai se on null
     */
    @Override
    public boolean sanatTäsmäävät(LinkedList<String> hakusanat)
    throws IllegalArgumentException {
        if (hakusanat != null && hakusanat.size() > 0) {
            // Jaetaan dokumentin teksti testattaviin palasiin
            String[] testattava = teksti.split(" ");
            // Lista löydetyille hakusanoille
            ArrayList<String> löydetty = new ArrayList<String>();
            
            // Menään listaa läpi, ja jos löydetään täsmäävä sana, heitetään se löydettyjen listalle
            for (int i = 0;i < hakusanat.size(); i++) {
                for (int n = 0;n < testattava.length; n++) {
                    if (hakusanat.get(i).equals(testattava[n])) {
                        // Jos sanaa ei ole vielä löydetty, se pääsee listalle
                        if (!löydetty.contains(testattava[n])) {
                            löydetty.add(testattava[n]);
                        }
                    }
                }
            }
            // Lopuksi, jos kaikki sanat on löydetty, niin true
            if (löydetty.size() == hakusanat.size()) {
                return true;
            }
            // Muuten false
            else {
                return false;
            }
        }
        else {
            throw new IllegalArgumentException();
        }
    }
    
    /**
     * Siivoaa dokumentista kaikki annetut sulkusanat ja välimerkit,
     * muuttaa myös isot kirjaimet pieniksi
     *
     * @param Linkitetty lista, jossa on tiedostosta luetut sulkusanat,
     * ja merkkijono, jossa on poistettavat välimerkit
     * @throws IllegalArgumentException, jos sulkusanoja ei ole, tai ne on null,
     * tai jos välimerkkejä ei ole, tai ne on null
     */
    @Override
    public void siivoa(LinkedList<String> sulkusanat,String välimerkit)
    throws IllegalArgumentException {
        if (sulkusanat != null && sulkusanat.size() > 0 && välimerkit != null && välimerkit.length() > 0) {
            // Splitataan välimerkit listalle
            String[] välimerkitSplit = välimerkit.split("(?!^)");
            // Poistetaan välimerkit
            for (int i = 0; i < välimerkitSplit.length; i++) {
                teksti = teksti.replace(välimerkitSplit[i],"");
            }
            
            // Muutetaan teksti pieniksi kirjaimiksi
            teksti = teksti.toLowerCase();
            
            // Lisätään teksti arraylistiin, koska sitä on helppo käsitellä
            String[] splitTeksti = teksti.split(" ");
            ArrayList<String> muokattavaLista = new ArrayList<String>();
            for (int i = 0; i < splitTeksti.length; i++) {
                muokattavaLista.add(splitTeksti[i]);
            }
            
            // Käydään läpi kaikki sulkusanat jokaiselle tekstin avaimelle
            for (int x = 0;x < sulkusanat.size();x++) {
                for (int y = 0;y < muokattavaLista.size(); y++) {
                    if (muokattavaLista.get(y).equals(sulkusanat.get(x))) {
                        muokattavaLista.remove(y);
                        y--;
                    }
                }
            }
            
            // Poistetaan typerät tyhjät avaimet, jotka jostain syystä jää yli ja en tykkää niistä ollenkaan
            for (int n = 0;n < muokattavaLista.size(); n++) {
                if (muokattavaLista.get(n).equals("")) {
                    muokattavaLista.remove(n);
                }
            }
            
            // Lopuksi tehdään teksti uudestaan
            String teksti = "";
            for (int z = 0;z < muokattavaLista.size(); z++) {
                teksti = teksti + muokattavaLista.get(z);
                if (z != muokattavaLista.size() - 1) {
                    teksti = teksti + " ";
                }
            }
            // Käytetään setteriä uudelle tekstille
            teksti(teksti);
        }
        else {
            throw new IllegalArgumentException();
        }
    }
}