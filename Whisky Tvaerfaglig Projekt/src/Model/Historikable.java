package Model;

import java.util.ArrayList;

public interface Historikable {
    public void registrerHændelse(String type, String beskrivelse);
    public ArrayList<Historik> getHistorik();
}
