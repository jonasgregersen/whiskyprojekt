import Model.*;

public class Main {
    public static void main(String[] args) {
        Lager lager1 = new Lager(1, "Sall");
        Hylde h1 = new Hylde("H1", 4);
        Reol r1 = new Reol("R1");
        Placering p = new Placering(lager1, r1, h1);
        Destillering d1 = new Destillering(null, null, "NWB123");
        Destillering d2 = new Destillering(null, null, "NWB 124");
        Fad fad = new Fad(1, Fad.FadType.SHERRY, 200, "Tyskland");
        fad.setPlacering(p);
        d1.registrerDestilleringsData(70, 150);
        d2.registrerDestilleringsData(65, 150);
        d1.tilførFad(fad, 100);
        d2.tilførFad(fad, 50);
        d2.tilførFad(fad, 100);
        System.out.println(fad.klarTilTapning());

    }
}
