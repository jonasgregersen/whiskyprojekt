package Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VæskeBlanding {
    private HashMap<Fad, Double> fadVæske;
    private double fortyndingsMængde;
    private double totalMængde;
    private double alkoholProcent;
    public VæskeBlanding(double fortyndingsMængde) {
        this.totalMængde = fortyndingsMængde;
        this.fortyndingsMængde = fortyndingsMængde;
        alkoholProcent = 0;
    }
    public void tilføjFad(Fad fad, double mængde) {
        if (fad == null) {
            throw new IllegalArgumentException("Fadet skal være angivet.");
        }
        if (fad.getNuværendeIndhold() - mængde < 0) {
            throw new IllegalArgumentException("Ikke nok væske i fadet.");
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
    public void tilførProdukt(WhiskyProdukt produkt, double mængde) {

    }
}
