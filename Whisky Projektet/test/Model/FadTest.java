package Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FadTest {

    @BeforeEach
    void setUp() {
        int fadNr = 2;
        Fad.FadType type = Fad.FadType.BOURBON;
        double kapacitet = 32;
        Lager lager = new Lager("Sall");
        String land = "Frankrig";
        Hylde hylde = new Hylde("SH", 4);
        Reol reol = new Reol("R1");
        Fad sut = new Fad(fadNr, type, kapacitet, lager, hylde, reol, land);
        Destillering destillering = new Destillering(LocalDate.now(), "NW312");
        destillering.registrerDestilleringsData(60.0, 30);
    }
    @Test
    void påfyld() {
        String expected = "Fad nr. 2 + fadNr + er blevet påfyldt med + mængde + liter +" +
                "med alkoholprocent på + alkoholProcent +%. Nuværende indhold: + nuværendeIndhold";
    }

    @Test
    void tap() {
    }

    @Test
    void flytFad() {
    }

    @Test
    void udskrivFad() {
    }
}