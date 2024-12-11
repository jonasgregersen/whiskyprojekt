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
        Reol reol1 = sall.opretReol("R1");
        Reol reol2 = sall.opretReol("R2");
        Reol reol3 = sall.opretReol("R3");
        Reol reol4 = sall.opretReol("R4");
        Reol reol5 = sall.opretReol("R5");

        Hylde hylde1 = reol1.opretHylde("H1", 4);  // 4 pladser på H1
        Hylde hylde2 = reol1.opretHylde("H2", 4);  // 4 pladser på H2
        Hylde hylde3 = reol2.opretHylde("H3", 4);  // 4 pladser på H3
        Hylde hylde4 = reol2.opretHylde("H4", 4);  // 4 pladser på H4
        Hylde hylde5 = reol3.opretHylde("H5", 4);  // 4 pladser på H5
        Hylde hylde6 = reol3.opretHylde("H6", 4);  // 4 pladser på H6
        Hylde hylde7 = reol4.opretHylde("H7", 4);  // 4 pladser på H7
        Hylde hylde8 = reol4.opretHylde("H8", 4);  // 4 pladser på H8
        Hylde hylde9 = reol5.opretHylde("H9", 4);  // 4 pladser på H9
        Hylde hylde10 = reol5.opretHylde("H10", 4); // 4 pladser på H10

        Placering placering1 = new Placering(sall, reol1, hylde1);
        Placering placering2 = new Placering(sall, reol1, hylde2);
        Placering placering3 = new Placering(sall, reol2, hylde3);
        Placering placering4 = new Placering(sall, reol2, hylde4);
        Placering placering5 = new Placering(sall, reol3, hylde5);
        Placering placering6 = new Placering(sall, reol3, hylde6);
        Placering placering7 = new Placering(sall, reol4, hylde7);
        Placering placering8 = new Placering(sall, reol4, hylde8);
        Placering placering9 = new Placering(sall, reol5, hylde9);
        Placering placering10 = new Placering(sall, reol5, hylde10);

        Fad fad1 = Controller.opretFad(1, Fad.FadType.NY, 200.0, "Danmark");
        Fad fad2 = Controller.opretFad(2, Fad.FadType.BOURBON, 180.0, "USA");
        Fad fad3 = Controller.opretFad(3, Fad.FadType.SHERRY, 220.0, "Spanien");
        Fad fad4 = Controller.opretFad(4, Fad.FadType.OLOROSO, 150.0, "Spanien");
        Fad fad5 = Controller.opretFad(5, Fad.FadType.NY, 250.0, "Frankrig");
        Fad fad6 = Controller.opretFad(6, Fad.FadType.BOURBON, 200.0, "Irland");
        Fad fad7 = Controller.opretFad(7, Fad.FadType.SHERRY, 300.0, "Portugal");
        Fad fad8 = Controller.opretFad(8, Fad.FadType.OLOROSO, 400.0, "Italien");
        Fad fad9 = Controller.opretFad(9, Fad.FadType.NY, 350.0, "Tyskland");
        Fad fad10 = Controller.opretFad(10, Fad.FadType.BOURBON, 500.0, "Skotland");

        Controller.setFadPlacering(fad1, placering1);
        Controller.setFadPlacering(fad2, placering2);
        Controller.setFadPlacering(fad3, placering3);
        Controller.setFadPlacering(fad4, placering4);
        Controller.setFadPlacering(fad5, placering5);
        Controller.setFadPlacering(fad6, placering6);
        Controller.setFadPlacering(fad7, placering7);
        Controller.setFadPlacering(fad8, placering8);
        Controller.setFadPlacering(fad9, placering9);
        Controller.setFadPlacering(fad10, placering10);

        Destillering dest1 = Controller.opretDestillering(LocalDate.of(2020, 1, 1), LocalDate.of(2020, 3, 1), "NM77P");
        Destillering dest2 = Controller.opretDestillering(LocalDate.of(2021, 2, 1), LocalDate.of(2021, 4, 1), "NM78P");
        Destillering dest3 = Controller.opretDestillering(LocalDate.of(2019, 5, 1), LocalDate.of(2019, 8, 1), "NM89P");
        Destillering dest4 = Controller.opretDestillering(LocalDate.of(2020, 6, 1), LocalDate.of(2020, 9, 1), "NM94P");
        Destillering dest5 = Controller.opretDestillering(LocalDate.of(2022, 1, 15), LocalDate.of(2022, 4, 15), "NM67P");
        Destillering dest6 = Controller.opretDestillering(LocalDate.of(2020, 7, 1), LocalDate.of(2020, 10, 1), "NM91P");
        Destillering dest7 = Controller.opretDestillering(LocalDate.of(2019, 8, 1), LocalDate.of(2019, 11, 1), "NM45P");
        Destillering dest8 = Controller.opretDestillering(LocalDate.of(2021, 9, 1), LocalDate.of(2021, 12, 1), "NM31P");
        Destillering dest9 = Controller.opretDestillering(LocalDate.of(2020, 10, 1), LocalDate.of(2021, 1, 1), "NM65P");
        Destillering dest10 = Controller.opretDestillering(LocalDate.of(2021, 11, 1), LocalDate.of(2022, 2, 1), "NM74P");

        Controller.registrerDestilleringsData(dest1, 400.0, 65.0);
        Controller.registrerDestilleringsData(dest2, 450.0, 60.0);
        Controller.registrerDestilleringsData(dest3, 380.0, 70.0);
        Controller.registrerDestilleringsData(dest4, 520.0, 75.0);
        Controller.registrerDestilleringsData(dest5, 690.0, 68.0);
        Controller.registrerDestilleringsData(dest6, 850.0, 72.0);
        Controller.registrerDestilleringsData(dest7, 500.0, 55.0);
        Controller.registrerDestilleringsData(dest8, 470.0, 58.0);
        Controller.registrerDestilleringsData(dest9, 920.0, 62.0);
        Controller.registrerDestilleringsData(dest10, 650.0, 64.0);

        Råvare råvare1 = Controller.opretRåvare(sall, Råvare.KornSort.EVERGREEN, "MaltBatch1", 1000.0);
        Råvare råvare2 = Controller.opretRåvare(sall, Råvare.KornSort.STAIRWAY, "MaltBatch2", 1200.0);
        Råvare råvare3 = Controller.opretRåvare(sall, Råvare.KornSort.IRINA, "MaltBatch3", 1500.0);
        Råvare råvare4 = Controller.opretRåvare(sall, Råvare.KornSort.EVERGREEN, "MaltBatch4", 800.0);
        Råvare råvare5 = Controller.opretRåvare(sall, Råvare.KornSort.STAIRWAY, "MaltBatch5", 950.0);

        sall.tilføjRåvare(råvare1, 1000.0);
        sall.tilføjRåvare(råvare2, 1200.0);
        sall.tilføjRåvare(råvare3, 1500.0);
        sall.tilføjRåvare(råvare4, 800.0);
        sall.tilføjRåvare(råvare5, 950.0);

        dest1.tilføjRåvare(sall, råvare1, 200.0);
        dest2.tilføjRåvare(sall, råvare2, 250.0);
        dest3.tilføjRåvare(sall, råvare3, 300.0);
        dest4.tilføjRåvare(sall, råvare4, 150.0);
        dest5.tilføjRåvare(sall, råvare5, 180.0);
        dest6.tilføjRåvare(sall, råvare1, 100.0);
        dest7.tilføjRåvare(sall, råvare2, 120.0);
        dest8.tilføjRåvare(sall, råvare3, 200.0);
        dest9.tilføjRåvare(sall, råvare4, 250.0);
        dest10.tilføjRåvare(sall, råvare5, 220.0);

        Controller.tilførDestilleringTilFad(dest1, fad1, 150.0);
        Controller.tilførDestilleringTilFad(dest2, fad2, 180.0);
        Controller.tilførDestilleringTilFad(dest3, fad3, 170.0);
        Controller.tilførDestilleringTilFad(dest4, fad4, 150.0);
        Controller.tilførDestilleringTilFad(dest5, fad5, 200.0);
        Controller.tilførDestilleringTilFad(dest6, fad6, 150.0);
        Controller.tilførDestilleringTilFad(dest7, fad7, 180.0);
        Controller.tilførDestilleringTilFad(dest8, fad8, 220.0);
        Controller.tilførDestilleringTilFad(dest9, fad9, 180.0);
        Controller.tilførDestilleringTilFad(dest10, fad10, 250.0);

        fad1.setDatoPåfyldning(LocalDate.of(2020, 4, 15));
        fad2.setDatoPåfyldning(LocalDate.of(2019, 1, 1));
        fad3.setDatoPåfyldning(LocalDate.of(2019, 11, 22));
        fad4.setDatoPåfyldning(LocalDate.of(2021, 7, 10));
        fad5.setDatoPåfyldning(LocalDate.of(2018, 2, 25));
        fad6.setDatoPåfyldning(LocalDate.of(2020, 8, 5));
        fad7.setDatoPåfyldning(LocalDate.of(2022, 6, 14));
        fad8.setDatoPåfyldning(LocalDate.of(2021, 5, 30));
        fad9.setDatoPåfyldning(LocalDate.of(2020, 12, 18));
        fad10.setDatoPåfyldning(LocalDate.of(2019, 9, 12));

        // Opret tapning
        Tapning tapning1 = Controller.opretTapning("TAP101", 50.0); // Fortyndingsmængde på 50 liter vand
        Tapning tapning2 = Controller.opretTapning("TAP102", 30.0); // Fortyndingsmængde på 30 liter vand

        // Tap fadene til tapningerne
        Controller.tapFadEksisterendeTapning(tapning1, fad1, 10.0);  // Tap 100 liter fra fad1 til tapning1
        Controller.tapFadEksisterendeTapning(tapning1, fad2, 8.0);   // Tap 80 liter fra fad2 til tapning1
        Controller.tapFadEksisterendeTapning(tapning2, fad3, 15.0);  // Tap 150 liter fra fad3 til tapning2
        Controller.tapFadEksisterendeTapning(tapning2, fad4, 12.0);  // Tap 120 liter fra fad4 til tapning2
        Controller.tapFadEksisterendeTapning(tapning1, fad5, 20.0);

        WhiskyProdukt whisky1 = Controller.opretProdukt("Danmarks Finest", "BatchA-001", tapning1, 0.7);
        WhiskyProdukt whisky2 = Controller.opretProdukt("Skotland Reserve", "BatchB-001", tapning2, 0.7);
        WhiskyProdukt whisky3 = Controller.opretProdukt("Spanien Sherry Delight", "BatchA-002", tapning1, 0.7);
        WhiskyProdukt whisky4 = Controller.opretProdukt("Irland Bourbon Blend", "BatchB-002", tapning2, 0.7);

        whisky1.setLagerPlacering(sall);
        whisky2.setLagerPlacering(sall);
        whisky3.setLagerPlacering(sall);
        whisky4.setLagerPlacering(sall);

        //WhiskyProdukt p1 = Controller.opretProdukt("Whisky", "PB001", væskeBlanding,0.7);
        //p1.setLagerPlacering(sall);
        //System.out.println(p1.udskrivProduktionsProcess());
    }
}
