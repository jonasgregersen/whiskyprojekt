package Model;

import java.util.HashMap;

public class WhiskyProdukt {
    private String navn;
    private double alkoholProcent;
    private double batchNummer;
    private VæskeBlanding væskeBlanding;


    public WhiskyProdukt(String navn, double batchNummer, VæskeBlanding væskeBlanding) {
        this.navn = navn;
        this.batchNummer = batchNummer;
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

    public double getBatchNummer() {
        return batchNummer;
    }

    public void setBatchNummer(double batchNummer) {
        this.batchNummer = batchNummer;
    }

    @Override
    public String toString() {
        return "WhiskyProdukt{" +
                ", navn='" + navn + '\'' +
                ", alkoholProcent=" + alkoholProcent +
                ", batchNummer=" + batchNummer +
                '}';
    }
}
