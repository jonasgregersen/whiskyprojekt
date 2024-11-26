package Controller;

import Model.*;
import Storage.Storage;

import java.time.LocalDate;

public class Controller {
    public static Fad opretFad(int fadNr, Fad.FadType fadType, double kapacitet, Lager lager, Hylde hylde, Reol reol, String indkøbt) {
        Fad fad = new Fad(fadNr, fadType, kapacitet, lager, hylde, reol, indkøbt);
        Storage.addFad(fad);
        return fad;
    }
    public static Lager opretLager(String navn) {
        Lager lager = new Lager(navn);
        Storage.addLager(lager);
        return lager;
    }
    public static Destillering opretDestillering(LocalDate dato, String batch) {
        Destillering destillering = new Destillering(dato, batch);
        Storage.addDestillat(destillering);
        return destillering;
    }
}
