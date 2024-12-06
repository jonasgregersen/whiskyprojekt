package Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tapning {
    private HashMap<Fad, Double> fadVæske;
    private double fortyndingsMængde;
    private double totalMængde;
    private double alkoholProcent;
    private List<WhiskyProdukt> tilførteProdukter;
    public Tapning(double fortyndingsMængde) {
        this.totalMængde = fortyndingsMængde;
        this.fortyndingsMængde = fortyndingsMængde;
        alkoholProcent = 0;
        fadVæske = new HashMap<>();
    }
    public void tilføjFad(Fad fad, double mængde) {
        if (fad == null) {
            throw new IllegalArgumentException("Fadet skal være angivet.");
        }
        if (fad.getNuværendeIndhold() - mængde < 0) {
            throw new IllegalArgumentException("Ikke nok væske i fadet.");
        }
        if (fadVæske.containsKey(fad)) {
            double eksisterendeVæske = fadVæske.get(fad);
            double tilføjetVæske = mængde;
            fadVæske.put(fad, eksisterendeVæske + tilføjetVæske);
        }
        fadVæske.put(fad, mængde);
        totalMængde += mængde;
        alkoholProcent = beregnAlkoholProcent();
    }
    public double beregnAlkoholProcent() {
        if (fadVæske.isEmpty()) {
            return 0;
        }
        double beregnetAlkoPct = 0;
        double entryAlkoholVolumen;
        double totalAlkoholVolumen = 0;
        for (Map.Entry<Fad, Double> entry : fadVæske.entrySet()) {
            entryAlkoholVolumen = entry.getValue() * entry.getKey().beregnAlkoholProcent() / 100;
            totalAlkoholVolumen += entryAlkoholVolumen;
        }
        beregnetAlkoPct = totalAlkoholVolumen / totalMængde;
        return beregnetAlkoPct;
    }
    private HashMap<Fad, Double> beregnReduceretVæske(double mængde) {
        if (totalMængde - mængde < 0) {
            throw new IllegalArgumentException("Der er ikke nok væske i tapningen.");
        }
        if (totalMængde == 0) {
            throw new IllegalArgumentException("Tapningen er tom.");
        }
        HashMap<Fad, Double> reduceretVæskeMap = new HashMap<>();
        double reduceretVæske = 0;
        for (Map.Entry<Fad, Double> entry : fadVæske.entrySet()) {
            reduceretVæske = (entry.getValue() / totalMængde) * mængde;
            reduceretVæskeMap.put(entry.getKey(), entry.getValue() - reduceretVæske);
        }
        return reduceretVæskeMap;
    }
    public void tilførProdukt(WhiskyProdukt produkt) {
        produkt.setVæskeBlanding(this);
        this.totalMængde -= produkt.getIndholdsKapacitet();
        fadVæske = beregnReduceretVæske(produkt.getIndholdsKapacitet());
    }
    public HashMap<Fad, Double> getFadVæske() {
        return new HashMap<>(fadVæske);
    }
    public double getFortyndingsMængde() {
        return fortyndingsMængde;
    }
}
