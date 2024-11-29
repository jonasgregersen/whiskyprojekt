package Model;

public class WhiskyProdukt {
    private int id;
    private String navn;
    private double alkoholProcent;
    private double batchNummer;
    private double fortyndingsmængde;

    public WhiskyProdukt(int id, String navn, double alkoholProcent, double batchNummer, double fortyndingsmængde) {
        this.id = id;
        this.navn = navn;
        this.alkoholProcent = alkoholProcent;
        this.batchNummer = batchNummer;
        this.fortyndingsmængde = fortyndingsmængde;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public double getFortyndingsmængde() {
        return fortyndingsmængde;
    }

    public void setFortyndingsmængde(double fortyndingsmængde) {
        this.fortyndingsmængde = fortyndingsmængde;
    }

    @Override
    public String toString() {
        return "WhiskyProdukt{" +
                "id=" + id +
                ", navn='" + navn + '\'' +
                ", alkoholProcent=" + alkoholProcent +
                ", batchNummer=" + batchNummer +
                ", fortyndingsmængde=" + fortyndingsmængde +
                '}';
    }
}
