package Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Fad {
    private int fadNr;
    private FadType fadType;
    private double kapacitet;
    private Lager lager;
    private LocalDate datoPåfyldning;
    private double nuværendeIndhold;
    private double alkoholProcent;
    private String indkøbtFra;
    private List<Destillering> destillater;
    private List<Historik> historik;
    private Hylde hylde;
    private Reol reol;
    public enum FadType {
        NY, BOURBON, SHERRY, OLOROSO
    }
    public Fad(int fadNr, FadType fadType, double kapacitet, Lager lager, Hylde hylde, Reol reol, String indkøbt) {
        this.fadNr = fadNr;
        this.fadType = fadType;
        this.kapacitet = kapacitet;
        this.lager = lager;
        this.hylde = hylde;
        this.reol = reol;
        historik = new ArrayList<>();
        destillater = new ArrayList<>();
        this.indkøbtFra = indkøbt;
        this.nuværendeIndhold = 0.0;
    }
    public void påfyld(Destillering destillat, double mængde, double alkoholProcent) throws IllegalArgumentException {
        if (mængde + nuværendeIndhold > kapacitet) {
            throw new IllegalArgumentException("Fadet kan ikke rumme mere.");
        }
        if (alkoholProcent < 0 || alkoholProcent > 100) {
            throw new IllegalArgumentException("Alkoholprocenten skal ligge imellem 0 og 100.");
        }
        if (destillat == null) {
            throw new IllegalArgumentException("Spiritbatch skal specificeres.");
        }
        destillater.add(destillat);
        hylde.tilføjFad(this);
        this.nuværendeIndhold += mængde;
        this.alkoholProcent = alkoholProcent;
        datoPåfyldning = LocalDate.now();
        registrerHændelse("Påfyldning","Fad nr. " + fadNr + " er blevet påfyldt med " + mængde + " liter" +
                " fra spirit batch: " + destillat.getSpiritBatch() + ", med alkoholprocent på " + alkoholProcent + "%. Nuværende indhold: " + nuværendeIndhold);
    }
    public void tap(double mængde) throws IllegalArgumentException {
        if (nuværendeIndhold - mængde < 0) {
            throw new IllegalArgumentException("Tapmængden er større end fadets indhold.");
        }
        if (datoPåfyldning.plusYears(3).isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Fadet er ikke klar til tapning.");
        }
        this.nuværendeIndhold -= mængde;
        registrerHændelse("Tapning", "Fad nr. " + fadNr + " er blevet tappet for " + mængde + " liter" +
                ". Nuværende indhold: " + nuværendeIndhold);
    }
    public void flytLager(Lager lager, Hylde hylde, Reol reol) {
        registrerHændelse("Flytning", "Fad er blevet flyttet fra " + this.lager
                            + ", reolnr. " + this.reol.getReolId() + " og hyldeNr. " + this.hylde.getHyldeId() +
                            ", til " + lager + ", hylde " + hylde.getHyldeId() + ", reol " + reol.getReolId());
        this.lager = lager;
        this.hylde.fjernFad(this);
        this.hylde = hylde;
        this.reol = reol;
        hylde.tilføjFad(this);
    }
    private Historik registrerHændelse(String type, String beskrivelse) {
        Historik hændelse = new Historik();
        hændelse.registrerHændelse(type, beskrivelse);
        historik.add(hændelse);
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
                "\nLager: " + lager +
                "\nIndkøbt fra: " + indkøbtFra;
    }
    public void udskrivHistorik() {
        for (Historik h : historik) {
            System.out.println(h.udskriv());
        }
    }
    public void setDatoPåfyldning(LocalDate dato) {
        this.datoPåfyldning = dato;
    }
}
