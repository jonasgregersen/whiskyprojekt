package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WhiskyProdukt {
    private String navn;
    private double alkoholProcent;
    private String produktBatch;
    private Tapning væskeBlanding;
    private double indholdsKapacitet;
    private ProduktType type;
    private enum ProduktType {
        SINGLE_CASK, CASK_STRENGTH, BLENDED
    }


    public WhiskyProdukt(String navn, String produktBatch, Tapning væskeBlanding, double indholdsKapacitet) {
        this.navn = navn;
        this.produktBatch = produktBatch;
        this.indholdsKapacitet = indholdsKapacitet;
        this.væskeBlanding = setVæskeBlanding(væskeBlanding);
    }
    public Tapning setVæskeBlanding(Tapning væskeBlanding) throws IllegalArgumentException{
        this.væskeBlanding = væskeBlanding;
        if (væskeBlanding.getFadVæske().isEmpty()) {
            throw new IllegalArgumentException("Tapningsvæsken er tom.");
        }
        if (væskeBlanding.getFadVæske().size() == 1 && væskeBlanding.getFortyndingsMængde() == 0) {
            this.type = ProduktType.CASK_STRENGTH;
        }
        if (væskeBlanding.getFadVæske().size() == 1 && væskeBlanding.getFortyndingsMængde() > 0) {
            this.type = ProduktType.SINGLE_CASK;
        }
        if (væskeBlanding.getFadVæske().size() > 1) {
            this.type = ProduktType.BLENDED;
        }
        alkoholProcent = væskeBlanding.beregnAlkoholProcent();
        return væskeBlanding;
    }
    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public double getAlkoholProcent() {
        return alkoholProcent;
    }

    public String getProduktBatch() {
        return produktBatch;
    }
    public double getIndholdsKapacitet() {
        return indholdsKapacitet;
    }
    public ProduktType getType() {
        return type;
    }
    @Override
    public String toString() {
        return type != null ? type + " - " + produktBatch : produktBatch;
    }
    public String udskrivProduktionsProcess() {
        StringBuilder sbResult = new StringBuilder();
        sbResult.append("Whisky produkt:\n");
        sbResult.append("Navn: " + navn + "\n");
        sbResult.append("Type: " + type + "\n");
        sbResult.append("Produktbatch: " + produktBatch + "\n");
        sbResult.append("Alkoholprocent " + væskeBlanding.beregnAlkoholProcent() + "\n");
        sbResult.append("\nMaltbatch:\n");
        væskeBlanding.getFadVæske().forEach( (k,v) ->
                k.getDestillater().forEach((d, f) -> sbResult.append(d.getMaltBatch() + "\n")));
        sbResult.append("\nSpiritbatch:\n");
        væskeBlanding.getFadVæske().forEach( (k,v) ->
                k.getDestillater().forEach( (d, f) -> sbResult.append(d.getSpiritBatch() + "\n")));
        sbResult.append("\nFad brugt:\n");
        væskeBlanding.getFadVæske().forEach( (k,v) -> sbResult.append("Fad nr. " + k.getFadNr() + ": " + k.getFadType() +
                ", indkøbt fra " + k.getIndkøbtFra() + "\n"));
        sbResult.append("\nAlkoholprocent: " + væskeBlanding.beregnAlkoholProcent());
        return sbResult.toString();
    }
}
