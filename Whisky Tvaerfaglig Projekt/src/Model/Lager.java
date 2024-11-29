package Model;

import java.util.ArrayList;
import java.util.List;

public class Lager {
    private int id;
    private String navn;
    private List<Reol> reoler;
    private List<Råvare> råvarer;

    public Lager(int id, String navn) {
        this.navn = navn;
        this.id = id;
        reoler = new ArrayList<>();
        råvarer = new ArrayList<>();
    }

    public Reol findReol(String reolId) {
        for (Reol r : reoler) {
            if (r.getReolId().equals(reolId)) {
                return r;
            }
        }
        return null;
    }
    public Reol opretReol(String reolId) {
        Reol reol = new Reol(reolId);
        reoler.add(reol);
        return reol;
    }
    public String toString() {
        return "Lager ID: " + id + ", Navn: " + navn;
    }
    public void indkøbRåvarer(){

    }
}
