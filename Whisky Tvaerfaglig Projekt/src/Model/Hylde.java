package Model;
import java.util.ArrayList;
import java.util.List;
public class Hylde {
    private String hyldeId;
    private int kapacitet;
    private List<Fad> fade;
    public Hylde(String hyldeId, int kapacitet) {
        this.hyldeId = hyldeId;
        this.kapacitet = kapacitet;
        fade = new ArrayList<>();
    }
    public void tilføjFad(Fad fad) throws IndexOutOfBoundsException {
        if (fade.size() + 1 > kapacitet) {
            throw new IndexOutOfBoundsException("Der er ikke mere plads på hylden.");
        }
        fade.add(fad);
    }
    public ArrayList<Fad> getFade() {
        return new ArrayList<Fad>(fade);
    }
    public void fjernFad(Fad fad) {
        fade.remove(fad);
    }
    public String getHyldeId() {
        return hyldeId;
    }
    public boolean vedMaksKapacitet() {
        return kapacitet == fade.size();
    }
    public String toString() {
        return "Hylde ID: " + hyldeId;
    }
}
