package Controller;

import Model.*;
import Storage.Storage;

import java.time.LocalDate;
import java.util.ArrayList;

public class Controller {
    public static Fad opretFad(int fadNr, Fad.FadType fadType, double kapacitet, String indkøbt) {
        Fad fad = new Fad(fadNr, fadType, kapacitet, indkøbt);
        Storage.addFad(fad);
        return fad;
    }
    public static Lager opretLager(int id, String navn) {
        Lager lager = new Lager(id, navn);
        Storage.addLager(lager);
        return lager;
    }
    public static Destillering opretDestillering(LocalDate startDato, LocalDate slutDato, String batch) {
        Destillering destillering = new Destillering(startDato, slutDato, batch);
        Storage.addDestillat(destillering);
        return destillering;
    }
    public static ArrayList<Fad> fadKlarTilTapning() {
        ArrayList<Fad> result = new ArrayList<>();
        for (Fad f : Storage.getFade()) {
            if (f.klarTilTapning()) {
                result.add(f);
            }
        }
        return result;
    }
    public static Fad getFadMedNr(int fadNr) {
        for (Fad f : Storage.getFade()) {
            if (f.getFadNr() == fadNr) {
                return f;
            }
        }
        return null;
    }
    public static void tilførDestilleringTilFad(Destillering destillering, Fad fad, double mængde) {
        destillering.tilførFad(fad, mængde);
    }
    public static void tapFadEksisterendeTapning(Tapning tapning, Fad fad, double tapMængde) {
        if (tapning == null || fad == null || tapMængde <= 0 || fad.getNuværendeIndhold() < tapMængde) {
            throw new IllegalArgumentException("Ugyldige data eller utilstrækkelig mængde i fad.");
        }
        fad.tap(tapning, tapMængde);
    }
    public static Tapning opretTapning(String tapningsBatch, double fortyndingsMængde) {
        Tapning nyTapning = new Tapning(tapningsBatch, fortyndingsMængde);
        Storage.addTapning(nyTapning);
        return nyTapning;
    }
    public static WhiskyProdukt opretProdukt(String navn, String produktBatch, Tapning væskeBlanding, double kapacitet) {
        WhiskyProdukt produkt = new WhiskyProdukt(navn, produktBatch, væskeBlanding, kapacitet);
        Storage.addProdukt(produkt);
        return produkt;
    }
    public static void tilførTapningTilProdukt(Tapning tapning, WhiskyProdukt produkt) {
        produkt.setVæskeBlanding(tapning);
    }
}
