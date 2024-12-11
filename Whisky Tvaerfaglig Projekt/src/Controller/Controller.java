package Controller;

import GUI.RåvareLagerPane;
import Model.*;
import Storage.Storage;

import java.time.LocalDate;
import java.util.ArrayList;

public class Controller {
    public static Fad opretFad(int fadNr, Fad.FadType fadType, double kapacitet, String indkøbt) throws IllegalArgumentException {
        try {
            Fad fad = new Fad(fadNr, fadType, kapacitet, indkøbt);
            Storage.addFad(fad);
            return fad;
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static void fjernFad(Fad fad) throws IllegalArgumentException {
        if (Storage.getFade().contains(fad)) {
            Storage.removeFad(fad);
        } else {
            throw new IllegalArgumentException("Fadet findes ikke i storage.");
        }
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

    public static void fjernDestillering(Destillering destillering) {
        try {
            if (Storage.getDestillater().contains(destillering)) {
                Storage.removeDestillat(destillering);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Destillering findes ikke i storage.");
        }
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

    public static void tilførDestilleringTilFad(Destillering destillering, Fad fad, double mængde) throws IllegalArgumentException {
        try {
            destillering.tilførFad(fad, mængde);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
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

    public static void setProduktPlacering(WhiskyProdukt p, Lager l) {
        l.tilføjProdukt(p);
    }

    public static Råvare opretRåvare(Lager lager, Råvare.KornSort kornSort, String maltBatch, double mængde) {
        Råvare råvare = new Råvare(kornSort, maltBatch);
        lager.tilføjRåvare(råvare, mængde);
        return råvare;
    }

    public static void registrerDestilleringsData(Destillering destillering, double mængde, double alkoholProcent) {
        destillering.registrerDestilleringsData(alkoholProcent, mængde);
    }
    public static void setFadPlacering(Fad fad, Placering placering) {
        fad.setPlacering(placering);
    }

}
