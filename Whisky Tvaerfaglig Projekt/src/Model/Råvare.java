package Model;

import java.util.Map;

public class Råvare {
    private KornSort kornSort;
    private String maltBatch;
    public enum KornSort {
        EVERGREEN, STAIRWAY, IRINA
    }
    public Råvare(KornSort kornSort, String maltBatch, double mængde) {
        this.kornSort = kornSort;
        this.maltBatch = maltBatch;
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
}
