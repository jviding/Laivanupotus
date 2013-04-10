import javax.swing.*;
public class Laivanupotus extends JFrame {
    /**
     * Käynnistää pelin luokalla Paavalikko ja avaa täten päävalikon GUI:n.
     * Kun ikkuna suljetaan, niin koko ohjelman suoritus lakkaa.
     * @param args
     */
    public static void main(String[] args) {
        Paavalikko paaikkuna = new Paavalikko();
        paaikkuna.setTitle("Laivanupotus");
        paaikkuna.pack();
        paaikkuna.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        paaikkuna.setVisible(true);
    }
}