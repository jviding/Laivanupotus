public class Pelaa {
    /**
     * Kertoo montako laivaa kunakin hetkenä upotettuna.
     */
    int upotetut=0;
    /**
     * LuoKentta -luokassa luotujen laivojen tilanteet; laivojenTilanne[moneskoLaiva-1] kertoo laivan senhetkisen kunnon.
     */
    int[] laivojenTilanne;
    /**
     * LuoKentta -luokassa luodun kentän kanta, eli pituus tai leveys.
     */
    int kentanKanta;
    /**
     * LuoKentta -luokassa luotu kentta.
     */
    int[][] kentta;
    /**
     * Suorittaa luokan LuoPelikentta ja hakee itselleen siinä luodut parametrit kentta[][], kentanKanta ja laivojenTilanne[].
     * @param valinta valittu pelikoko
     */
    Pelaa(int valinta){
        LuoPelikentta pelikentta = new LuoPelikentta();
        pelikentta.asetaLaivat(pelikentta.luoKentta(valinta));
        this.laivojenTilanne=pelikentta.laivojenTilanne;
        this.kentanKanta=pelikentta.kentanKanta;
        this.kentta=pelikentta.kentta;
    }
    /**
     * Tarkistaa onko laivoja jäljellä.
     * Jos int[] laivojenTilanne on jossain kohdassa >0, on laivoja vielä jäljellä ainakin yksi.
     * @return true jos kaikki eivät ole tuhoutuneet, false jos on.
     */
    boolean onkoJaljella(){
        for(int i=0;i<laivojenTilanne.length;i++)
            if(laivojenTilanne[i]>0)
                return true;
        return false;
    }
    /**
     *Tutkii onko kohdassa (ampumakorkeus, ampumaleveys) mahdollisesti laiva.
     * @param ampumakorkeus koordinaatti kohdalle, johon ammuttu
     * @param ampumaleveys koordinaatti kohdalle, johon ammuttu
     * @return true jos kohdassa laiva, false jos ei ole.
     */
    boolean osuko(int ampumakorkeus, int ampumaleveys){
        if(kentta[ampumakorkeus][ampumaleveys]!=0)
            return true;
        else
            return false;
    }
    /**
     *Tutkii upposiko laiva, kun siihen osui. Vähentää laivojenTilanne[indeksi] arvosta yhden, ja
     * jos tämän jälkeen laivojenTilanne[indeksi]=0, niin laiva upposi.
     * @param indeksi käytetään: laivojenTilanne[indeksi], kun selvitetään laivan kuntoa.
     * @return true jos upposi, muuten false.
     */
    boolean upposiko(int indeksi){
        laivojenTilanne[indeksi]-=1;
        if(laivojenTilanne[indeksi]==0){
            upotetut+=1;
            return true;
        }
        else
            return false;
    }
}
