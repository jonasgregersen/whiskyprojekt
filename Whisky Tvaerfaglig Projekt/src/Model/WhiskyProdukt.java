package Model;

import java.util.HashMap;

public class WhiskyProdukt {
    private String navn;
    private double alkoholProcent;
    private String produktBatch;
    private VæskeBlanding væskeBlanding;


    public WhiskyProdukt(String navn, String produktBatch, VæskeBlanding væskeBlanding) {
        this.navn = navn;
        this.produktBatch = produktBatch;
        this.væskeBlanding = væskeBlanding;
        alkoholProcent = væskeBlanding.beregnAlkoholProcent();
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

    public void setAlkoholProcent(double alkoholProcent) {
        if (alkoholProcent >= 0 && alkoholProcent <= 100) {
            this.alkoholProcent = alkoholProcent;
        } else {
            throw new IllegalArgumentException("Alkoholprocenten skal ligge imellem 0 og 100.");
        }   }

    public String getProduktBatch() {
        return produktBatch;
    }

    public void setBatchNummer(String produktBatch) {
        this.produktBatch = produktBatch;
    }

    @Override
    public String toString() {
        return "WhiskyProdukt{" +
                ", navn='" + navn + '\'' +
                ", alkoholProcent=" + alkoholProcent +
                ", produktBatch=" + produktBatch +
                '}';
    }
}
