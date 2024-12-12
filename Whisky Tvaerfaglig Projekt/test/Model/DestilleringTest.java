package Model;

import Controller.Controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DestilleringTest {
    private Destillering destillering;

    @BeforeEach
    void setUp() {
        destillering = new Destillering(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 10), "Batch001");
    }

    @Test
    void registrerDestilleringsData() {
        destillering.registrerDestilleringsData(63.0, 200.0);

        assertEquals(63.0, destillering.getAlkoholProcent(), "Destilleringen skal indeholde 65% alkohol.");
        assertEquals(200.0, destillering.getMængde(), "Destilleringen skal indeholde 200 enheder af væsken.");

        assertThrows(IllegalArgumentException.class, () -> destillering.registrerDestilleringsData(-10, 200.0), "Der skal ikke kunne være -10% alkohol i destilleringen.");
        assertThrows(IllegalArgumentException.class, () -> destillering.registrerDestilleringsData(50, -100.0), "Der skal ikke kunne være en negativ mængde af destilleringen.");
    }

    @Test
    void tilførFad() {
        Fad fad = new Fad(1, Fad.FadType.BOURBON, 300, "null");
        destillering.registrerDestilleringsData(65, 200);
        Controller.tilførDestillatTilFad(destillering, fad, 100);
        Fad expected = fad;
        Fad actual = destillering.getTilførteFad().get(0);

        assertEquals(expected, actual, "Tilførte fad listen skal indeholde destilleringen.");

    }

    @Test
    void tilføjRåvare() {
        Lager lager = new Lager(1, "Lager A");
        Råvare råvare = new Råvare(Råvare.KornSort.EVERGREEN, "Batch001");
        lager.tilføjRåvare(råvare, 500);

        destillering.tilføjRåvare(lager, råvare, 200);

        assertEquals("Batch001", destillering.getMaltBatch());
        assertEquals(300, lager.mængdePåBatch("Batch001"));
        assertEquals(200, destillering.getRåvareMængde());

        assertThrows(IllegalArgumentException.class, () -> destillering.tilføjRåvare(lager, råvare, 1000), "Beholdningen af råvaren overstiges.");
    }

    @Test
    void getTilførteFad() {
    }

    @Test
    void getHistorik() {
    }
}