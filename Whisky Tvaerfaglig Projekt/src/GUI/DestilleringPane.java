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

import java.util.Map;

public class DestilleringPane extends GridPane {
    private TextField txfStartDato, txfIndhold, txfSlutDato, txfAlkoholProcent, txfMaltBatch;
    private TextArea txaTilførteFad, txaCustms;
    private ListView<Destillering> lvwDestillering;

    public DestilleringPane() {
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        Label lblDestillering = new Label("Destillering");
        this.add(lblDestillering, 0, 0);

        lvwDestillering = new ListView<>();
        this.add(lvwDestillering, 0, 1, 1, 3);
        lvwDestillering.setPrefWidth(300);
        lvwDestillering.setPrefHeight(200);
        lvwDestillering.getItems().setAll(Storage.getDestillater());

        ChangeListener<Destillering> listener = (ov, oldDest, newDest) -> this.selectedDestilleringChanged();
        lvwDestillering.getSelectionModel().selectedItemProperty().addListener(listener);

        Label lblStartDato = new Label("Startdato:");
        this.add(lblStartDato, 1, 1);

        txfStartDato = new TextField();
        this.add(txfStartDato, 2, 1);
        txfStartDato.setEditable(false);

        Label lblSlutDato = new Label("Slutdato:");
        this.add(lblSlutDato, 1, 2);

        txfSlutDato = new TextField();
        this.add(txfSlutDato, 2, 2);
        txfSlutDato.setEditable(false);

        Label lblAlkoholProcent = new Label("Alkoholprocent:");
        this.add(lblAlkoholProcent, 1, 3);

        txfAlkoholProcent = new TextField();
        this.add(txfAlkoholProcent, 2, 3);
        txfAlkoholProcent.setEditable(false);

        Label lblIndhold = new Label("Indhold i liter:");
        this.add(lblIndhold, 1, 4);

        txfIndhold = new TextField();
        this.add(txfIndhold, 2, 4);
        txfIndhold.setEditable(false);

        Label lblMaltBatch = new Label("Maltbatch:");
        this.add(lblMaltBatch, 1, 5);
        GridPane.setValignment(lblMaltBatch, VPos.BASELINE);
        lblMaltBatch.setPadding(new Insets(4, 0, 4, 0));

        txfMaltBatch = new TextField();
        this.add(txfMaltBatch, 2, 5);
        txfMaltBatch.setEditable(false);

        Label lblTilførteFad = new Label("Tilførte fad:");
        this.add(lblTilførteFad, 1, 6);
        GridPane.setValignment(lblTilførteFad, VPos.BASELINE);
        lblTilførteFad.setPadding(new Insets(4, 0, 4, 0));

        txaTilførteFad = new TextArea();
        this.add(txaTilførteFad, 2, 6);
        txaTilførteFad.setPrefWidth(300);
        txaTilførteFad.setPrefHeight(100);
        txaTilførteFad.setEditable(false);

        HBox hbxButtons = new HBox(40);
        this.add(hbxButtons, 0, 7, 3, 1);
        hbxButtons.setPadding(new Insets(10, 0, 0, 0));
        hbxButtons.setAlignment(Pos.BASELINE_CENTER);

        Button btnCreate = new Button("Opret destillering");
        hbxButtons.getChildren().add(btnCreate);
        btnCreate.setOnAction(event -> this.createDestilleringAction());

        Button btnUpdate = new Button("Opdater destillering");
        hbxButtons.getChildren().add(btnUpdate);


        Button btnDelete = new Button("Fjern destillering");
        hbxButtons.getChildren().add(btnDelete);

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
    }

    public void updateControls() {
        Destillering destillering = lvwDestillering.getSelectionModel().getSelectedItem();
        txfStartDato.setText(destillering.getStartDato() == null ? "Ikke angivet" : destillering.getStartDato().toString());
        txfSlutDato.setText(destillering.getSlutDato() == null ? "Ikke angivet" : destillering.getSlutDato().toString());
        txfIndhold.setText(Double.toString(destillering.getMængde()));
        txfMaltBatch.setText(destillering.getMaltBatch());
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
        DestilleringWindow dia = new DestilleringWindow("Opret Destillering");
        dia.showAndWait();
        updateControls();

        lvwDestillering.getItems().setAll(Storage.getDestillater());
        int index = lvwDestillering.getItems().size() - 1;
        lvwDestillering.getSelectionModel().select(index);
    }
    private void seDestilleringHistorikAction() {
        Destillering destillering = lvwDestillering.getSelectionModel().getSelectedItem();
        HistorikWindow dia = new HistorikWindow("Fad nr. " + destillering.getSpiritBatch() + " historik", destillering);
        dia.showAndWait();
    }
    // ------------------------------------------------------------------------

}
