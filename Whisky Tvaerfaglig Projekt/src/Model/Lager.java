package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import Model.Råvare;

public class Lager implements Historikable {
    private int id;
    private String navn;
    private List<Reol> reoler;
    private Map<Råvare, Double> råvarer;
    private ArrayList<Historik> historik;

    public Lager(int id, String navn) {
        this.navn = navn;
        this.id = id;
        reoler = new ArrayList<>();
        råvarer = new HashMap<>();
        historik = new ArrayList<>();
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
        registrerHændelse("Opret reol", "Ny reol oprettet - " + reol.toString());
        return reol;
    }
    public boolean kornSortPaaLager(Råvare.KornSort kornSort) {
        for (Map.Entry<Råvare, Double> entry : råvarer.entrySet()) {
            if (entry.getKey().getKornSort() == kornSort) {
                return true;
            }
        }
        return false;
    }
    public void forbrugRåvare(Råvare råvare, double mængde) throws IllegalArgumentException {
        double gammelMængde = råvarer.get(råvare);
        if (gammelMængde - mængde < 0) {
            throw new IllegalArgumentException("Der er ikke nok råvarer til dette forbrug.");
        }
        råvarer.put(råvare, gammelMængde - mængde);
        registrerHændelse("Forbrug", råvare.getMaltBatch() + "(" + råvare.getKornSort() + ") er blevet forbrugt med " + mængde);
    }
    public String toString() {
        return "Lager ID: " + id + ", Navn: " + navn;
    }
    public void tilføjRåvare(Råvare råvare, double mængde) {
        råvarer.put(råvare, mængde);
        registrerHændelse("Indkøb", råvare.getMaltBatch() + "(" + råvare.getKornSort() + ") er blevet indkøbt, mængde: " + mængde);
    }
    public Råvare findRåvare(String maltBatch) {
        for (Map.Entry<Råvare, Double> entry : råvarer.entrySet()) {
            if (entry.getKey().getMaltBatch().equals(maltBatch)) {
                return entry.getKey();
            }
        }
        return null;
    }
    public double mængdePåBatch(String maltBatch) {
        return råvarer.get(findRåvare(maltBatch));
    }

    @Override
    public void registrerHændelse(String type, String beskrivelse) {
        Historik hændelse = new Historik();
        hændelse.registrerHændelse(type, beskrivelse);
        historik.add(hændelse);
    }

    @Override
    public ArrayList<Historik> getHistorik() {
        return new ArrayList<>(historik);
    }
}
