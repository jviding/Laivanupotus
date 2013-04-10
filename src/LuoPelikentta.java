import java.util.Random;
public class LuoPelikentta {
    /**
     * Käytetään lukujen arvonnassa.
     */
    Random randomoitsija = new Random();
    /**
     * Luo pelikentän, sijoittaa siihen laivat ja tallentaa muistiin laivojen määrän ja kunkin laivan pituuden.
     * @param valinta valittu pelikoko
     */
    LuoPelikentta() {
    }
    /**
     * Pelikentän leveys ja korkeus ovat yhtäsuuret, joten niitä merkitään yhteisellä muuttujalla: kentanKanta.
     */
    int kentanKanta;
    /**
     * Pelikenttä; sisältää arvon 0 tyhjissä kohdissa ja niissä kohdissa joissa on laiva, sijaitsee jokin kokonaisluku, joka
     * kuvastaa monentenako kyseinen laiva on asetettu.
     */
    int[][] kentta;
    /**
     * Monentenako laiva on asetettu kenttään.
     */
    int moneskoLaiva=0;
    /**
     * Laivojen tilanteet taulukoituna. Sisältää kunkin laivan pituuden kohdassa laivojenTilanne[moneskoLaiva-1].
     */
    int[] laivojenTilanne;
    /**
     * Asettaa laivat pelikenttään valitun pelikoon perusteella. Pohjustaa myös laivojen pituudet
     * int[] taulukkoon. Laivoja kentällä (valinta x(1+2+3)) ja valinta voi olla 1, 2 tai 3.
     * 4-pituisia valinnan verran, 3-pituisia 2x valinta ja 2-pituisia 3x valinta.
     * @param valinta valittu pelikoko
     */
    void asetaLaivat(int valinta){
        laivojenTilanne=new int[valinta*(1+2+3)];
        for(int pituus=4;pituus>1;pituus--)
            for(int maara=0;maara<valinta*(5-pituus);maara++)
                laivaAsentaja(pituus);
    }
    /**
     * Yrittää asettaa laivaa kenttään niin kauan kunnes onnistuu siinä.
     * @param pituus laivan pituus
     */
    void laivaAsentaja(int pituus){
        laivojenTilanne[moneskoLaiva]=pituus;
        moneskoLaiva+=1;
        while(asetaLaiva(pituus)==false){}
    }
    /**
     * Metodi arpoo luvun väliltä 0 - (luku-1)
     * @param luku syöte, jonka mukaan kokonaisluku arvotaan
     * @return kokonaisluku valilta 0 - (luku-1)
     */
    int luvunArvonta(int luku){
        return randomoitsija.nextInt(luku);
    }
    /**
     * Arpoo laivalle sijoituskohdan ja pyrkii sijoittamaan sen siihen.
     * @param pituus laivan pituus
     * @return true jos laiva sijoittuu, false jos ei.
     */
    boolean asetaLaiva(int pituus){
        int suunta = luvunArvonta(2);
        int asetuskorkeus = luvunArvonta(kentanKanta);
        int asetusleveys = luvunArvonta(kentanKanta);
        return mahtuuko(pituus, suunta, asetuskorkeus, asetusleveys);
    }
    /**
     * Tutkii voiko laivaa sijoittaa tahdottuun kohtaan tietyinpäin. Jos sen voi, niin laiva myös asettuu siihen.
     * @param pituus laivan pituus
     * @param suunta laivan suunta; 0=vaakataso, 1=pystytaso
     * @param asetuskorkeus laivan sijoituksen alkukoordinaatti
     * @param asetusleveys laivan sijoituksen alkukoordinaatti
     * @return true jos laiva asettuu, false jos ei.
     */
    boolean mahtuuko(int pituus, int suunta, int asetuskorkeus, int asetusleveys){
        if(suunta==0)
            return mahtuukoVaakana(pituus, asetuskorkeus,asetusleveys);
        else
            return mahtuukoPystyna(pituus, asetuskorkeus,asetusleveys);
    }
    /**
     * Tutkii mahtuuko laiva kyseiseen pelikentän kohtaan vaaka-asennossa. Jos mahtuu, niin myös sijoittaa sen siihen.
     * @param pituus laivan pituus
     * @param asetuskorkeus toinen sijoituskoordinaatti
     * @param asetusleveys toinen sijoituskoordinaatti
     * @return true jos laiva saadaan sijoitettua, muuten false.
     */
    boolean mahtuukoVaakana(int pituus, int asetuskorkeus, int asetusleveys){
        for(int i=asetusleveys;i<asetusleveys+pituus;i++){
            if(i>=kentanKanta)
                return false;
            if(onkoYmparilla(asetuskorkeus,i)==true)
                return false;
        }
        sijoitaVaakalaiva(asetuskorkeus, asetusleveys, pituus);
        return true;
    }
    /**
     * Sijoittaa laivan vaaka-asentoon ja sen vasemmaisin koordinaatti on (asetuskorkeus,asetusleveys).
     * @param asetuskorkeus toinen alkukoordinaatti
     * @param asetusleveys toinen alkukoordinaatti
     * @param pituus laivan pituus
     */
    void sijoitaVaakalaiva(int asetuskorkeus, int asetusleveys, int pituus){
        for(int a=asetusleveys;a<asetusleveys+pituus;a++)
            kentta[asetuskorkeus][a]=moneskoLaiva;
    }
    /**
     * Tutkii mahtuuko laiva kyseiseen pelikentän kohtaan pystyasennossa. Jos mahtuu, niin myös sijoittaa sen siihen.
     * @param pituus laivan pituus
     * @param asetuskorkeus toinen sijoituskoordinaatti
     * @param asetusleveys toinen sijoituskoordinaatti
     * @return true jos laiva saadaan sijoitettua, muuten false.
     */
    boolean mahtuukoPystyna(int pituus, int asetuskorkeus, int asetusleveys){
        for(int a=asetuskorkeus;a<asetuskorkeus+pituus;a++){
            if(a>=kentanKanta)
                return false;
            if(onkoYmparilla(a,asetusleveys)==true)
                return false;
            }
        sijoitaPystylaiva(asetuskorkeus, asetusleveys, pituus);
        return true;
    }
    /**
     * Sijoittaa laivan pystyasentoon ja sen alin koordinaatti on (asetuskorkeus,asetusleveys).
     * @param asetuskorkeus toinen alkukoordinaatti
     * @param asetusleveys toinen alkukoordinaatti
     * @param pituus laivan pituus
     */
    void sijoitaPystylaiva(int asetuskorkeus, int asetusleveys, int pituus){
        for(int a=asetuskorkeus;a<asetuskorkeus+pituus;a++){
            kentta[a][asetusleveys]=moneskoLaiva;
        }
    }
    /**
     * Ohjelma tutkii onko mahdollisen sijoitusruudun viereisissä ruuduissa laivoja.
     * @param asetuskorkeus toinen sijaintikoordinaatti
     * @param asetusleveys toinen sijaintikoordinaatti
     * @return palauttaa true, jos ympärillä on ja false, jos ympärillä ei ole.
     */
    boolean onkoYmparilla(int asetuskorkeus, int asetusleveys){
        for(int i=-1;i<2;i++)
            for(int a=-1;a<2;a++){
                if(asetuskorkeus+i>=0 && asetuskorkeus+i<kentanKanta && asetusleveys+a>=0
                        && asetusleveys+a<kentanKanta && kentta[asetuskorkeus+i][asetusleveys+a]!=0)
                    return true;
            }
        return false;
    }
    /**
     * Luo pelikentän valitun pelikoon perusteella. Kentän pinta-ala on (valinta x 8)
     * Valinta voi saada arvot 1, 2 ja 3.
     * @param valinta valittu koko pelille
     * @return palauttaa tyhjän pelikentän int[][] -muodossa
     */
    int luoKentta(int valinta) {
        kentanKanta = valinta * 8;
        kentta = new int[kentanKanta][kentanKanta];
        return valinta;
    }
}
