import java.util.Scanner;
import java.io.*;
import harjoitustyo.kokoelma.*;
import harjoitustyo.dokumentit.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

/**
 * Käyttöliittymäluokka OOPE2-harjoitustyölle
 * <p>
 * Olio-ohjelmoinnin perusteet II, kevät 2020
 *
 * @version 1.0
 * @author Oskari Kansanen, oskari.kansanen@tuni.fi
 */
public class Kayttoliittyma {
    /*
     * Luokan vakiot
     */
     
    /** print-komennon vakio */
    public static final String PRINT = "print";
    /** add-komennon vakio */
    public static final String ADD = "add";
    /** remove-komennon vakio */
    public static final String REMOVE = "remove";
    /** find-komennon vakio */
    public static final String FIND = "find";
    /** polish-komennon vakio */
    public static final String POLISH = "polish";
    /** reset-komennon vakio */
    public static final String RESET = "reset";
    /** echo-komennon vakio */
    public static final String ECHO = "echo";
    /** quit-komennon vakio */
    public static final String QUIT = "quit";
    
    /** formaattivakio */
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("d.M.yyyy");
    
    /**
     * lataa kokoelman annetulta tiedostolta
     *
     * @param ladattavan tiedoston nimi, ja sen prefixi - joko jokes tai news
     * @return ladattu Kokoelma
     */
    public Kokoelma lataaKokoelma(String file1,String[] filePrefix) {
        // Luodaan kokoelma
        Kokoelma toReturn = new Kokoelma();
        Scanner tiedostonlukija = null;
        
        try {
            // Kiinnitetään tiedosto lukijaolioon
            File file = new File(file1);
            tiedostonlukija = new Scanner(file);
            
            // Käydään läpi kaikki linet tiedostosta
            while (tiedostonlukija.hasNextLine()) {
                String line = tiedostonlukija.nextLine();
                String[] lineSplit = line.split("///");
                
                // Jos käsittelemme uutisia, tehdään näin
                if (filePrefix[0].equals("news")) {
                    // Localdaten kanssa leikkiminen on tosi ei kivaa
                    LocalDate localdate = LocalDate.parse(lineSplit[1],FORMATTER);
                    Uutinen uutinen = new Uutinen(Integer.parseInt(lineSplit[0]),localdate,lineSplit[2]);
                    toReturn.lisää(uutinen);
                }
                else {
                    // Jos ei käsitellä uutisia, käsitellään vitsejä ja ne ovat huomattavasti hauskempia käsiteltäviä
                    Vitsi vitsi = new Vitsi(Integer.parseInt(lineSplit[0]),lineSplit[1],lineSplit[2]);
                    toReturn.lisää(vitsi);
                }
            }
            // suljetaan tiedostonlukija, ja palautetaan kokoelma
            tiedostonlukija.close();
            return toReturn;
        }
        catch (Exception e) {
            // O-ou! jos tiedostonlukija jäi auki, suljetaan se
            if (tiedostonlukija != null) {
                tiedostonlukija.close();
            }
            return null;
        }
    }
    
    /**
     * Lataa sulkusanat annetusta tiedostosta
     *
     * @param tiedoston nimi
     * @return linkitetty lista, jossa kaikki sulkusanat
     */
    public LinkedList<String> lataaSulkusanat(String file2) {
        // Käytännössä sama toiminnallisuus, kuin edellisessä metodissa,
        // mutta tässä käsitellää kokoelman sijaan linkitettyä listaa
        LinkedList<String> sulkusanat = new LinkedList<String>();
        Scanner tiedostonLukija = null;
        
        // yritetään 
        try {
            // Lukijaolio jne.
            File file = new File(file2);
            tiedostonLukija = new Scanner(file);
            while (tiedostonLukija.hasNextLine()) {
                // Helposti käsiteltävä juttu, lisätään vaan kaikki sanat listaan
                String line = tiedostonLukija.nextLine();
                sulkusanat.add(line);
            }
            tiedostonLukija.close();
            return sulkusanat;
        }
        catch (Exception e) {
            // jos lukija jäi auki, suljetaan se
            if (tiedostonLukija != null) {
                tiedostonLukija.close();
            }
            return null;
        }
    }
    
    /**
     * Tulostaa annetun dokumentin
     *
     * @param kokoelma ja käyttäjän komento String-muotoisena listana
     */
    public void tulosta(Kokoelma korpus,String[] splitKomento) {
        try {
            // haetaan dokumentti, jos se ei ole null, niin printataan se
            Dokumentti toPrint = korpus.hae(Integer.parseInt(splitKomento[1]));
            if (toPrint != null) {
                System.out.println(toPrint);
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
     * Tulostaa kaikki dokumentit
     *
     * @params korpus
     */
    public void tulostaKaikki(Kokoelma korpus) {
        // PRINT ALL THE THINGS
        for (int i = 0; i < korpus.dokumentit().size(); i++) {
            System.out.println(korpus.dokumentit().get(i));
        }
    }
    
    /**
     * Toinen metodi käyttäjän komennon tarkastelua varten,
     * tämä on tehty, jotta ohjelmaa olisi vähän helpompi lukea
     *
     * @param käyttäjän komento, koko korpus, sulkusanat, ja tiedoston prefixi
     */
    public void splitParse(String komento,Kokoelma korpus,LinkedList<String> sulkusanat,String[] filePrefix) {
        // tehdään komennosta tarkasteltava lista
        String[] splitKomento = komento.split(" ");
        
        // jos print, niin kutsutaan printmetodia
        if (splitKomento[0].equals(PRINT) && splitKomento.length == 2) {
            tulosta(korpus,splitKomento);
        }
        // jos add, niin kutsutaan lisäysmetodia
        else if (splitKomento[0].equals(ADD) && splitKomento.length >= 2) {
            korpus.lisääDokumentti(komento,filePrefix);
        }
        // jos remove, niin kutsutaan poistometodia
        else if (splitKomento[0].equals(REMOVE) && splitKomento.length == 2) {
            korpus.poista(splitKomento);
        }
        // jos find, niin kutsutaan etsimismetodia
        else if (splitKomento[0].equals(FIND) && splitKomento.length != 1) {
            LinkedList<Integer> täsmäävät = korpus.haeTäsmäävät(komento);
            // tässä printataan kaikki löydetyt tunnisteet
            for (int i = 0; i < täsmäävät.size(); i++) {
                System.out.println(täsmäävät.get(i));
            }
        }
        // Viimeinen tarkistus - jos polish, niin kutsutaan polishmetodia ja toivotaan parasta
        else if (splitKomento[0].equals(POLISH) && splitKomento.length == 2) {
            String välimerkit = splitKomento[1];
            korpus.siivoaMerkit(sulkusanat,välimerkit);
        }
        // Jos mikään ei päde, niin error
        else {
            System.out.println("Error!");
        }
    }
    
    /**
     * Käynnistää käyttöliittymän, lataa tiedostot ja hoitaa ensimmäisen osan käyttäjän komentojen käsittelystä
     * 
     * @param korpustiedoston ja sulkusanatiedoston nimet merkkijonoina
     */
    public void kaynnista(String file1,String file2) {
        
        // lippumuuttuja käyttiksen luupille
        boolean jatketaan = true;
        // muuttuja echo-komennolle
        boolean kaiku = false;
        
        // olio käyttäjän syötteiden lukemiseen
        Scanner lukija = new Scanner(System.in);
        String komento = "";
        
        // Ladataan tiedostot kokoelmaan ja listaan
        String[] filePrefix = file1.split("_");
        Kokoelma korpus = lataaKokoelma(file1,filePrefix);
        LinkedList<String> sulkusanat = lataaSulkusanat(file2);
        
        // jos jokin kusee, niin poistutaan
        if (korpus == null || sulkusanat == null) {
            jatketaan = false;
            System.out.println("Missing file!");
            System.out.println("Program terminated.");
        }
        
        // Pääsilmukka
        while (jatketaan) {
            System.out.println("Please, enter a command:");
            komento = lukija.nextLine();
            
            // Hoidetaan kaiku
            if (kaiku == true || komento.equals(ECHO)) {
                System.out.println(komento);
            }
            
            // Hoidetaan poistuminen hallitusti
            if (komento.equals(QUIT)) {
                jatketaan = false;
                System.out.println("Program terminated.");
            }
            // Kaiun togglaus
            else if (komento.equals(ECHO)) {
                if (kaiku == false) {
                    kaiku = true;
                }
                else {
                    kaiku = false;
                }
            }
            // Ladataan kokoelma uudestaan, ja aloitetaan alusta
            else if (komento.equals(RESET)) {
                korpus = lataaKokoelma(file1,filePrefix);
            }
            else if (komento.equals(PRINT)) {
                tulostaKaikki(korpus);
            }
            else {
                // jos mikään äsköisistä ei toiminut, mennään seuraavaan metodiin vaikeampiin komentoihin
                splitParse(komento,korpus,sulkusanat,filePrefix);
            }
        }
    }
}