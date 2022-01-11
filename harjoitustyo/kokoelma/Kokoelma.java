package harjoitustyo.kokoelma;
import harjoitustyo.dokumentit.*;
import harjoitustyo.omalista.*;
import harjoitustyo.apulaiset.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
/**
 * Kokoelmaluokka OOPE2-harjoitustyölle
 * <p>
 * Olio-ohjelmoinnin perusteet II, kevät 2020
 * 
 * @version 1.0
 * @author Oskari Kansanen, oskari.kansanen@tuni.fi
 */
public class Kokoelma implements Kokoava<Dokumentti>{
    /*
     * Attribuutit
     */
    
    /** Lista kaikista kokoelman dokumenteista */
    private OmaLista<Dokumentti> dokumentit;
    
    /*
     * Rakentajat
     */
    public Kokoelma() {
        dokumentit = new OmaLista<Dokumentti>();
    }
    
    /*
     * Aksessorit
     */
    public OmaLista<Dokumentti> dokumentit() {
        return dokumentit;
    }
    
    /*
     * Metodit
     */
     
    /**
     * Lisää annetun dokumentin listaan
     *
     * @param Uusi dokumentti
     * @throws IllegalArgumentException, jos uusi on null;
     */
    public void lisää(Dokumentti uusi)
    throws IllegalArgumentException {
        if (uusi == null) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < dokumentit.size(); i++) {
            if (dokumentit.get(i).equals(uusi)) {
                throw new IllegalArgumentException();
            }
        }
        dokumentit.lisää(uusi);
    }
    
    /**
     * Hakee kokoelmasta tunnisteen perusteella dokumentin
     * 
     * @param haettava tunniste
     * @return löydetty dokumentti, tai null, jos ei löydy
     */
    public Dokumentti hae(int tunniste) {
        try {
            Dokumentti toReturn = null;
            for (int i = 0;i < dokumentit.size();i++) {
                if (dokumentit.get(i).tunniste() == (tunniste)) {
                    toReturn = dokumentit.get(i);
                }
            }
            return toReturn;
        }
        catch (Exception e) {
            return null;
        }
    }
    
    /**
     * Poistaa kokoelmasta dokumentin, jos semmoinen löytyy
     * 
     * @param Käyttäjän antama komento String-muotoisena listana
     */
    public void poista(String[] splitKomento) {
        try {
            // tarkistetaan, onko poistettava dokumentti kokoelmassa
            if (hae(Integer.parseInt(splitKomento[1])) != null) {
                for (int i = 0; i < dokumentit.size(); i++) {
                    // poistetaan se heh
                    if (hae(Integer.parseInt(splitKomento[1])) == dokumentit.get(i)) {
                        dokumentit.remove(i);
                    }
                }
            }
            else {
                System.out.println("Error!");
            }
        }
        catch (Exception e) {
            System.out.println("Error!");
        }
    }
    
    /**
     * Lisää uuden dokumentin kokoelmaan
     *
     * @param komento, ja alkuperäisen tiedoston prefixi eli joko news tai jokes
     */
    public void lisääDokumentti(String komento,String[] filePrefix) {
        String[] toAdd = null;
        // katkaistaan komento siten, että voidaan käsitellä pelkästään xx///yyy///zzz-osaa
        String[] splitKomento = komento.split("add ");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy");
        
        // tehdään lista lisättävästä tavarasta
        toAdd = splitKomento[1].split("///");
        try {
            // katsotaan, että lista on oikean pituinen
            if (toAdd.length == 3) {
                if (hae(Integer.parseInt(toAdd[0])) == null) {
                    // Jos uutiset niin tämä
                    if (filePrefix[0].equals("news")) {
                        LocalDate localdate = LocalDate.parse(toAdd[1],formatter);
                        Uutinen uusiUutinen = new Uutinen(Integer.parseInt(toAdd[0]),localdate,toAdd[2]);
                        lisää(uusiUutinen);
                    }
                    else {
                        // Jos vitsi, niin tarkistetaan ensin, että se ei ole uutinen
                        try {
                            LocalDate testiDate = LocalDate.parse(toAdd[1],formatter);
                            System.out.println("Error!");
                            return;
                        }
                        catch (Exception e) {
                            Vitsi uusiVitsi = new Vitsi(Integer.parseInt(toAdd[0]),toAdd[1],toAdd[2]);
                            lisää(uusiVitsi);
                        }
                    }
                }
                else {
                    System.out.println("Error!");
                }
            }
            else {
                System.out.println("Error!");
            }
        }
        catch (Exception e) {
            System.out.println("Error!");
        }
    }
    
    /**
     * Kutsuu Dokumentti-luokan siivoa-metodia, jotta saadaan kaikki turhat asiat kokoelmasta pois
     * 
     * @param Linkitetty lista sulkusanoista, ja merkkijono poistettavista välimerkeistä
     */
    public void siivoaMerkit(LinkedList<String> sulkusanat,String välimerkit) {
        // Kutsutaan siivoa-metodia jokaiselle dokumentille
        for (int i = 0; i < dokumentit.size(); i++) {
            dokumentit.get(i).siivoa(sulkusanat,välimerkit);
        }
    }
    
    /**
     * Kutsuu Dokumentti-luokan sanatTäsmäävät-metodia, jotta löydetään kaikki täsmäävät dokumentit
     * 
     * @param käyttäjän antama komento
     * @return Linkitetty lista kaikkien täsmäävien dokumenttien tunnisteista
     */
    public LinkedList<Integer> haeTäsmäävät(String komento) {
        String[] splitKomento = komento.split(" ");
        // Lista hakusanoille
        LinkedList<String> hakusanat = new LinkedList<String>();
        // Lista palautettaville tunnisteille
        LinkedList<Integer> toReturn = new LinkedList<Integer>();
        
        // Lisätään hakusanat listaan
        for (int i = 1; i < splitKomento.length; i++) {
            hakusanat.add(splitKomento[i]);
        }
        
        // Käydään läpi kaikki dokumenit, ja jos kaikki sanat löytyy, lisätään ne palautettavaan listaan
        for (int n = 0; n < dokumentit.size(); n++) {
            boolean haku = dokumentit.get(n).sanatTäsmäävät(hakusanat);
            if (haku == true) {
                toReturn.add(dokumentit.get(n).tunniste());
            }
        }
        // Palautellaan
        return toReturn;
    }
}