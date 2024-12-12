package Model;

import Controller.Controller;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
class TapningTest {
    @Test
    void testKonstruktør() {
        Tapning tapning = new Tapning("Batch001", 10.0);
        assertEquals("Batch001", tapning.getTapningsBatch());
        assertEquals(10.0, tapning.getFortyndingsMængde(), 0.001);
        assertEquals(10.0, tapning.getTotalMængde(), 0.001);
        assertTrue(tapning.getFadVæske().isEmpty());
    }

    @Test
    void testTilføjFad_NormaltTilfælde() {
        Tapning tapning = new Tapning("Batch002", 5.0);
        Fad fad = new Fad(1, Fad.FadType.BOURBON, 200.0, "Kentucky");
        fad.påfyld(new Destillering(LocalDate.now(), LocalDate.now(), "Batch001"), 50.0);

        tapning.tilføjFad(fad, 20.0);

        assertEquals(25.0, tapning.getTotalMængde(), 0.001);
        assertEquals(1, tapning.getFadVæske().size());
        assertEquals(20.0, tapning.getFadVæske().get(fad), 0.001);
    }

    @Test
    void testTilføjFad_FadNull() {
        Tapning tapning = new Tapning("Batch003", 5.0);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> tapning.tilføjFad(null, 10.0));
        assertEquals("Fadet skal være angivet.", exception.getMessage());
    }

    @Test
    void testBeregnAlkoholProcent() {
        Tapning tapning = new Tapning("Batch004", 10.0);
        Fad fad1 = new Fad(1, Fad.FadType.BOURBON, 200.0, "Kentucky");
        fad1.påfyld(new Destillering(LocalDate.now(), LocalDate.now(), "Batch001"), 50.0);
        Fad fad2 = new Fad(2, Fad.FadType.SHERRY, 150.0, "Spanien");
        fad2.påfyld(new Destillering(LocalDate.now(), LocalDate.now(), "Batch002"), 30.0);

        tapning.tilføjFad(fad1, 20.0);
        tapning.tilføjFad(fad2, 10.0);

        double forventetAlkoholProcent = ((20.0 * fad1.beregnAlkoholProcent()) + (10.0 * fad2.beregnAlkoholProcent())) / 30.0;
        assertEquals(forventetAlkoholProcent, tapning.beregnAlkoholProcent());
    }

    @Test
    void testBeregnReduceretVæske() {
        Tapning tapning = new Tapning("Batch005", 10.0);
        Fad fad = new Fad(1, Fad.FadType.BOURBON, 200.0, "Kentucky");
        fad.påfyld(new Destillering(LocalDate.now(), LocalDate.now(), "Batch001"), 50.0);
        tapning.tilføjFad(fad, 20.0);

        HashMap<Fad, Double> reduceretVæske = tapning.beregnReduceretVæske(10.0);
        double fortyndingsMængde = 10;
        double fadVæske = 20;
        double totalMængde = 30;
        double reduceretMængde = 10;
        double forventetReduction = fadVæske - (fadVæske / totalMængde) * reduceretMængde;

        assertEquals(forventetReduction, reduceretVæske.get(fad));
    }

    @Test
    void testTilførProdukt() {
        Tapning tapning = new Tapning("Batch006", 10.0);
        Fad fad = new Fad(1, Fad.FadType.BOURBON, 200.0, "Kentucky");
        fad.påfyld(new Destillering(LocalDate.now(), LocalDate.now(), "Batch001"), 50.0);
        tapning.tilføjFad(fad, 20.0);

        WhiskyProdukt produkt = new WhiskyProdukt("Whisky001", "adada",tapning,15.0);
        tapning.tilførProdukt(produkt);

        assertEquals(15.0, produkt.getIndholdsKapacitet(), 0.001);
        assertEquals(15.0, tapning.getTotalMængde(), 0.001);
    }

    @Test
    void testTilførProdukt_ManglendeVæske() {
        Tapning tapning = new Tapning("Batch007", 9.0);
        Fad fad = new Fad(1, Fad.FadType.BOURBON, 200.0, "Kentucky");
        Destillering destillering = Controller.opretDestillering(LocalDate.now(),LocalDate.now(), "123");
        destillering.registrerDestilleringsData(65,100);
        fad.påfyld(destillering, 9);
        fad.setDatoPåfyldning(LocalDate.now().minusYears(4));
        fad.tap(tapning, 1);
        WhiskyProdukt produkt = new WhiskyProdukt("Whisky002", "ada", tapning, 11);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> tapning.tilførProdukt(produkt));
        assertEquals("Der er ikke nok væske i tapning.", exception.getMessage());
    }
  
}