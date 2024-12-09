package GUI;

import Model.*;
import Storage.Storage;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class FadLagerPane extends GridPane {
    private ListView<Lager> lvwLagre;
    private ListView<Reol> lvwReoler;
    private ListView<Hylde> lvwHylder;
    private ListView<Fad> lvwFade;
    public FadLagerPane() {
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        Label lblLagre = new Label("Lagre");
        this.add(lblLagre, 0, 0);

        lvwLagre = new ListView<>();
        lvwLagre.getItems().setAll(Storage.getLagre());
        this.add(lvwLagre, 0 , 1);

        Label lblReoler = new Label("Reol");
        this.add(lblReoler, 1, 0);

        lvwReoler = new ListView<>();
        this.add(lvwReoler, 1, 1);

        HBox hbxLagerButtons = new HBox(40);
        this.add(hbxLagerButtons, 0, 2);

        Button opretLagerButton = new Button("Opret lager");
        opretLagerButton.setOnAction(event -> this.opretLagerAction());

        Button opretReolButton = new Button("Opret reol");
        opretReolButton.setOnAction(event -> this.opretReolAction());

        hbxLagerButtons.getChildren().addAll(opretLagerButton, opretReolButton);

        Label lblHylder = new Label("Hylde");
        this.add(lblHylder, 2, 0);

        Button opretHyldeButton = new Button("Opret hylde");
        this.add(opretHyldeButton, 1, 2);
        opretHyldeButton.setOnAction(event -> this.opretHyldeAction());

        lvwHylder = new ListView<>();
        this.add(lvwHylder, 2, 1);

        Label lblFad = new Label("Fad");
        this.add(lblFad, 3, 0);

        lvwFade = new ListView<>();
        this.add(lvwFade, 3, 1);

        ChangeListener<Lager> lagerListener = (ov, oldLager, newLager) -> this.updateControls();
        lvwLagre.getSelectionModel().selectedItemProperty().addListener(lagerListener);
        ChangeListener<Reol> reolListener = (ov, oldReol, newReol) -> this.updateControls();
        lvwReoler.getSelectionModel().selectedItemProperty().addListener(reolListener);
        ChangeListener<Hylde> hyldeListener = (ov, oldHylde, newHylde) -> this.updateControls();
        lvwHylder.getSelectionModel().selectedItemProperty().addListener(hyldeListener);


    }
    private void updateControls() {
        Lager selectedLager = lvwLagre.getSelectionModel().getSelectedItem();
        if (selectedLager != null) {
            lvwReoler.getItems().setAll(selectedLager.getReoler());
        }
        Reol selectedReol = lvwReoler.getSelectionModel().getSelectedItem();
        if (selectedReol != null) {
            lvwHylder.getItems().setAll(selectedReol.getHylder());
        }
        Hylde selectedHylde = lvwHylder.getSelectionModel().getSelectedItem();
        if (selectedHylde != null) {
            lvwFade.getItems().setAll(selectedHylde.getFade());
        }
    }
    private void opretLagerAction() {

    }
    private void opretReolAction() {
        Lager selectedLager = lvwLagre.getSelectionModel().getSelectedItem();
        if (selectedLager != null) {
            OpretReolWindow dia = new OpretReolWindow(selectedLager.getNavn(), selectedLager);
            dia.showAndWait();
            updateControls();
        }
    }
    private void opretHyldeAction() {
        Reol selectedReol = lvwReoler.getSelectionModel().getSelectedItem();
        if (selectedReol != null) {
            OpretHyldeWindow dia = new OpretHyldeWindow(selectedReol.getReolId(), selectedReol);
            dia.showAndWait();
            updateControls();
        }
    }

}
