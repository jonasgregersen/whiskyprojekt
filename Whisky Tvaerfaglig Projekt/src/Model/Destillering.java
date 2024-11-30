package Model;

import javax.swing.event.MenuKeyEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Destillering {
    private LocalDate startDato;
    private LocalDate slutDato;
    private double alkoholProcent;
    private String spiritBatch;
    private double mængde;
    private List<Fad> fade;
    private List<Historik> historik;
    private String maltBatch;


    public Destillering(LocalDate startDato, LocalDate slutDato, String spiritBatch) {
        this.spiritBatch = spiritBatch;
        historik = new ArrayList<>();
        this.fade = new ArrayList<>();
        this.startDato = startDato;
        this.slutDato = slutDato;
    }

    public void registrerDestilleringsData(double alkoholProcent, double mængde) throws IllegalArgumentException {
        if (alkoholProcent < 0 || mængde < 0) {
            throw new IllegalArgumentException("Destillerings data skal være et positivt tal.");
        }
        this.alkoholProcent = alkoholProcent;
        this.mængde = mængde;
        registrerHændelse("Dataregistrering", "Destillering afsluttet. Alkohol procent: " + alkoholProcent + ", mængde : " + mængde);
    }

    public void tilførFad(Fad fad, double mængde) throws IllegalArgumentException {
        if (this.mængde - mængde < 0) {
            throw new IllegalArgumentException("Ikke nok væske i destilleringen.");
        }
        if (this.mængde == 0 || this.alkoholProcent == 0) {
            throw new NullPointerException("Kan ikke tilføre fad uden destillerings data.");
        }
        if (fad == null) {
            throw new IllegalArgumentException("Fadet skal angives");
        }
        this.mængde -= mængde;
        fad.påfyld(this, mængde);
        fade.add(fad);
        registrerHændelse("Tilførelse", "BatchNr. " + spiritBatch + " påfyldt fadNr. " + fad.getFadNr() + " " + mængde + " liter.");
    }

    private Historik registrerHændelse(String type, String beskrivelse) {
        Historik hændelse = new Historik();
        hændelse.registrerHændelse(type, beskrivelse);
        historik.add(hændelse);
        return hændelse;
    }

    public void tilføjRåvare(Lager lager, Råvare råvare, double mængde) {
        lager.forbrugRåvare(råvare, mængde);
        maltBatch = råvare.getMaltBatch();
        registrerHændelse("Tilføjelse af råvare", maltBatch +
                " er blevet tilføjet til spirit batch: " + spiritBatch);
    }
    public void fjernFad(Fad fad) {
        fade.remove(fad);
        registrerHændelse("Fjernelse af fad", "Fad nr. " + fad.getFadNr() +
                " fjernet fra spirit batch: " + spiritBatch);
    }
    public String getSpiritBatch() {
        return spiritBatch;
    }

    public void udskrivHistorik() {
        for (Historik h : historik) {
            h.udskriv();
        }
    }

    public ArrayList<Fad> getTilførteFad() {
        return new ArrayList<>(fade);
    }

    public double getAlkoholProcent() {
        return alkoholProcent;
    }

    public double getMængde() {
        return mængde;
    }

    public ArrayList<Historik> getHistorik() {
        return new ArrayList<>(historik);
    }

    public String getMaltBatch() {
        return maltBatch;
    }
}
