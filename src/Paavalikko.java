import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
public class Paavalikko extends JFrame {
    /**
     * Suorittaa luoPaneelit();.
     */
    Paavalikko(){
        luoPaneelit();
    }
    /**
     * Luo pelikoonvalintanapeille ActionListenerit.
     * Painamalla nappia, suoritetaan napinPainallus(i+1);,
     * jossa pienipeli: i=0, keskikokoinen: i=1, iso: i=2.
     */
    void napeilleTapahtumat(){
        for(int i=0;i<3;i++){
            pelienKoot[i].addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent tapahtuma){
                        for(int i=0;i<3;i++){
                            if(pelienKoot[i]==tapahtuma.getSource()){
                                napinPainallus(i+1);
                            }
                        }
                    }
                }
            );
        }
    }
    /**
     * Suorittaa luokan upotusikkuna ja avaa sen GUI:n.
     * @param i valittu pelikoko
     */
    void napinPainallus(int i){
        Upotusikkuna pelattava = new Upotusikkuna(i);
        pelattava.setTitle("Laivanupotus");
        pelattava.pack();
        pelattava.setVisible(true);
    }
    /**
     * Sisältää JButtonit pelien koista.
     */
    JButton[] pelienKoot;
    /**
     * Muodostaa JButton[3], jossa eri pelien koot nappeina; pieni, keskikokoinen ja suuri.
     * Suorittaa myös napeilleTapahtumat();.
     * @return pelikoonvalintanapit
     */
    JButton[] vaihtoehdot(){
        pelienKoot = new JButton[3];
        pelienKoot[0] = new JButton("Pieni");
        pelienKoot[1] = new JButton("Keskikokoinen");
        pelienKoot[2] = new JButton("Suuri");
        napeilleTapahtumat();
        return pelienKoot;
    }
    /**
     * Muodostaa JPanelin, jossa JLabel("Valitse pelin koko:") ja sen alla pelikoonvalintanapit.
     * @return JPanel valinnat.
     */
    JPanel getValinnat(){
        JLabel tekstia = new JLabel("Valitse pelin koko:");
        JPanel valinnat = new JPanel(new GridLayout(4,1));
        valinnat.add(tekstia);
        JButton[] valintanapit = vaihtoehdot();
        for(int a=0;a<3;a++)
            valinnat.add(valintanapit[a]);
        return valinnat;
    }
    /**
     * Asettaa huipputulokset-napille tapahtumaksi JOptionPane:n avaamisen.
     * Suorittaa myös haeTulokset(luoScanner(tiedosto));, jossa tiedosto on "tulokset.txt".
     * Kertoo haetut huipputulokset avatussa JOptionPane:ssa.
     * @param nappi "Huipputulokset"-nappi
     */
    void tulosnapilleTapahtuma(JButton nappi){
        nappi.addActionListener(
            new ActionListener(){
                public void actionPerformed(ActionEvent happening){
                    File tiedosto = new File("tulokset.txt");
                    String[] tulokset = haeTulokset(luoScanner(tiedosto));
                    JOptionPane.showMessageDialog(null,"Pieni: "+tulokset[0]+"\nKeskikokoinen: "+tulokset[1]+
                            "\nSuuri: "+tulokset[2]);
                }
            }
        );
    }
    /**
     * Luo JPanel:in, jossa tyhjän JLabelin alla on "Huipputulokset" JButton ja
     *  suorittaa tulosnapilleTapahtuma(tulosnappi);.
     * @return JPanel, jonka luo.
     */
    JPanel getHuipputulos(){
        JButton tulosnappi = new JButton("Huipputulokset");
        JPanel tulospaneeli = new JPanel(new GridLayout(2,1));
        JLabel tekstia=new JLabel("");
        tulospaneeli.add(tekstia);
        tulospaneeli.add(tulosnappi);
        tulosnapilleTapahtuma(tulosnappi);
        return tulospaneeli;
    }
    /**
     * Asettaa JPanel:it GUI:hin.
     */
    void luoPaneelit(){
        this.setLayout(new BorderLayout());
        this.add("Center", getValinnat());
        this.add("South", getHuipputulos());
    }
    /**
     *Luo Scannerin, joka syöttää tiedoston "tulokset.txt" sisältöä.
     * @param tulokset tiedosto "tulokset.txt"
     * @return Jos tulokset.txt löytyy, niin returnaa Scanner:in, joka syöttää
     * kyseisen tiedoston sisältöä.
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
     * Hakee "tulokset.txt":n sisältämät huipputulokset ja kirjoittaa ne String[] tekstitulos:iin talteen.
-     * @param lukija Scanner, joka syöttää "tulokset.txt":n sisältöä.
     * @return String[] tekstitulos, joka sisältää huipputulokset.
     */
    String[] haeTulokset(Scanner lukija){
        String[] tekstitulos=new String[3];
        for(int i=0;i<3;i++)
            tekstitulos[i] = lukija.nextLine();
        return tekstitulos;
    }
}
