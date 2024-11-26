package Model;

public class Placering {
    private Lager lager;
    private Reol reol;
    private Hylde hylde;

    public Placering(Lager lager, Reol reol, Hylde hylde) {
        this.lager = lager;
        this.reol = reol;
        this.hylde = hylde;
    }

    public Lager getLager() {
        return lager;
    }

    public Reol getReol() {
        return reol;
    }

    public Hylde getHylde() {
        return hylde;
    }
    public String toString() {
        return "Placering: Lager [" + lager +
                "], Reol [" + reol +
                "], Hylde [" + hylde +
                "]";
    }
}
