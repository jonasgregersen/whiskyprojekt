package Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Destillering {
    private int destilleringId;
    private LocalDate startDato;
    private LocalDate slutDato;
    private double alkoholProcent;
    private String spiritBatch;
    private double mængde;
    private static int id = 0;
    private List<Fad> fade;
    private List<Historik> historik;


    public Destillering(LocalDate startDato, String spiritBatch) {
        this.startDato = startDato;
        this.spiritBatch = spiritBatch;
        destilleringId = id;
        historik = new ArrayList<>();
        this.fade = new ArrayList<>();
        id++;
    }

    public void registrerDestilleringsData(double alkoholProcent, double mængde) throws IllegalArgumentException {
        if (alkoholProcent < 0 || mængde < 0) {
            throw new IllegalArgumentException("Destillerings data skal være et positivt tal.");
        }
        this.alkoholProcent = alkoholProcent;
        this.mængde = mængde;
        afslut();
        registrerHændelse("Dataregistrering", "Destillering afsluttet d." + slutDato.getDayOfMonth() + "/" + slutDato.getDayOfMonth() +
                "/" + slutDato.getYear() + ". Alkohol procent: " + alkoholProcent + ", mængde : " + mængde);
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
        fad.påfyld(this, mængde, this.alkoholProcent);
        fade.add(fad);
        registrerHændelse("Tilførelse", "BatchNr. " + spiritBatch + " påfyldt fadNr. " + fad.getFadNr() + " " + mængde + " liter.");
    }

    private Historik registrerHændelse(String type, String beskrivelse) {
        Historik hændelse = new Historik();
        hændelse.registrerHændelse(type, beskrivelse);
        historik.add(hændelse);
        return hændelse;
    }

    public String getSpiritBatch() {
        return spiritBatch;
    }

    public void afslut() {
        slutDato = LocalDate.now();
    }
}
