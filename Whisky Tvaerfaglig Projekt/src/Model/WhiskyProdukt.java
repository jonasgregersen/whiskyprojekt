package Model;

public class WhiskyProdukt {
    private String navn;
    private double alkoholProcent;
    private String produktBatch;
    private Tapning væskeBlanding;
    private double indholdsKapacitet;
    private ProduktType type;
    private enum ProduktType {
        SINGLECASK, CASKSTRENGTH, BLENDED
    }


    public WhiskyProdukt(String navn, String produktBatch, double indholdsKapacitet) {
        this.navn = navn;
        this.produktBatch = produktBatch;
        this.indholdsKapacitet = indholdsKapacitet;
    }
    public void setVæskeBlanding(Tapning væskeBlanding) throws IllegalArgumentException{
        this.væskeBlanding = væskeBlanding;
        if (væskeBlanding.getFadVæske().isEmpty()) {
            throw new IllegalArgumentException("Tapningsvæsken er tom.");
        }
        if (væskeBlanding.getFadVæske().size() == 1 && væskeBlanding.getFortyndingsMængde() == 0) {
            this.type = ProduktType.CASKSTRENGTH;
        }
        if (væskeBlanding.getFadVæske().size() == 1 && væskeBlanding.getFortyndingsMængde() > 0) {
            this.type = ProduktType.SINGLECASK;
        }
        if (væskeBlanding.getFadVæske().size() > 1) {
            this.type = ProduktType.BLENDED;
        }
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
        return "WhiskyProdukt{" +
                ", navn='" + navn + '\'' +
                ", alkoholProcent=" + alkoholProcent +
                ", produktBatch=" + produktBatch +
                '}';
    }
}
