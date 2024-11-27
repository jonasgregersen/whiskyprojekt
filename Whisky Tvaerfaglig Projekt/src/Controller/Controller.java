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
    public static Destillering opretDestillering(LocalDate dato, String batch) {
        Destillering destillering = new Destillering(dato, batch);
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
}
