package Test;

import Controller.Controller;
import Model.Fad;
import Model.Destillering;
import Model.Placering;
import Model.Hylde;
import Model.Reol;
import Model.Lager;

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
        destillering = new Destillering(LocalDate.of(2020,9,14), "BatchA");

        fad = new Fad(1, Fad.FadType.BOURBON, 200.0, "Kentucky");
        fad.setPlacering(placering);
    }

    @Test
    void testPåfyld() {
        destillering.registrerDestilleringsData(65, 150);
        Controller.tilførDestilleringTilFad(destillering, fad, 150);

        assertEquals(150.0, fad.getNuværendeIndhold(), "Indholdet i fadet skal være 150 liter.");
        assertEquals(65.0, fad.getAlkoholProcent(), "Alkoholprocenten skal være 65%.");
        assertNotNull(fad.getHistorik(), "Historik skal indeholde en ny hændelse.");
    }

    @Test
    void testPåfyldOverKapacitet() {
        destillering.registrerDestilleringsData(65, 250);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Controller.tilførDestilleringTilFad(destillering, fad, 250);
        });
        assertEquals("Fadet kan ikke rumme mere.", exception.getMessage());
    }

    @Test
    void testTap() {
        destillering.registrerDestilleringsData(65,150);
        Controller.tilførDestilleringTilFad(destillering,fad,150);
        fad.setDatoPåfyldning(LocalDate.of(2020,9,14));
        fad.tap(50.0);

        assertEquals(100.0, fad.getNuværendeIndhold(), "Indholdet efter tapning skal være 100 liter.");
    }

    @Test
    void testTapForMeget() {
        destillering.registrerDestilleringsData(65,150);
        Controller.tilførDestilleringTilFad(destillering,fad,150);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            fad.tap(200.0);
        });
        assertEquals("Tapmængden er større end fadets indhold.", exception.getMessage());
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
        Destillering destillering1 = new Destillering(LocalDate.of(2019, 8, 13), "Batch A");
        Destillering destillering2 = new Destillering(LocalDate.of(2016, 6,9), "Batch B");

        Fad fad1 = new Fad(1, Fad.FadType.BOURBON, 200.0, "Sall");
        fad1.setPlacering(placering);
        destillering1.registrerDestilleringsData(65, 120);
        destillering2.registrerDestilleringsData(65, 80);
        Controller.tilførDestilleringTilFad(destillering1, fad1, 120);
        Controller.tilførDestilleringTilFad(destillering2, fad1, 80);


        double mængdeTappet = 100.0;
        Map<Destillering, Double> tappetMængde = fad1.tap(mængdeTappet);

        assertEquals(60.0, tappetMængde.get(destillering1), "Batch A's tappede mængde skal være 60 liter.");
        assertEquals(40.0, tappetMængde.get(destillering2), "Batch B's tappede mængde skal være 40 liter.");
        assertEquals(60.0, fad1.getDestillater().get(destillering1), "Batch A's resterende mængde skal være 60 liter.");
        assertEquals(40.0, fad1.getDestillater().get(destillering2), "Batch B's resterende mængde skal være 40 liter.");
        assertEquals(100.0, fad1.getNuværendeIndhold(), "Fadets samlede indhold skal være 100 liter.");
    }
}
