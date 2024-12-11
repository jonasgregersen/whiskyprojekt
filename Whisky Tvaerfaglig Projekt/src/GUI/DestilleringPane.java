package GUI;

import Model.Destillering;
import Model.Fad;
import Storage.Storage;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Optional;

public class DestilleringPane extends GridPane {
    private TextField txfStartDato, txfSlutDato, txfMaltBatch, txfIndhold, txfAlkoholProcent;
    private TextArea txaTilførteFad;
    private ListView<Destillering> lvwDestillering;

    public DestilleringPane() {
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        Label lblDestillering = new Label("Destillering");
        this.add(lblDestillering, 0, 0);

        lvwDestillering = new ListView<>();
        this.add(lvwDestillering, 0,1);
        lvwDestillering.getItems().setAll(Storage.getDestillater());
        ChangeListener<Destillering> destilleringListener = (ov, oldDest, newDest) -> this.selectedDestilleringChanged();
        lvwDestillering.getSelectionModel().selectedItemProperty().addListener(destilleringListener);

        VBox vbxInfo = new VBox(40);
        this.add(vbxInfo, 1,1);

        HBox hbxStartDato = new HBox(40);
        hbxStartDato.setPrefWidth(400);
        Label lblStartDato = new Label("Startdato:");
        txfStartDato = new TextField();
        txfStartDato.setEditable(false);
        hbxStartDato.getChildren().addAll(lblStartDato,txfStartDato);

        HBox hbxSlutDato = new HBox(40);
        hbxSlutDato.setPrefWidth(400);
        Label lblSlutDato = new Label("Slutdato:");
        txfSlutDato = new TextField();
        txfSlutDato.setEditable(false);
        hbxSlutDato.getChildren().addAll(lblSlutDato,txfSlutDato);

        HBox hbxIndhold = new HBox(40);
        Label lblIndhold = new Label("Indhold i liter:");
        txfIndhold = new TextField();
        txfIndhold.setEditable(false);
        hbxIndhold.getChildren().addAll(lblIndhold,txfIndhold);

        HBox hbxMaltBatch = new HBox(40);
        Label lblMaltBatch = new Label("Maltbatch:");
        txfMaltBatch = new TextField();
        txfMaltBatch.setEditable(false);
        hbxMaltBatch.getChildren().addAll(lblMaltBatch,txfMaltBatch);

        HBox hbxAlkoholProcent = new HBox(40);
        Label lblAlkoholProcent = new Label("Alkoholprocent:");
        txfAlkoholProcent = new TextField();
        txfAlkoholProcent.setEditable(false);
        hbxAlkoholProcent.getChildren().addAll(lblAlkoholProcent,txfAlkoholProcent);

        HBox hbxTilførteFad = new HBox(40);
        Label lblTilførteFad = new Label("Tilførte fad:");
        txaTilførteFad = new TextArea();
        txaTilførteFad.setEditable(false);
        hbxTilførteFad.getChildren().addAll(lblTilførteFad,txaTilførteFad);

        vbxInfo.getChildren().addAll(hbxStartDato,hbxSlutDato,hbxIndhold,hbxMaltBatch,hbxAlkoholProcent, hbxTilførteFad);

        HBox hbxButtons = new HBox(40);
        this.add(hbxButtons, 0, 7, 3, 1);
        hbxButtons.setPadding(new Insets(10, 0, 0, 0));
        hbxButtons.setAlignment(Pos.BASELINE_CENTER);

        Button btnCreate = new Button("Opret destillering");
        hbxButtons.getChildren().add(btnCreate);
        btnCreate.setOnAction(event -> this.createDestilleringAction());

        Button btnUpdate = new Button("Registrer destillering");
        hbxButtons.getChildren().add(btnUpdate);
        btnUpdate.setOnAction(event -> this.registrerDataAction());

        Button btnDelete = new Button("Fjern destillering");
        hbxButtons.getChildren().add(btnDelete);
        btnDelete.setOnAction(event -> {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Bekræft handling");
            confirmationAlert.setHeaderText("Vil du slette destillering?");

            ButtonType okButton = new ButtonType("Ok");
            ButtonType cancelButton = new ButtonType("Cancel");
            confirmationAlert.getButtonTypes().setAll(cancelButton, okButton);

            Optional<ButtonType> result = confirmationAlert.showAndWait();

            if (result.isPresent() && result.get() == okButton) {
                fjernDestilleringAction();
            } else {
                confirmationAlert.hide();
            }
        });

        HBox hbxButtons2 = new HBox(40);
        this.add(hbxButtons2,0,4);

        Button btnHistorik = new Button("Se historik");
        btnHistorik.setOnAction(event -> this.seDestilleringHistorikAction());

        Button btnPåfyldFad = new Button("Påfyld fad");
        btnPåfyldFad.setOnAction(event -> this.påfyldFadAction());

        hbxButtons2.getChildren().addAll(btnHistorik, btnPåfyldFad);


        if (lvwDestillering.getItems().size() > 0) {
            lvwDestillering.getSelectionModel().select(0);
        }
    }

    public void selectedDestilleringChanged() {
        this.updateControls();
    }
    private void påfyldFadAction() {
        Destillering destillering = lvwDestillering.getSelectionModel().getSelectedItem();
        PåfyldWindow dia = new PåfyldWindow("Påfyld fad", destillering);
        dia.showAndWait();
        updateControls();
    }

    public void updateControls() {
        Destillering destillering = lvwDestillering.getSelectionModel().getSelectedItem();
        txfStartDato.setText(destillering.getStartDato() == null ? "Ikke angivet" : destillering.getStartDato().toString());
        txfSlutDato.setText(destillering.getSlutDato() == null ? "Ikke angivet" : destillering.getSlutDato().toString());
        txfIndhold.setText(Double.toString(destillering.getMængde()));
        txfMaltBatch.setText(destillering.getMaltBatch() + " - " + destillering.getRåvareMængde());
        if (!destillering.getTilførteFad().isEmpty()) {
            StringBuilder sbDest = new StringBuilder();
            for (Fad fad : destillering.getTilførteFad()) {
                sbDest.append("Fad nr. " + fad.getFadNr() + " - påfyldt: " + fad.getDestillater().get(destillering) +
                        " liter, " + destillering.getAlkoholProcent() + "%\n");
            }
            txaTilførteFad.setText(sbDest.toString());
        } else {
            txaTilførteFad.setText("Destillat er ikke tilført fad");
        }
        txfAlkoholProcent.setText(Double.toString(destillering.getAlkoholProcent()));
    }

    private void createDestilleringAction() {
        OpretDestilleringWindow dia = new OpretDestilleringWindow("Opret Destillering");
        dia.showAndWait();
        updateControls();

        lvwDestillering.getItems().setAll(Storage.getDestillater());
        int index = lvwDestillering.getItems().size() - 1;
        lvwDestillering.getSelectionModel().select(index);
    }
    private void fjernDestilleringAction() {
        Destillering d = lvwDestillering.getSelectionModel().getSelectedItem();
        try {
            Controller.Controller.fjernDestillering(d);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.setTitle("Fjern destillering");
            alert.setHeaderText("Kan ikke fjerne destillering");
            alert.showAndWait();
        }
        lvwDestillering.getItems().setAll(Storage.getDestillater());
    }
    private void seDestilleringHistorikAction() {
        Destillering destillering = lvwDestillering.getSelectionModel().getSelectedItem();
        HistorikWindow dia = new HistorikWindow("Fad nr. " + destillering.getSpiritBatch() + " historik", destillering);
        dia.showAndWait();
    }
    private void registrerDataAction() {
        Destillering destillering = lvwDestillering.getSelectionModel().getSelectedItem();
        RegistrerDestilleringWindow dia = new RegistrerDestilleringWindow("Registrer " + destillering.getSpiritBatch(), destillering);
        dia.showAndWait();
        updateControls();
    }
    // ------------------------------------------------------------------------

}
