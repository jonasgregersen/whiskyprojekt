package GUI;
import Model.*;
import javafx.application.Application;
import Controller.Controller;

import java.time.LocalDate;

public class WhiskyApp {
    public static void main(String[] args) {
        initStorage();
        Application.launch(StartWindow.class);
    }
    public static void initStorage() {
        Lager sall = Controller.opretLager(1, "Sall");
        Reol reol = sall.opretReol("R1");
        Hylde hylde = reol.opretHylde("H1", 4);
        Placering placering = new Placering(sall, reol, hylde);

        Fad fad1 = Controller.opretFad(1, Fad.FadType.BOURBON, 32.0, "Kentucky");
        fad1.setPlacering(placering);
        Fad fad2 = Controller.opretFad(2, Fad.FadType.SHERRY, 32.0, "Spanien");
        fad2.setPlacering(placering);
        Fad fad3 = Controller.opretFad(3, Fad.FadType.OLOROSO, 94.0, "Portugal");

        Destillering d1 = Controller.opretDestillering(LocalDate.of(2017,6,19), LocalDate.of(2021, 5, 1), "NM77P");
        Destillering d2 = Controller.opretDestillering(LocalDate.of(2019,5,5), LocalDate.of(2022,8,1), "NM84-87");
        d1.registrerDestilleringsData(60, 200);
        d2.registrerDestilleringsData(63, 300);
        Controller.tilførDestilleringTilFad(d1, fad1, 30);
        Controller.tilførDestilleringTilFad(d2, fad2, 30);
        Controller.tilførDestilleringTilFad(d1, fad3, 64);
        Controller.tilførDestilleringTilFad(d2, fad3, 30);

        Råvare byg = new Råvare(Råvare.KornSort.EVERGREEN, "Batch001", 300);
        sall.tilføjRåvare(byg, 300);
        d1.tilføjRåvare(sall, byg, 100);

        fad1.setDatoPåfyldning(LocalDate.of(2019,5,2));
        fad2.setDatoPåfyldning(LocalDate.of(2021,5,21));
        fad3.setDatoPåfyldning(LocalDate.of(2023,7,19));

        Tapning væskeBlanding = Controller.opretTapning("TAP101", 0);
        Controller.tapFadEksisterendeTapning(væskeBlanding, fad1, 15);
        Controller.tapFadEksisterendeTapning(væskeBlanding, fad2, 10);

        WhiskyProdukt p1 = Controller.opretProdukt("Whisky", "PB001", væskeBlanding,0.7);
        System.out.println(p1.udskrivProduktionsProcess());
    }
}
