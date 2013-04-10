import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
public class Upotusikkuna extends JFrame {
    /**
     * Pelikentän ruudut, joita pelissä klikkaillaan.
     */
    JButton[] pelinappulat;
    /**
     * Pelikentän päälle tulevat koordinaattikirjaimet.
     */
    JLabel[] kirjaimet;
    /**
     * Pelikentän sivuun tulevat koordinaattiluvut.
     */
    JLabel[] numerot;
    /**
     * Peli sisältää pelikentän ja toimii eräänlaisena GUI:n ja pelikentän välikätenä.
     */
    Pelaa peli;
    /**
     * Pelikentän korkeus.
     */
    int korkeus;
    /**
     * Pelikentän leveys.
     */
    int leveys;
    /**
     * Klikkaukset kertovat kuinka monta eri ruutua pelikentästä on klikattuna.
     */
    int klikkaukset=0;
    /**
     * Sijaitsee GUI:n ylälaidassa ja kertoo klikkaukset sekä upotettujen laivojen määrän.
     */
    JLabel ylarivi;
    /**
     * Kertoo valitun pelin koon.
     */
    int valinta;
    /**
     * Luo GUI:n valitun pelikoon mukaan; pelikentta ActionListenereineen,
     * koordinaatit laidoille ja ylalaidan pelitilannetaulu. Suorittaa myös luokan Pelaa.
     * @param valinta valittu pelikoko
     */
    Upotusikkuna(int valinta){
        this.valinta=valinta;
        peli = new Pelaa(valinta);
        pohjustaArvot();
        luoReunakoordinaatit();
        luoNappitapahtumat();
        luoPaneelit();
    }
    /**
     * Muuttaa laivan kaikki ruudut punaisiksi ja tarkistaa onko laivoja enempää jäljellä.
     * Jos upotettu laiva oli viimeinen, niin suoritetaan lopetaPelikierros();.
     * @param i klikatun ruudun sijainti
     */
    void laivaUpposi(int i){
        for(int a=0;a<pelinappulat.length;a++)
            if(peli.kentta[a/leveys][a%leveys]==peli.kentta[i/leveys][i%leveys])
                pelinappulat[a].setBackground(Color.red);
        if(peli.onkoJaljella()==false)
            lopetaPelikierros();
    }
    /**
     * Luodaan graafinen ulkoasu pelille, mikä paneeli mihinkin kohtaan GUI:ta.
     */
    void luoPaneelit(){
        this.setLayout(new BorderLayout());
        this.add("Center", getPelinappipaneeli());
        this.add("West", getNumeropaneeli());
        this.add("North", getYlarivi());
    }
    /**
     *Luo JLabelin, joka sijoitetaan GUI:n yläreunaan.
     * JLabelissa lukee eri klikattujen ruutujen ja upotettujen laivojen määrät.
     * @return palauttaa muodostetun JLabelin
-     */
    JLabel getYlarivi(){
        ylarivi = new JLabel("Klikkaukset: "+klikkaukset+"     Upotettu: "
                    +peli.upotetut+"/"+peli.laivojenTilanne.length);
        return ylarivi;
    }
    /**
     * Luo paneelin, joka tulee pelikentän vasempaan laitaan ja jossa on koordinaattiluvut.
     * @return JPanel luvuilla 1,2,...,korkeus
     */
    JPanel getNumeropaneeli(){
        JPanel numeropaneeli = new JPanel(new GridLayout(korkeus+1, 1));
        for(int riviluvut=0;riviluvut<korkeus+1;riviluvut++)
            numeropaneeli.add(numerot[riviluvut]);
        return numeropaneeli;
    }
    /**
     * Luo paneelin, jossa pelikentän ruudukko ja sen päällä kirjainkoordinaatit.
     * @return JPanel, jossa peliruudut ja kirjainkoordinaatit.
     */
    JPanel getPelinappipaneeli(){
        JPanel pelinappipaneeli = new JPanel(new GridLayout(korkeus+1, leveys));
        for(int ekaRivi=0;ekaRivi<leveys;ekaRivi++)
            pelinappipaneeli.add(kirjaimet[ekaRivi]);
        for(int arvausnapit=0;arvausnapit<korkeus*leveys;arvausnapit++)
            pelinappipaneeli.add(pelinappulat[arvausnapit]);
        return pelinappipaneeli;
    }
    /**
     * Hakee kentän korkeuden ja leveyden luodusta Pelaa luokasta, muodostaa JLabel[leveys+1] kirjaimet sekä
     * JLabel[korkeus+1] numerot, ja näiden lisäksi vielä JButton[korkeus*leveys] pelinappulat.
     */
    void pohjustaArvot(){
        this.korkeus=peli.kentanKanta;
        this.leveys=peli.kentanKanta;
        kirjaimet = new JLabel[leveys+1];
        numerot = new JLabel[korkeus+1];
        pelinappulat = new JButton[korkeus*leveys];
    }
    /**
     * Muodostaa kaksi eri String[]:iä, joista toisessa aakkoset ja toisessa
     * kokonaislukuja. Tämän jälkeen asettaa ne JLabel[] kirjaimet ja JLabel[] numerot sijainteihin,
     *  joista muodostuu pelikentän reunojen koordinaattiarvot.
     */
    void luoReunakoordinaatit(){
        String[] aakkoset = new String[] {"    A","    B","    C","    D",
        "    E","    F","    G","    H","    I","    J","    K","    L","    M",
        "    N","    O","    P","    Q","    R","    S","    T","    U","    V",
        "    W","    X","    Y"};
        String[] lukujono = new String[] {""," 1"," 2"," 3"," 4"," 5"," 6"," 7"," 8"," 9",
        "10","11","12","13","14","15","16","17","18","19","20","21","22","23","24"};
        for(int q=0;q<korkeus+1;q++){
            kirjaimet[q]=new JLabel(aakkoset[q]);
            numerot[q]=new JLabel(lukujono[q]);
        }
    }
    /**
     * Boolean[] kertoo kunkin indeksin totuusarvolla aina onko mahdollista
     *  klikattua peliruutua klikattu jo.
     */
    boolean[] onkoPainettuJo;
    /**
     * Muodostaa boolean[pelinappulat.length] onkoPainettuJo, jossa on aluksi jokaista
     *  pelikentän nappia varten oma indeksoitu kohta arvolla false.
     * Tämän jälkeen luo pelikentän ruuduille omat ActionListenerit.
     * Pelikentän ruutua painamalla suoritetaan nappiaPainetaan(i), jossa i kertoo ruudun
     * paikan.
     */
    void luoNappitapahtumat(){
        onkoPainettuJo = new boolean[pelinappulat.length];
        for(int i=0;i<korkeus*leveys;i++){
            pelinappulat[i] = new JButton("");
            pelinappulat[i].addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent tapahtuma){
                        for(int i=0;i<pelinappulat.length;i++)
                            if(pelinappulat[i]==tapahtuma.getSource()){
                                nappiaPainetaan(i);
                                break;
                            }
                    }
                }
            );
        }
    }
    /**
     * Jos ruutua on jo klikattu aiemmin, ei tapahdu mitään.
     * Jos ruutua painetaan vasta ensimmäistä kertaa, merkataan se klikatuksi ja
     * suoritetaan suoritetaanNappi(i);.
     * Samalla yläreunan pelitilannetaulu päivittyy klikkausten osalta.
     * @param i painetun ruudun sijainti
     */
    void nappiaPainetaan(int i){
        if(onkoPainettuJo[i]==false){
            klikkaukset+=1;
            ylarivi.setText("Klikkaukset: "+klikkaukset+"     Upotettu: "
                    +peli.upotetut+"/"+peli.laivojenTilanne.length);
            suoritetaanNappi(i);
            onkoPainettuJo[i]=true;
        }
    }
    /**
     * Jos klikatussa kohdassa ei ole laivaa, muuttuu ruutu siniseksi ja siihen
     *  ilmestyy merkintä O.
     * Jos ruudusta löytyykin laiva, muutetaan se keltaiseksi ja merkitään siihen X,
     *  jonka jälkeen suoritetaan upposikoLaiva(i);.
     * @param i
     */
    void suoritetaanNappi(int i){
        if(peli.osuko(i/leveys,i%leveys)==true){
                pelinappulat[i].setText("X");
                pelinappulat[i].setBackground(Color.yellow);
                upposikoLaiva(i);
            }
        else{
                pelinappulat[i].setText("O");
                pelinappulat[i].setBackground(Color.blue);
            }
    }
    /**
     * Tarkastaa upposiko laiva kun siihen osui.
     * Jos ei, niin ei tapahdu mitään
     * Jos osui, niin suoritetaan laivaUpposi(i);.
     * @param i klikatun ruudun sijainti
     */
    void upposikoLaiva(int i) {
        if(peli.upposiko(peli.kentta[i/leveys][i%leveys]-1)==true){
            ylarivi.setText("Klikkaukset: "+klikkaukset+"     Upotettu: "
                +peli.upotetut+"/"+peli.laivojenTilanne.length);
            laivaUpposi(i);
        }
    }
    /**
     * Avaa JOptionPane:n, jossa lukee laivojen upottamiseen käytetty klikkausten määrä.
     * Samalla myös suorittaa tallenna(); ja dispose();.
     */
    void lopetaPelikierros(){
        JOptionPane.showMessageDialog(null, "Klikkausten maara: "+klikkaukset);
        tallenna();
        dispose();
    }
    /**
     *Ottaa käsittelyyn tiedoston "tulokset.txt", hakee siitä huipputulokset ja
     * muuttaa, jos saatu tulos on parempi kuin aiempi ennätys. Tämän jälkeen
     * tallentaa tulokset takaisin samaiseen "tulokset.txt" tiedostoon.
     */
    void tallenna(){
        File tulokset = new File("tulokset.txt");
        Scanner lukija = luoScanner(tulokset);
        String[] tekstitulos = haeTulokset(lukija);
        tallennaTulokset(tekstitulos, tulokset);
    }
    /**
     *Luo Scannerin, joka syöttää "tulokset.txt" sisältöä.
     * Samalla tarkistaa syöttötiedoston olemassaolon.
     * @param tulokset tiedosto: "tulokset.txt"
     * @return palauttaa luodun Scannerin, jos FileNotFoundException ei toteudu.
     */
    Scanner luoScanner(File tulokset){
        try {
            Scanner lukija = new Scanner(tulokset);
            return lukija;
        }
        catch(FileNotFoundException e){
            System.out.println("Tiedostoa ei löydy!");
        }
        return null;
    }
    /**
     *Muodostaa String[], jossa kolmen eri pelikoon huipputulokset.
     * Vertaa kyseisen pelikoon aiempaa huipputulosta nyt saatuun ja muuttaa,
     *  jos tarvetta.
     * @param lukija Scanner, joka syöttää "tulokset.txt":stä huipputulokset.
     * @return String[] tekstitulos, jossa huipputulokset päivitettynä.
     */
    String[] haeTulokset(Scanner lukija){
        String[] tekstitulos=new String[3];
        for(int i=0;i<3;i++)
            tekstitulos[i] = lukija.nextLine();
        if(Integer.parseInt(tekstitulos[valinta-1])>klikkaukset)
            tekstitulos[valinta-1] = Integer.toString(klikkaukset);
        return tekstitulos;
    }
    /**
     *Luo PrintWriter:in ja kirjoittaa päivitetyt huipputulokset takaisin tiedostoon
     * "tulokset.txt". Tarkistaa myös poikkeuksen, että "tulokset.txt" ei löydykkään.
     * @param tekstitulos String[], jossa huipputulokset.
     * @param tulokset tiedosto "tulokset.txt"
     */
    void tallennaTulokset(String[] tekstitulos, File tulokset){
        try{
            PrintWriter kirjotin = new PrintWriter(tulokset);
            for(int i=0;i<3;i++){
                kirjotin.println(tekstitulos[i]);
                System.out.println(tekstitulos[i]);
            }
            kirjotin.close();
        }
        catch(FileNotFoundException e){
            System.out.println("Tiedostoa ei löydy!");
        }

    }
}
