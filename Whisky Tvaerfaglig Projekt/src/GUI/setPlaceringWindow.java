package GUI;

import Model.*;
import Storage.Storage;
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
        pane.add(lvwLagre, 0 , 1);

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
    }
    private void setPlaceringAction() {
        try {
            Lager lager = lvwLagre.getSelectionModel().getSelectedItem();
            Reol reol = lvwReoler.getSelectionModel().getSelectedItem();
            Hylde hylde = lvwHylder.getSelectionModel().getSelectedItem();
            Placering placering = new Placering(lager, reol, hylde);
            fad.setPlacering(placering);
            this.hide();
        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.setTitle("Set fadplacering");
            alert.setHeaderText("Kan ikke sette fadplacering.");
            alert.showAndWait();
        }

    }
}
