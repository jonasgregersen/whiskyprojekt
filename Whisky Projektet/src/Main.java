import Controller.Controller;
import Model.*;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Lager sall = Controller.opretLager("Sall");
        Reol r1 = sall.opretReol("R1");
        Hylde h1 = r1.opretHylde("H1", 4);
        Fad fad = Controller.opretFad(1, Fad.FadType.NY, 32, sall, h1, r1, "Frankrig");
        Destillering destillering = new Destillering(LocalDate.now(), "NMW231");
        destillering.afslut();
        destillering.registrerDestilleringsData(68, 300);
        fad.påfyld(destillering, 30, 70);
        fad.setDatoPåfyldning(LocalDate.of(2021,11,26));
        fad.tap(10);
        fad.udskrivHistorik();
    }
}
