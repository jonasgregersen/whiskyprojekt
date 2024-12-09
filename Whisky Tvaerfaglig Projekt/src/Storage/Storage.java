package Storage;
import Model.*;

import java.util.ArrayList;

public class Storage {
    private final static ArrayList<Fad> fade = new ArrayList<>();
    private final static ArrayList<Lager> lagre = new ArrayList<>();
    private final static ArrayList<Destillering> destillater = new ArrayList<>();
    private final static ArrayList<Tapning> tapninger = new ArrayList<>();
    public static void addFad(Fad fad) {
        if (!fade.contains(fad)) {
            fade.add(fad);
        }
    }
    public static void removeFad(Fad fad) {
        fade.remove(fad);
    }
    public static ArrayList<Fad> getFade() {
        return new ArrayList<>(fade);
    }
    public static void addLager(Lager l) {
        if (!lagre.contains(l)) {
            lagre.add(l);
        }
    }
    public static void removeLager(Lager l) {
        lagre.remove(l);
    }
    public static ArrayList<Lager> getLagre() {
        return new ArrayList<>(lagre);
    }
    public static void addDestillat(Destillering d) {
        if (!destillater.contains(d)) {
            destillater.add(d);
        }
    }
    public static void removeDestillat(Destillering d) {
        destillater.remove(d);
    }
    public static ArrayList<Destillering> getDestillater() {
        return new ArrayList<>(destillater);
    }
    public static void addTapning(Tapning t) {
        if (!tapninger.contains(t)) {
            tapninger.add(t);
        }
    }
    public static Tapning removeTapning(Tapning t) {
        Tapning fjernetTapning = t;
        tapninger.remove(t);
        return fjernetTapning;
    }
    public static ArrayList<Tapning> getTapninger() {
        return new ArrayList<>(tapninger);
    }
}
