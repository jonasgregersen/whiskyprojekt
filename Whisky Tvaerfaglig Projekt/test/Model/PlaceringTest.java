package Test;

import Model.Lager;
import Model.Reol;
import Model.Hylde;
import Model.Placering;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlaceringTest {
    private Lager lager;
    private Reol reol;
    private Hylde hylde;
    private Placering placering;

    @BeforeEach
    void setUp() {
        // Opret mock-data til Lager, Reol og Hylde
        lager = new Lager(1, "Lager 1");
        reol = new Reol("101");
        hylde = new Hylde("201", 10);

        // Initialiser Placering
        placering = new Placering(lager, reol, hylde);
    }

    @Test
    void testPlaceringAttributes() {
        // Test at attributterne er korrekt initialiseret
        assertEquals(lager, placering.getLager(), "Placeringen skal referere til det korrekte lager.");
        assertEquals(reol, placering.getReol(), "Placeringen skal referere til den korrekte reol.");
        assertEquals(hylde, placering.getHylde(), "Placeringen skal referere til den korrekte hylde.");
    }

    @Test
    void testToString() {
        // Test toString-metoden
        String expected = "Placering: Lager [Lager ID: 1, Navn: Lager 1], Reol [Reol ID: 101], Hylde [Hylde ID: 201]";
        assertEquals(expected, placering.toString(), "toString skal returnere en korrekt beskrivelse af placeringen.");
    }

    @Test
    void testUpdatePlacering() {
        // Opret en ny placering
        Lager nytLager = new Lager(2, "Lager 2");
        Reol nyReol = new Reol("102");
        Hylde nyHylde = new Hylde("202", 10);

        // Opdater placeringens komponenter
        Placering nyPlacering = new Placering(nytLager, nyReol, nyHylde);

        // Test at den nye placering er korrekt
        assertEquals(nytLager, nyPlacering.getLager(), "Den nye placering skal referere til det nye lager.");
        assertEquals(nyReol, nyPlacering.getReol(), "Den nye placering skal referere til den nye reol.");
        assertEquals(nyHylde, nyPlacering.getHylde(), "Den nye placering skal referere til den nye hylde.");
    }

    @Test
    void testNullAttributes() {
        // Test oprettelse med null-attributter
        Placering nullPlacering = new Placering(null, null, null);

        assertNull(nullPlacering.getLager(), "Lager i placeringen skal være null.");
        assertNull(nullPlacering.getReol(), "Reol i placeringen skal være null.");
        assertNull(nullPlacering.getHylde(), "Hylde i placeringen skal være null.");
    }
}
