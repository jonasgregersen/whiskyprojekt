package GUI;

import Model.*;
import Storage.Storage;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class setPlaceringWindow extends Stage {
    private Fad fad;
    private ListView<Lager> lvwLagre;
    private ListView<Reol> lvwReoler;
    private ListView<Hylde> lvwHylder;

    private Lager valgtLager;
    private Reol valgtReol;
    private Hylde valgtHylde;

    public setPlaceringWindow(String title, Fad fad) {
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);

        this.fad = fad;

        this.setTitle(title);
        GridPane pane = new GridPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        this.setScene(scene);
    }

    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);

        Label lblLagre = new Label("Lagre");
        pane.add(lblLagre, 0, 0);

        lvwLagre = new ListView<>();
        lvwLagre.getItems().setAll(Storage.getLagre());
        pane.add(lvwLagre, 0, 1);

        Label lblReoler = new Label("Reol");
        pane.add(lblReoler, 1, 0);

        lvwReoler = new ListView<>();
        pane.add(lvwReoler, 1, 1);

        Label lblHylder = new Label("Hylde");
        pane.add(lblHylder, 2, 0);

        lvwHylder = new ListView<>();
        pane.add(lvwHylder, 2, 1);

        Button btnSetPlacering = new Button("Set hyldeplacering");
        btnSetPlacering.setOnAction(event -> this.setPlaceringAction());
        pane.add(btnSetPlacering, 2, 2);

        lvwLagre.getSelectionModel().selectedItemProperty().addListener((ov, oldLager, newLager) -> {
            valgtLager = newLager;
            if (valgtLager != null) {
                lvwReoler.getItems().setAll(valgtLager.getReoler());
                valgtReol = null;
                lvwHylder.getItems().clear();
                valgtHylde = null;
            }
        });

        lvwReoler.getSelectionModel().selectedItemProperty().addListener((ov, oldReol, newReol) -> {
            valgtReol = newReol;
            if (valgtReol != null) {
                lvwHylder.getItems().setAll(valgtReol.getHylder());
                valgtHylde = null;
            }
        });

        lvwHylder.getSelectionModel().selectedItemProperty().addListener((ov, oldHylde, newHylde) -> {
            valgtHylde = newHylde;
        });
    }

    private void setPlaceringAction() {
        try {
            Lager lager = valgtLager;
            Reol reol = valgtReol;
            Hylde hylde = valgtHylde;
            Placering placering = new Placering(lager, reol, hylde);
            fad.setPlacering(placering);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Set placering");
            alert.setHeaderText(fad.getFadNr() + " placeret i " + placering.toString());
            alert.showAndWait();
            this.hide();
        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.setTitle("Set fadplacering");
            alert.setHeaderText("Kan ikke sette fadplacering.");
            alert.showAndWait();
        }

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
        lvwLagre.getItems().setAll(Storage.getLagre());
    }
}
