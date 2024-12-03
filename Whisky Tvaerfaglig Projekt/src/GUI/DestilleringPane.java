package GUI;

import Model.Destillering;
import Model.Fad;
import Storage.Storage;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;


public class DestilleringPane extends GridPane {
    private TextField txfMængde, txfMaltBatch, txfAlkoPct, txfStartDato, txfSlutDato;
    private TextArea txaFade;
    private ListView<Destillering> lvwDestillering;

    public DestilleringPane() {
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        Label lblDestilleringer = new Label("Destilleringer");
        this.add(lblDestilleringer, 0, 0);

        lvwDestillering = new ListView<>();
        this.add(lvwDestillering, 0,1, 1, 3);
        lvwDestillering.setPrefWidth(300);
        lvwDestillering.setPrefHeight(200);
        lvwDestillering.getItems().setAll(Storage.getDestillater());

        ChangeListener<Destillering> listener = (ov, oldDest, newDest) -> this.selectedDestilleringChange();
        lvwDestillering.getSelectionModel().selectedItemProperty().addListener(listener);

        Label lblMaltBatch = new Label("Maltbatch:");
        this.add(lblMaltBatch, 0, 4);

        txfMaltBatch = new TextField();
        this.add(txfMaltBatch, 0, 5);
        txfMaltBatch.setEditable(false);


    }
    public void selectedDestilleringChange() {
        this.updateControls();
    }
    public void updateControls() {
        Destillering destillering = lvwDestillering.getSelectionModel().getSelectedItem();
        if (destillering.getRåvare() != null) {
            txfMaltBatch.setText(destillering.getMaltBatch() + "(" + destillering.getRåvare().getKornSort().toString() + ")");
        } else {
            txfMaltBatch.setText("Ingen tilføjet");
        }
        lvwDestillering.getItems().setAll(Storage.getDestillater());
    }
}
