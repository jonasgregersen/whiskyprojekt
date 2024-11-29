package Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fad {
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

    public Fad(int fadNr, FadType fadType, double kapacitet, String indkøbt) {
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
        destillater.put(destillat, mængde);
        placering.getHylde().tilføjFad(this);
        this.nuværendeIndhold += mængde;
        datoPåfyldning = LocalDate.now();
        registrerHændelse("Påfyldning", "Fad nr. " + fadNr + " er blevet påfyldt med " + mængde + " liter" +
                " fra spirit batch: " + destillat.getSpiritBatch() + ", med alkoholprocent på " + destillat.getAlkoholProcent() + "%. Nuværende indhold: " + nuværendeIndhold + " liter.");
    }

    public HashMap<Destillering, Double> tap(double mængde) throws IllegalArgumentException {
        if (nuværendeIndhold - mængde < 0) {
            throw new IllegalArgumentException("Tapmængden er større end fadets indhold.");
        }
        if (nuværendeIndhold == 0) {
            throw new IllegalArgumentException("Fadet er tomt.");
        }
        //if (!klarTilTapning()) { // Fadet skal være lagret i mindst 3 år.
        //    throw new IllegalArgumentException("Fadet er ikke klar til tapning.");
        //}
        double tapMængde = 0;
        HashMap<Destillering, Double> tapningPrDestillat = new HashMap<>();
        for (Map.Entry<Destillering, Double> d : destillater.entrySet()) {
            tapMængde = (d.getValue() / nuværendeIndhold) * mængde;
            tapningPrDestillat.put(d.getKey(), tapMængde);
            destillater.put(d.getKey(), d.getValue() - tapMængde);
        }
        nuværendeIndhold -= mængde;
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
        if (placering != null) {
            Lager gammelLager = this.placering.getLager();
            Reol gammelReol = this.placering.getReol();
            Hylde gammelHylde = this.placering.getHylde();
            Lager nytLager = nyPlacering.getLager();
            Reol nyReol = nyPlacering.getReol();
            Hylde nyHylde = nyPlacering.getHylde();
            registrerHændelse("Flytning", "Fad er blevet flyttet fra " + gammelLager
                    + ", reol ID: " + gammelReol + " og hylde ID: " + gammelHylde +
                    ", til " + nytLager + ", hylde ID: " + nyHylde + ", reol ID: " + nyReol);
            gammelHylde.fjernFad(this);
            nyHylde.tilføjFad(this);
            this.placering = nyPlacering;
        } else {
            Lager nytLager = nyPlacering.getLager();
            Reol nyReol = nyPlacering.getReol();
            Hylde nyHylde = nyPlacering.getHylde();
            registrerHændelse("Registrering i lageret", "Fad er blevet lagret i " + nytLager +
                    ", reol ID: " + nyReol + ", hylde ID: " + nyHylde);
            nyHylde.tilføjFad(this);
            this.placering = nyPlacering;

        }
    }
    public double beregnAlkoholProcent() {
        double beregnetAlkoholProcent = 0;
        double totalAlkoholVolumen = 0;
        for (Map.Entry<Destillering, Double> entry : destillater.entrySet()) {
            double totalMængde = entry.getValue();
            double alkoholVolumen = entry.getKey().getAlkoholProcent() / 100;
            totalAlkoholVolumen += totalMængde * alkoholVolumen;
        }
        beregnetAlkoholProcent = (totalAlkoholVolumen / nuværendeIndhold) * 100;
        return beregnetAlkoholProcent;
    }

    private Historik registrerHændelse(String type, String beskrivelse) {
        Historik hændelse = new Historik();
        hændelse.registrerHændelse(type, beskrivelse);
        historik.add(hændelse);
        hændelse.udskriv();
        return hændelse;
    }

    public int getFadNr() {
        return fadNr;
    }

    public ArrayList<Historik> getHistorik() {
        return new ArrayList<>(historik);
    }

    public String udskrivFad() {
        return "FadNr: " + fadNr +
                "\nFad type: " + fadType +
                "\nKapacitet: " + kapacitet +
                "\nNuværende indhold: " + nuværendeIndhold +
                "\nPlacering: " + placering +
                "\nIndkøbt fra: " + indkøbtFra;
    }

    public void udskrivHistorik() {
        for (Historik h : historik) {
            h.udskriv();
        }
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
        return "FadNr: " + fadNr + ", af type: " + fadType.toString() + ", " + placering;
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
}
