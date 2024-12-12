package Controller;

import Model.*;
import Storage.Storage;

import java.time.LocalDate;

public class Controller {
    public static Fad opretFad(int fadNr, Fad.FadType fadType, double kapacitet, String indkøbt) throws IllegalArgumentException {
        try {
            Fad fad = new Fad(fadNr, fadType, kapacitet, indkøbt);
            Storage.addFad(fad);
            return fad;
        } catch (Exception e) {
            throw e;
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

    public static Destillering opretDestillering(LocalDate startDato, LocalDate slutDato, String spiritBatch) {
        Destillering destillering = new Destillering(startDato, slutDato, spiritBatch);
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

    public static void tilførDestillatTilFad(Destillering destillat, Fad fad, double mængde) throws IllegalArgumentException {
        try {
            destillat.tilførFad(fad, mængde);
        } catch (Exception e) {
            throw e;
        }
    }

    public static void tapFad(Tapning tapning, Fad fad, double tapMængde) {
        if (tapning == null || fad == null || tapMængde <= 0 || fad.getNuværendeIndhold() - tapMængde < 0) {
            throw new IllegalArgumentException("Ugyldige data eller utilstrækkelig mængde i fad.");
        }
        fad.tap(tapning, tapMængde);
    }

    public static Tapning opretTapning(String tapningsBatch, double fortyndingsMængde) {
        Tapning nyTapning = new Tapning(tapningsBatch, fortyndingsMængde);
        Storage.addTapning(nyTapning);
        return nyTapning;
    }

    public static WhiskyProdukt opretProdukt(String navn, String produktBatch, Tapning tapning, double kapacitet) {
        WhiskyProdukt produkt = new WhiskyProdukt(navn, produktBatch, tapning, kapacitet);
        Storage.addProdukt(produkt);
        return produkt;
    }

    public static void tilførTapningTilProdukt(Tapning tapning, WhiskyProdukt produkt) {
        produkt.setVæskeBlanding(tapning);
        tapning.tilførProdukt(produkt);
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
