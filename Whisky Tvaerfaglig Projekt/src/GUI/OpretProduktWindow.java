package GUI;

import Controller.Controller;
import Model.Lager;
import Model.Tapning;
import Model.WhiskyProdukt;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class OpretProduktWindow extends Stage {
    private TextField txfNavn, txfProduktBatch, txfVolumeKapacitet;
    private Lager lager;
    private ListView<Tapning> lvwTapning;
    public OpretProduktWindow(String title, Lager lager) {
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);

        this.lager = lager;
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

        Label lblVælgTapning = new Label("Vælg tapning");
        pane.add(lblVælgTapning, 0,0);

        lvwTapning = new ListView<>();
        pane.add(lvwTapning,0,1);
        lvwTapning.getItems().setAll(Storage.Storage.getTapninger());

        Label lblNavn = new Label("Produkt navn:");
        pane.add(lblNavn, 1,1);

        txfNavn = new TextField();
        pane.add(txfNavn, 2, 1);

        Label lblProduktBatch = new Label("Produkt batch:");
        pane.add(lblProduktBatch, 1, 2);

        txfProduktBatch = new TextField();
        pane.add(txfProduktBatch,2,2);

        Label lblVolumeKapacitet = new Label("Volume kapacitet:");
        pane.add(lblVolumeKapacitet, 1, 3);

        txfVolumeKapacitet = new TextField();
        pane.add(txfVolumeKapacitet,2,3);

        HBox hbxButtons = new HBox(40);
        pane.add(hbxButtons, 0, 4);

        Button btnCancel = new Button("Cancel");
        btnCancel.setOnAction(event -> this.hide());

        Button btnOk = new Button("Ok");
        btnOk.setOnAction(event -> this.okAction());

        hbxButtons.getChildren().addAll(btnCancel,btnOk);
    }
    private void okAction() {
        Tapning valgtTapning = lvwTapning.getSelectionModel().getSelectedItem();
        String navn = txfNavn.getText().trim();
        String produktBatch = txfProduktBatch.getText().trim();
        double volumeKapacitet = Double.parseDouble(txfVolumeKapacitet.getText().trim());

        try {
            WhiskyProdukt produkt = Controller.opretProdukt(navn, produktBatch, valgtTapning, volumeKapacitet);
            produkt.setLagerPlacering(lager);
            Controller.tilførTapningTilProdukt(valgtTapning, produkt);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Opret produkt");
            alert.setHeaderText(navn + " - " + produktBatch + " oprettet.");
            alert.showAndWait();
            this.hide();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.setTitle("Opret produkt");
            alert.setHeaderText("Kan ikke oprette produkt");
            alert.showAndWait();
        }
    }
}
