package Test;

import Controller.Controller;
import Model.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FadTest {
    private Fad fad;
    private Placering placering;
    private Hylde hylde;
    private Reol reol;
    private Lager lager;
    private Destillering destillering;

    @BeforeEach
    void setUp() {
        lager = new Lager(1, "Lager 1");
        reol = new Reol("101");
        hylde = new Hylde("201", 4);
        placering = new Placering(lager, reol, hylde);
        destillering = new Destillering(LocalDate.of(2016, 6, 12), LocalDate.of(2020,9,14), "BatchA");


        fad = new Fad(1, Fad.FadType.BOURBON, 200.0, "Kentucky");
    }
    @Test
    void testConstructor() {
        int fadNr = 10;
        Fad.FadType fadType = Fad.FadType.OLOROSO;
        double kapacitet = 200;
        String indkøbt = "England";
        Fad fad1 = Controller.opretFad(fadNr, fadType, kapacitet, indkøbt);

        assertEquals(fadNr, fad1.getFadNr(), "FadNr skal være 10.");
        assertEquals(fadType, fad1.getFadType(), "Fadtypen skal være OLOROSO.");
        assertEquals(kapacitet, fad1.getKapacitet(), "Fadkapaciteten skal være 200.");
        assertEquals(indkøbt, fad1.getIndkøbtFra(), "Fadet skal være indkøbt fra England.");

    }

    @Test
    void testPåfyldGyldigData() {
        destillering.registrerDestilleringsData(65, 150);
        Controller.tilførDestillatTilFad(destillering, fad, 150);

        assertEquals(150.0, fad.getNuværendeIndhold(), "Indholdet i fadet skal være 150 liter.");
        assertEquals(65.0, fad.beregnAlkoholProcent(), "Alkoholprocenten skal være 65%.");
        assertNotNull(fad.getHistorik(), "Historik skal indeholde en ny hændelse.");
    }

    @Test
    void testPåfyldOverKapacitet() {
        destillering.registrerDestilleringsData(65, 250);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Controller.tilførDestillatTilFad(destillering, fad, 250);
        });
        assertEquals("Fadet kan ikke rumme den angivne mængde.", exception.getMessage());
    }

    @Test
    void testPåfyldNegativMængde() {
        destillering.registrerDestilleringsData(65, 250);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Controller.tilførDestillatTilFad(destillering, fad, -250);
        });
        assertEquals("Påfyldningsmængden skal være over 0.", exception.getMessage());
    }

    @Test
    void testPåfyldNulMængde() {
        destillering.registrerDestilleringsData(65, 250);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Controller.tilførDestillatTilFad(destillering, fad, 0);
        });
        assertEquals("Påfyldningsmængden skal være over 0.", exception.getMessage());
    }

    @Test
    void testTap() {
        Tapning tapning = new Tapning("TP101", 0);
        destillering.registrerDestilleringsData(65,150);
        Controller.tilførDestillatTilFad(destillering,fad,150);
        fad.setDatoPåfyldning(LocalDate.of(2020,9,14));
        fad.tap(tapning,50.0);

        assertEquals(100.0, fad.getNuværendeIndhold(), "Indholdet efter tapning skal være 100 liter.");
    }

    @Test
    void testTapForMeget() {
        Tapning tapning = new Tapning("TP101", 0);
        destillering.registrerDestilleringsData(65,150);
        Controller.tilførDestillatTilFad(destillering,fad,150);
        fad.setDatoPåfyldning(LocalDate.of(2020,9,14));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            fad.tap(tapning,200.0);
        });
        assertEquals("Tapmængden er større end fadets indhold.", exception.getMessage());
    }

    @Test
    void testTapNegativ() {
        Tapning tapning = new Tapning("TP101", 0);
        destillering.registrerDestilleringsData(65,150);
        Controller.tilførDestillatTilFad(destillering,fad,150);
        fad.setDatoPåfyldning(LocalDate.of(2020,9,14));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            fad.tap(tapning,-20.0);
        });
        assertEquals("Tapmængden skal være over 0.", exception.getMessage());
    }

    @Test
    void testBeregnTapningPrDestillat() {
        Tapning tapning = new Tapning("TP101", 0);
        destillering.registrerDestilleringsData(65,100);
        Destillering destillering1 = Controller.opretDestillering(LocalDate.now(), LocalDate.now(), "123");
        destillering1.registrerDestilleringsData(70, 100);
        Controller.tilførDestillatTilFad(destillering,fad,100);
        Controller.tilførDestillatTilFad(destillering1, fad, 100);
        fad.setDatoPåfyldning(LocalDate.of(2020,9,14));
        double destillat1Mængde = fad.beregnTapningPrDestillat(100).get(destillering);
        double destillat2Mængde = fad.beregnTapningPrDestillat(100).get(destillering1);

        assertEquals(50.0, destillat1Mængde, "Der skal være tappet 50 fra destillat1.");
        assertEquals(50.0, destillat2Mængde, "Der skal være tappet 50 fra destillat2.");
    }

    @Test
    void testFlytPlacering() {
        Lager nytLager = new Lager(2, "Lager 2");
        Reol nyReol = new Reol("Reol B");
        Hylde nyHylde = new Hylde("202", 6);
        Hylde gammelHylde = hylde;
        Placering nyPlacering = new Placering(nytLager, nyReol, nyHylde);

        fad.setPlacering(placering);
        assertEquals(fad, hylde.getFade().get(0), "Test for fadet er registreret i orginal placing.");

        fad.setPlacering(nyPlacering);

        assertEquals(nytLager, fad.getPlacering().getLager(), "Fadet skal være flyttet til det nye lager.");
        assertEquals(nyReol, fad.getPlacering().getReol(), "Fadet skal være flyttet til den nye reol.");
        assertEquals(nyHylde, fad.getPlacering().getHylde(), "Fadet skal være flyttet til den nye hylde.");
        assertNotNull(fad.getHistorik(), "Historikken skal være opdateret efter hændelsen.");
        assertTrue(gammelHylde.getFade().isEmpty(), "Den gamle hylde skal være tom efter flytningen.");
    }

    @Test
    void testKlarTilTapning() {
        fad.setDatoPåfyldning(LocalDate.now().minusYears(4));
        assertTrue(fad.klarTilTapning(), "Fadet skal være klar til tapning, da det har været påfyldt i mere end 3 år.");
    }

    @Test
    void testIkkeKlarTilTapning() {
        fad.setDatoPåfyldning(LocalDate.now().minusYears(2));
        assertFalse(fad.klarTilTapning(), "Fadet skal ikke være klar til tapning, da det ikke har været påfyldt i 3 år.");
    }
    @Test
    void testBeregnDestilleringerVedTapning() {
        Destillering destillering1 = new Destillering(LocalDate.of(2016, 6, 12), LocalDate.of(2019, 8, 13), "Batch A");
        Destillering destillering2 = new Destillering(LocalDate.of(2012, 6, 12), LocalDate.of(2016, 6,9), "Batch B");
        Fad fad1 = new Fad(1, Fad.FadType.BOURBON, 200.0, "Sall");
        Tapning tapning = new Tapning("TP101", 0);
        destillering1.registrerDestilleringsData(65, 120);
        destillering2.registrerDestilleringsData(65, 80);
        Controller.tilførDestillatTilFad(destillering1, fad1, 120);
        Controller.tilførDestillatTilFad(destillering2, fad1, 80);
        fad1.setDatoPåfyldning(LocalDate.of(2019,5,1));


        double mængdeTappet = 100.0;
        Map<Destillering, Double> tappetMængde = fad1.tap(tapning, mængdeTappet);

        assertEquals(60.0, tappetMængde.get(destillering1), "Batch A's tappede mængde skal være 60 liter.");
        assertEquals(40.0, tappetMængde.get(destillering2), "Batch B's tappede mængde skal være 40 liter.");
        assertEquals(60.0, fad1.getDestillater().get(destillering1), "Batch A's resterende mængde skal være 60 liter.");
        assertEquals(40.0, fad1.getDestillater().get(destillering2), "Batch B's resterende mængde skal være 40 liter.");
        assertEquals(100.0, fad1.getNuværendeIndhold(), "Fadets samlede indhold skal være 100 liter.");
    }
    @Test
    void testBeregnAlkoholProcent_normaltForløb() {
        Destillering destillering1 = new Destillering(LocalDate.of(2016, 6, 12), LocalDate.of(2019, 8, 13), "Batch A");
        Destillering destillering2 = new Destillering(LocalDate.of(2012, 6, 12), LocalDate.of(2016, 6, 9), "Batch B");
        Destillering destillering3 = new Destillering(LocalDate.now(), LocalDate.now(), "Batch C");
        Fad fad1 = new Fad(1, Fad.FadType.NY, 300, "null");

        destillering1.registrerDestilleringsData(85, 100);
        destillering2.registrerDestilleringsData(70, 100);
        destillering3.registrerDestilleringsData(40, 100);
        destillering1.tilførFad(fad1, 50);
        destillering2.tilførFad(fad1, 100);
        destillering3.tilførFad(fad1, 100);

        double expected = 61;
        double actual = fad1.beregnAlkoholProcent();

        assertEquals(expected, actual, "Alkoholprocenten i blandingen skal være 61 procent.");
    }
    @Test
    void testBeregnAlkoholProcent_AlkoholProcentPaa0() {
        Destillering destillering1 = new Destillering(LocalDate.now(), LocalDate.now(), "test123");
        Destillering destillering2 = new Destillering(LocalDate.now(), LocalDate.now(), "test124");
        Destillering destillering3 = new Destillering(LocalDate.now(), LocalDate.now(), "test125");
        Fad fad1 = new Fad(1, Fad.FadType.NY, 300, "null");

        destillering1.registrerDestilleringsData(0, 100);
        destillering2.registrerDestilleringsData(0,50);
        destillering3.registrerDestilleringsData(0,150);
        destillering1.tilførFad(fad1, 100);
        destillering2.tilførFad(fad1, 50);
        destillering3.tilførFad(fad1, 150);

        double expected = 0;
        double actual = fad1.beregnAlkoholProcent();

        assertEquals(expected, actual, "Alkoholprocenten i fadet skal være 0%.");

    }
    @Test
    void testBeregnAlkoholProcent_AlkoholProcentPaa100() {
        Destillering destillering1 = new Destillering(LocalDate.now(), LocalDate.now(), "test123");
        Destillering destillering2 = new Destillering(LocalDate.now(), LocalDate.now(), "test124");
        Destillering destillering3 = new Destillering(LocalDate.now(), LocalDate.now(), "test125");
        Fad fad1 = new Fad(1, Fad.FadType.NY, 300, "null");

        destillering1.registrerDestilleringsData(100, 100);
        destillering2.registrerDestilleringsData(100, 50);
        destillering3.registrerDestilleringsData(100, 150);
        destillering1.tilførFad(fad1, 100);
        destillering2.tilførFad(fad1, 50);
        destillering3.tilførFad(fad1, 150);

        double expected = 100;
        double actual = fad1.beregnAlkoholProcent();

        assertEquals(expected, actual, "Alkoholprocenten i fadet skal være 100%.");
    }

    @Test
    void testBeregnAlkoholProcent_KunEnDestillering() {
        Destillering destillering1 = new Destillering(LocalDate.now(), LocalDate.now(), "test123");
        Fad fad1 = new Fad(1, Fad.FadType.NY, 300, "null");

        destillering1.registrerDestilleringsData(67, 100);
        destillering1.tilførFad(fad1, 100);

        double expected = 67;
        double actual = fad1.beregnAlkoholProcent();

        assertEquals(expected, actual, "Alkoholprocenten i fadet skal være 67%.");
    }
    @Test
    void fjernDestillering() {
        Destillering destillering1 = new Destillering(LocalDate.now(), LocalDate.now(), "NMW123");
        Destillering destillering2 = new Destillering(LocalDate.now(), LocalDate.now(), "NWM124");
        Fad fad1 = new Fad(1, Fad.FadType.NY, 300, "null");

        destillering1.registrerDestilleringsData(65, 100);
        destillering2.registrerDestilleringsData(70, 100);
        Controller.tilførDestillatTilFad(destillering1, fad1, 100);
        Controller.tilførDestillatTilFad(destillering2, fad1, 100);

        assertEquals(67.5, fad1.beregnAlkoholProcent());
        assertEquals(200, fad1.getNuværendeIndhold());

        fad1.fjernDestillat(destillering1);

        assertEquals(70, fad1.beregnAlkoholProcent());
        assertEquals(100, fad1.getNuværendeIndhold());
    }
    @Test
    void tømFad() {
        Destillering destillering1 = new Destillering(LocalDate.now(), LocalDate.now(), "NWM123");
        destillering1.registrerDestilleringsData(65, 200);
        destillering1.tilførFad(fad, 200);

        assertFalse(destillering1.getTilførteFad().isEmpty(), "Destilleringen skal have 1 fad i deres tilførte fad liste.");
        assertFalse(fad.getDestillater().isEmpty(), "Fadet skal indeholde 1 destillat.");
        assertEquals(200, fad.getNuværendeIndhold());

        fad.tømFad();

        assertTrue(destillering1.getTilførteFad().isEmpty(), "Listen over fad tilført skal være tom.");
        assertTrue(fad.getDestillater().isEmpty(), "Fadet skal være tom for destillater.");
        assertEquals(0, fad.getNuværendeIndhold());
    }
    @Test
    void setDatoPåfyldning() {
        Destillering destillering1 = Controller.opretDestillering(LocalDate.now(), LocalDate.now(), "123");
        destillering1.registrerDestilleringsData(67, 100);
        Fad fad1 = new Fad(1, Fad.FadType.BOURBON, 100, "Frankrig");
        Controller.tilførDestillatTilFad(destillering1, fad1, 100);

        assertEquals(LocalDate.now(), fad1.getDatoPåfyldning());

        fad1.setDatoPåfyldning(LocalDate.of(2021, 8, 17));

        assertEquals(LocalDate.of(2021,8,17), fad1.getDatoPåfyldning());
    }
}
