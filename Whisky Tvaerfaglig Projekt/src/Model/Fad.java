package Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fad implements Historikable {
    private int fadNr;
    private FadType fadType;
    private double kapacitet;
    private Placering placering;
    private LocalDate datoPåfyldning;
    private double nuværendeIndhold;
    private String indkøbtFra;
    private Map<Destillering, Double> destillater = new HashMap<>();
    private List<Historik> historik;

    public enum FadType {
        NY, BOURBON, SHERRY, OLOROSO
    }

    public Fad(int fadNr, FadType fadType, double kapacitet, String indkøbt) throws IllegalArgumentException {
        if (fadNr <= 0) {
            throw new IllegalArgumentException("Fadnr. skal være større end 0.");
        }
        if (fadType == null) {
            throw new IllegalArgumentException("Fadtype skal angives.");
        }
        if (kapacitet <= 0) {
            throw new IllegalArgumentException("Kapacitet skal være større end 0.");
        }
        if (indkøbt.isEmpty()) {
            throw new IllegalArgumentException("Indkøbsland skal angives.");
        }
        this.fadNr = fadNr;
        this.fadType = fadType;

        this.kapacitet = kapacitet;
        historik = new ArrayList<>();
        this.indkøbtFra = indkøbt;
        this.nuværendeIndhold = 0.0;
    }

    public void påfyld(Destillering destillat, double mængde) throws IllegalArgumentException {
        if (mængde + nuværendeIndhold > kapacitet) {
            throw new IllegalArgumentException("Fadet kan ikke rumme mere.");
        }
        if (destillat.getAlkoholProcent() < 0 || destillat.getAlkoholProcent() > 100) {
            throw new IllegalArgumentException("Alkoholprocenten skal ligge imellem 0 og 100.");
        }
        if (destillat == null) {
            throw new IllegalArgumentException("Spiritbatch skal specificeres.");
        }
        if (!destillat.isDataRegistreret()) {
            throw new IllegalArgumentException("Registreringsdata er ikke registreret.");
        }
        destillater.put(destillat, destillater.containsKey(destillat) ?
                destillater.get(destillat) + mængde : mængde);
        this.nuværendeIndhold += mængde;
        datoPåfyldning = LocalDate.now();
        registrerHændelse("Påfyldning", "Fad nr. " + fadNr + " påfyldt " + mængde + " liter, " + destillat.getAlkoholProcent() +
                "% fra spirit batch: " + destillat.getSpiritBatch() +
                ". Nuværende indhold: " + nuværendeIndhold + " liter, nuværende alkohol procent: " + beregnAlkoholProcent() + "%.");
    }

    public HashMap<Destillering, Double> tap(Tapning tapning, double mængde) throws IllegalArgumentException {
        if (nuværendeIndhold - mængde < 0) {
            throw new IllegalArgumentException("Tapmængden er større end fadets indhold.");
        }
        if (!klarTilTapning()) { // Fadet skal være lagret i mindst 3 år.
            throw new IllegalArgumentException("Fadet er ikke klar til tapning.");
        }
        if (mængde <= 0) {
            throw new IllegalArgumentException("Tapmængden skal være over 0.");
        }
        double tapMængde = 0;
        HashMap<Destillering, Double> tapningPrDestillat = beregnTapningPrDestillat(mængde);
        for (Map.Entry<Destillering, Double> d : destillater.entrySet()) {
            tapMængde = (d.getValue() / nuværendeIndhold) * mængde;
            destillater.put(d.getKey(), d.getValue() - tapMængde);
        }
        nuværendeIndhold -= mængde;
        tapning.tilføjFad(this, mængde);
        registrerHændelse("Tapning", "Fad nr. " + fadNr + " er blevet tappet for " + mængde + " liter" +
                ". Nuværende indhold: " + nuværendeIndhold + " liter.");
        return tapningPrDestillat;
    }

    public HashMap<Destillering, Double> beregnTapningPrDestillat(double mængde) {
        double tapMængde = 0;
        HashMap<Destillering, Double> tapningPrDestillat = new HashMap<>();
        for (Map.Entry<Destillering, Double> d : destillater.entrySet()) {
            tapMængde = (d.getValue() / nuværendeIndhold) * mængde;
            tapningPrDestillat.put(d.getKey(), tapMængde);
        }
        return tapningPrDestillat;
    }

    public void setPlacering(Placering nyPlacering) throws IllegalArgumentException {
        if (nyPlacering.getHylde().vedMaksKapacitet()) {
            throw new IllegalArgumentException("Hylde er ved maks kapacitet.");
        }
        Lager nytLager = nyPlacering.getLager();
        Reol nyReol = nyPlacering.getReol();
        Hylde nyHylde = nyPlacering.getHylde();
        if (placering != null) {
            Lager gammelLager = this.placering.getLager();
            Reol gammelReol = this.placering.getReol();
            Hylde gammelHylde = this.placering.getHylde();
            registrerHændelse("Flytning", "Fad er blevet flyttet fra " + gammelLager
                    + ", reol ID: " + gammelReol + " og hylde ID: " + gammelHylde +
                    ", til " + nytLager + ", hylde ID: " + nyHylde + ", reol ID: " + nyReol);
            gammelHylde.fjernFad(this);
            nyHylde.tilføjFad(this);
            this.placering = nyPlacering;
        } else {
            registrerHændelse("Registrering i lageret", "Fad er blevet lagret i " + nytLager +
                    ", reol ID: " + nyReol + ", hylde ID: " + nyHylde);
            nyHylde.tilføjFad(this);
            this.placering = nyPlacering;

        }
    }

    public double beregnAlkoholProcent() throws IllegalStateException {
        if (nuværendeIndhold == 0) {
            throw new IllegalStateException("Fadet er tomt.");
        }
        if (destillater.isEmpty()) {
            throw new IllegalStateException("Fadet indeholder ingen destillater.");
        }
        double beregnetAlkoholProcent = 0;
        double totalAlkoholVolumen = 0;
        for (Map.Entry<Destillering, Double> entry : destillater.entrySet()) {
            double destillatMængde = entry.getValue();
            double destillatAlkoholProcent = entry.getKey().getAlkoholProcent() / 100;
            totalAlkoholVolumen += destillatMængde * destillatAlkoholProcent;
        }
        beregnetAlkoholProcent = (totalAlkoholVolumen / nuværendeIndhold) * 100;
        return beregnetAlkoholProcent;
    }

    public void fjernDestillat(Destillering destillat) throws IllegalArgumentException {
        if (!destillater.containsKey(destillat)){
            throw new IllegalArgumentException("Destillat findes ikke i fadet.");
        }
        double destillatIndhold = destillater.get(destillat);
        nuværendeIndhold -= destillatIndhold;
        destillater.remove(destillat);
        destillat.fjernFad(this);
        registrerHændelse("Fjern destillat", destillat.getSpiritBatch() + " fjernet fra fad nr. " + fadNr + " (" + destillatIndhold + " liter).");
    }

    public void tømFad() {
        nuværendeIndhold = 0;
        destillater.forEach((destillat, mængde) -> destillat.fjernFad(this));
        destillater.clear();
        registrerHændelse("Tøm fad", "Fad nr. " + fadNr + " er blevet tømt.");
    }

    public void registrerHændelse(String type, String beskrivelse) {
        Historik hændelse = new Historik();
        hændelse.registrerHændelse(type, beskrivelse);
        historik.add(hændelse);
    }

    public int getFadNr() {
        return fadNr;
    }

    public ArrayList<Historik> getHistorik() {
        return new ArrayList<>(historik);
    }

    public boolean klarTilTapning() {
        return LocalDate.now().isAfter(datoPåfyldning.plusYears(3));
    }

    public void setDatoPåfyldning(LocalDate dato) {
        this.datoPåfyldning = dato;
    }

    public HashMap<Destillering, Double> getDestillater() {
        return new HashMap<>(destillater);
    }

    public String toString() {
        return "FadNr: " + fadNr + ", af type: " + fadType.toString();
    }

    public double getNuværendeIndhold() {
        return nuværendeIndhold;
    }

    public double getKapacitet() {
        return kapacitet;
    }

    public Placering getPlacering() {
        return placering;
    }
    public LocalDate getDatoPåfyldning() {
        return datoPåfyldning;
    }
    public FadType getFadType() {
        return fadType;
    }
    public String getIndkøbtFra() {
        return indkøbtFra;
    }
}
