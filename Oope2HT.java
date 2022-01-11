/**
 * Käynnistysluokka OOPE2-harjoitustyölle
 * <p>
 * Olio-ohjelmoinnin perusteet II, kevät 2020
 *
 * @version 1.0
 * @author Oskari Kansanen, oskari.kansanen@tuni.fi
 */
public class Oope2HT {   
    public static void main(String[] args) {
        try {
            // Tulostetaan käyttäjälle kiva tervehdys
            System.out.println("Welcome to L.O.T.");
            
            // Tarkastellaan komennon parametrejä
            if (args.length >  2 || 2 > args.length) {
                System.out.println("Wrong number of command-line arguments!");
                System.out.println("Program terminated.");
            }
            else {
                // Leikitään parametreillä, jotta niitä olisi helpompi käsitellä
                String file1 = args[0].toString();
                String file2 = args[1].toString();
                String[] str1 = args[0].split("_");
                String[] str2 = args[1].split("_");
                if (args.length == 2 && str2[0].equals("stop") && (str1[0].equals("jokes") || str1[0].equals("news")))
                {
                    // jos kaikki on okei, käynnistetään käyttis
                    Kayttoliittyma kayttoliittymä = new Kayttoliittyma();
                    kayttoliittymä.kaynnista(file1,file2);
                }
                else {
                    // Jos kaikki ei ole okei, pistetään mesta palamaan ja mennään kotiin
                    System.out.println("Missing file!");
                    System.out.println("Program terminated.");
                }
            }
        }
        // Otetaan KAIKKI poikkeukset kiinni hehheh
        catch (Exception e) {
            System.out.println("Missing file!");
            System.out.println("Program terminated.");
        }
    }
}