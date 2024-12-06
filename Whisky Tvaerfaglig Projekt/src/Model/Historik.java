package Model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class Historik {
    private String type;
    private String beskrivelse;
    private LocalDate dato;
    private LocalTime tid;

    public Historik() {
        dato = LocalDate.now();
        tid = LocalTime.now();
    }

    public void registrerHændelse(String type, String beskrivelse) {
        this.beskrivelse = beskrivelse;
        this.type = type;
    }

    public String toString() {
        return dato.toString() + " " + tid.toString().substring(0, 8) + " - " + type + ": " + beskrivelse;
    }
}
