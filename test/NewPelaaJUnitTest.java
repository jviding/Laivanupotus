import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
public class NewPelaaJUnitTest {

    public NewPelaaJUnitTest() {
    }

    Pelaa pieniPeli = new Pelaa(1);
    Pelaa keskikokPeli = new Pelaa(2);
    Pelaa isoPeli = new Pelaa(3);

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        LuoPelikentta pelikentta = new LuoPelikentta();
        pelikentta.luoKentta(1);
        pieniPeli.kentta=pelikentta.kentta;
        pelikentta.luoKentta(2);
        keskikokPeli.kentta=pelikentta.kentta;
        pelikentta.luoKentta(3);
        isoPeli.kentta=pelikentta.kentta;
    }

    @After
    public void tearDown() {
    }


    @Test
    public void osukoTestFalse() {
        assertFalse(pieniPeli.osuko(3,6));
        assertFalse(keskikokPeli.osuko(1,9));
        assertFalse(isoPeli.osuko(15,3));
    }
    @Test
    public void osukoTestTrue(){
        pieniPeli.kentta[1][2]=1;
        keskikokPeli.kentta[3][7]=8;
        isoPeli.kentta[10][12]=3;
        assertTrue(pieniPeli.osuko(1,2));
        assertTrue(keskikokPeli.osuko(3,7));
        assertTrue(isoPeli.osuko(10,12));
    }
    @Test
    public void onkoJaljellaTest(){
        int[] esimTilanne = new int[10];
        pieniPeli.laivojenTilanne=esimTilanne;
        assertFalse(pieniPeli.onkoJaljella());
        esimTilanne[3]=3;
        keskikokPeli.laivojenTilanne=esimTilanne;
        assertTrue(keskikokPeli.onkoJaljella());
        esimTilanne[1]=4;
        isoPeli.laivojenTilanne=esimTilanne;
        assertTrue(isoPeli.onkoJaljella());
    }
    @Test
    public void upposikoTest(){
        int[] esimTilanne = new int[10];
        esimTilanne[1]=1;
        pieniPeli.laivojenTilanne=esimTilanne;
        assertTrue(pieniPeli.upposiko(1));
        esimTilanne[3]=3;
        keskikokPeli.laivojenTilanne=esimTilanne;
        assertFalse(keskikokPeli.upposiko(3));
        esimTilanne[1]=4;
        isoPeli.laivojenTilanne=esimTilanne;
        assertFalse(isoPeli.upposiko(1));
    }
}