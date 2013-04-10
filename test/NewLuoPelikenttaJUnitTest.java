import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
public class NewLuoPelikenttaJUnitTest {

    public NewLuoPelikenttaJUnitTest() {
    }

    LuoPelikentta pieni = new LuoPelikentta();
    LuoPelikentta keskikok = new LuoPelikentta();
    LuoPelikentta iso = new LuoPelikentta();


    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        pieni.luoKentta(1);
        keskikok.luoKentta(2);
        iso.luoKentta(3);
    }

    @After
    public void tearDown() {
    }


    @Test
    public void kenttienMitatTest() {
        assertTrue(pieni.kentanKanta==8*1);
        assertTrue(keskikok.kentanKanta==8*2);
        assertTrue(iso.kentanKanta==8*3);
    }
    @Test
    public void sijoitetaanPystylaivatTest(){
        assertTrue(pieni.mahtuukoPystyna(3,1,1));
        assertTrue(keskikok.mahtuukoPystyna(3,1,1));
        assertTrue(iso.mahtuukoPystyna(3,1,1));
        assertFalse(pieni.mahtuukoPystyna(8*1+1,1,1));
        assertFalse(keskikok.mahtuukoPystyna(8*2+1,1,1));
        assertFalse(iso.mahtuukoPystyna(8*3+1,1,1));
    }
    @Test
    public void sijoitetaanVaakalaivatTest(){
        assertTrue(pieni.mahtuukoVaakana(4,1,2));
        assertTrue(keskikok.mahtuukoVaakana(4,1,2));
        assertTrue(iso.mahtuukoVaakana(4,1,2));
        assertFalse(pieni.mahtuukoVaakana(8*1+1,1,1));
        assertFalse(keskikok.mahtuukoVaakana(8*2+1,1,1));
        assertFalse(iso.mahtuukoVaakana(8*3+1,1,1));
    }
    @Test
    public void onkoYmparillaTestFalse(){
        assertFalse(pieni.onkoYmparilla(4,8));
        assertFalse(keskikok.onkoYmparilla(16,7));
        assertFalse(iso.onkoYmparilla(24,24));
    }
    @Test
    public void onkoYmparillaTestTrue(){
        pieni.moneskoLaiva=3;
        keskikok.moneskoLaiva=2;
        iso.moneskoLaiva=5;
        pieni.mahtuukoVaakana(4,1,2);
        keskikok.mahtuukoPystyna(4,1,2);
        iso.mahtuukoVaakana(4,1,2);
        assertTrue(pieni.onkoYmparilla(2,3));
        assertTrue(keskikok.onkoYmparilla(1,1));
        assertTrue(iso.onkoYmparilla(1,2));
    }
    @Test
    public void laivamaaratTest(){
        pieni.asetaLaivat(1);
        keskikok.asetaLaivat(2);
        iso.asetaLaivat(3);
        assertTrue(pieni.moneskoLaiva==(1*(1+2+3)));
        assertTrue(keskikok.moneskoLaiva==(2*(1+2+3)));
        assertTrue(iso.moneskoLaiva==(3*(1+2+3)));
    }
}
