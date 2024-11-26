package Model;

import java.util.ArrayList;
import java.util.List;

public class Lager {
    private String navn;
    private List<Reol> reoler;

    public Lager(String navn) {
        this.navn = navn;
        reoler = new ArrayList<>();
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
    public String getNavn() {
        return navn;
    }
}
