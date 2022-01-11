package harjoitustyo.omalista;
import harjoitustyo.apulaiset.*;
import java.util.LinkedList;

/**
 * OmaLista-luokka OOPE2-harjoitustyölle
 * <p>
 * Olio-ohjelmoinnin perusteet II, kevät 2020
 *
 * @version 1.0
 * @author Oskari Kansanen, oskari.kansanen@tuni.fi
 */
 
public class OmaLista<E> extends LinkedList<E> implements Ooperoiva<E> {
    /**
     * Lisää kokoelmaan käyttöliittymän kautta annetun dokumentin.
     * <p>
     * Lisäys onnistuu, jos parametri liittyy dokumenttiin, jota voidaan vertailla 
     * Comparable-rajapinnan compareTo-metodilla ja jos kokoelmassa ei ole vielä
     * Dokumentti-luokan equals-metodilla mitaten samanlaista dokumenttia.
     * Null-arvon lisäys epäonnistuu aina.
     *
     * @param uusi viite lisättävään dokumenttiin.
     * @throws IllegalArgumentException jos dokumentin vertailu Comparable-rajapintaa
     * käyttäen ei ole mahdollista, listalla on jo equals-metodin mukaan sama
     * dokumentti eli dokumentti, jonka tunniste on sama kuin uuden dokumentin
     * tai parametri on null.
     */
    @SuppressWarnings({"unchecked"})
    public void lisää(E uusi) 
    throws IllegalArgumentException {
        if (uusi != null && uusi instanceof java.lang.Comparable) {
            // Ensimmäinen elementti menee aina ensimmäiseksi
            if (size() == 0) {
                add(uusi);
            }
            else {
                int i = 0;
                while (i <= size()) {
                    Comparable nykyinen = (Comparable)get(i);
                    // Jos isompi, lisätään vaan
                    if (nykyinen.compareTo(uusi) > 0) {
                        add(i, uusi);
                        break;
                    }
                    // Jos pienempi, niin katsotaan tarkkaan, ennen kuin lisätään
                    else if ((nykyinen.compareTo(uusi) < 0 || nykyinen.compareTo(uusi) == 0) && i == size() - 1) {
                        add(uusi);
                        break;
                    }
                    // Jos ei sopinut mihinkään, niin siirrytään listassa eteenpäin
                    else {
                        i++;
                    }
                }
            }
        }
        else {
            throw new IllegalArgumentException();
        }
    }
}