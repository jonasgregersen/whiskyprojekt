package Model;

import java.util.ArrayList;
import java.util.List;

public class Reol {
    private String reolId;
    private List<Hylde> hylder;

    public Reol(String reolId) {
        this.reolId = reolId;
        this.hylder = new ArrayList<>();
    }

    public Hylde opretHylde(String hyldeId, int kapacitet) {
        Hylde hylde = new Hylde(hyldeId, kapacitet);
        hylder.add(hylde);
        return hylde;
    }

    public void fjernHylde(Hylde hylde) {
        hylder.remove(hylde);
    }

    public List<Hylde> getHylder() {
        return new ArrayList<>(hylder);
    }

    public String getReolId() {
        return reolId;
    }

    public Hylde getHylde(String hyldeId) {
        for (Hylde h : hylder) {
            if (hyldeId.equals(h.getHyldeId())) ;
            return h;
        }
        return null;
    }

    public String toString() {
        return "Reol ID: " + reolId;
    }
}
