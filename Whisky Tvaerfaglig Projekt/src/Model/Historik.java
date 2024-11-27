package Model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class Historik {
    private int historikId;
    private String type;
    private static int id;
    private String beskrivelse;
    private LocalDate dato;
    private LocalTime tid;

    public Historik() {
        historikId = id;
        id++;
        dato = LocalDate.now();
        tid = LocalTime.now();
    }

    public void registrerHændelse(String type, String beskrivelse) {
        this.beskrivelse = beskrivelse;
        this.type = type;
    }

    public String udskriv() {
        return dato.toString() + " " + tid.toString().substring(0, 8) + " - " + type + ": " + beskrivelse;
    }
}
