package Model;

import java.util.ArrayList;
import java.util.List;

public class Hylde implements Historikable{
    private String hyldeId;
    private int kapacitet;
    private List<Fad> fade;
    private ArrayList<Historik> historik;

    public Hylde(String hyldeId, int kapacitet) {
        this.hyldeId = hyldeId;
        this.kapacitet = kapacitet;
        fade = new ArrayList<>();
        historik = new ArrayList<>();
    }

    public void tilføjFad(Fad fad) throws IndexOutOfBoundsException {
        if (fade.size() + 1 > kapacitet) {
            throw new IndexOutOfBoundsException("Der er ikke mere plads på hylden.");
        }
        fade.add(fad);
        registrerHændelse("Lagring af fad", "Fad nr. " + fad.getFadNr() + " er lagret på hylden.");
    }

    public ArrayList<Fad> getFade() {
        return new ArrayList<Fad>(fade);
    }

    public void fjernFad(Fad fad) {
        fade.remove(fad);
        registrerHændelse("Fjernelse af fad", "Fad nr. " + fad.getFadNr() + " er fjernet fra hylden.");
    }

    public String getHyldeId() {
        return hyldeId;
    }

    public boolean vedMaksKapacitet() {
        return kapacitet == fade.size();
    }
    public void registrerHændelse(String type, String beskrivelse) {
        Historik hændelse = new Historik();
        hændelse.registrerHændelse(type, beskrivelse);
        historik.add(hændelse);
    }
    public ArrayList<Historik> getHistorik() {
        return new ArrayList<>(historik);
    }

    public String toString() {
        return "Hylde ID: " + hyldeId;
    }
}
