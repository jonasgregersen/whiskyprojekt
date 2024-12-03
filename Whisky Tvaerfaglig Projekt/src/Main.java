import Controller.Controller;
import Model.*;
import Storage.Storage;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Controller.opretDestillering(LocalDate.now(), LocalDate.now(), "NM88P");
        System.out.println(Storage.getDestillater());

    }
}
