package Model;

public class Råvare {
    private KornSort kornSort;
    private String maltBatch;
    private double mængde;
    private String leverandør;
    private enum KornSort {
        EVERGREEN, STAIRWAY, IRINA
    }
    public Råvare(KornSort kornSort, String maltBatch, double mængde, String leverandør) {
        this.kornSort = kornSort;
        this.maltBatch = maltBatch;
        this.mængde = mængde;
        this.leverandør = leverandør;
    }

    public KornSort getKornSort() {
        return kornSort;
    }

    public String getMaltBatch() {
        return maltBatch;
    }

    public void setMaltBatch(String maltBatch) {
        this.maltBatch = maltBatch;
    }

    public double getMængde() {
        return mængde;
    }

    public void setMængde(double mængde) {
        this.mængde = mængde;
    }

    public String getLeverandør() {
        return leverandør;
    }

    public void setLeverandør(String leverandør) {
        this.leverandør = leverandør;
    }
}
