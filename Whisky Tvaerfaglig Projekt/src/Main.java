import Controller.Controller;
import Model.*;
import Storage.Storage;
import javafx.scene.control.Alert;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Destillering d1 = Controller.opretDestillering(LocalDate.now(), LocalDate.now(), "NM88P");
        d1.registrerDestilleringsData(65,100);
        Tapning t1 = Controller.opretTapning("TP101", 0);
        System.out.println(Storage.getDestillater());
        Fad fad1 = Controller.opretFad(1, Fad.FadType.NY, 300, "Frankrig");
        Controller.tilførDestilleringTilFad(d1, fad1, 100);
        fad1.setDatoPåfyldning(LocalDate.of(2010,1,1));
        Controller.tapFadEksisterendeTapning(t1 ,fad1, 50);
        System.out.println(t1.toString());

    }
}
