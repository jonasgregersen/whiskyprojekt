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

public class FadPane extends GridPane {
    private TextField txfIndhold, txfAlkoPct, txfDatoPåfyldning, txfSlutDato, txfKlarTilTapning;
    private TextArea txaDestilleringer, txaCustms, txaPlacering;
    private ListView<Fad> lvwFad;

    public FadPane() {
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        Label lblFad = new Label("Fad");
        this.add(lblFad, 0, 0);

        lvwFad = new ListView<>();
        this.add(lvwFad, 0, 1, 1, 3);
        lvwFad.setPrefWidth(300);
        lvwFad.setPrefHeight(200);
        lvwFad.getItems().setAll(Storage.getFade());

        ChangeListener<Fad> listener = (ov, oldFad, newFad) -> this.selectedFadChanged();
        lvwFad.getSelectionModel().selectedItemProperty().addListener(listener);

        Label lblIndhold = new Label("Nuværende indhold:");
        this.add(lblIndhold, 1, 2);

        txfIndhold = new TextField();
        this.add(txfIndhold, 2, 2);
        txfIndhold.setEditable(false);

        Label lblStartDato = new Label("Påfyldningsdato:");
        this.add(lblStartDato, 1, 1);

        txfDatoPåfyldning = new TextField();
        this.add(txfDatoPåfyldning, 2, 1);
        txfDatoPåfyldning.setEditable(false);

        Label lblKlarTilTapning = new Label("Status:");
        this.add(lblKlarTilTapning, 1, 3);

        txfKlarTilTapning = new TextField();
        this.add(txfKlarTilTapning, 2, 3);
        txfKlarTilTapning.setEditable(false);

        Label lblAlkoPct = new Label("Alkohol procent:");
        this.add(lblAlkoPct, 1, 4);

        txfAlkoPct = new TextField();
        this.add(txfAlkoPct, 2, 4);
        txfAlkoPct.setEditable(false);

        Label lblPlacering = new Label("Placering:");
        this.add(lblPlacering, 1, 5);
        GridPane.setValignment(lblPlacering, VPos.BASELINE);
        lblPlacering.setPadding(new Insets(4, 0, 4, 0));

        txaPlacering = new TextArea();
        this.add(txaPlacering, 2, 5);
        txaPlacering.setPrefHeight(60);
        txaPlacering.setPrefWidth(300);
        txaPlacering.setEditable(false);

        Label lblDestilleringer = new Label("Destilleringer");
        this.add(lblDestilleringer, 1, 6);
        GridPane.setValignment(lblDestilleringer, VPos.BASELINE);
        lblDestilleringer.setPadding(new Insets(4, 0, 4, 0));

        txaDestilleringer = new TextArea();
        this.add(txaDestilleringer, 2, 6);
        txaDestilleringer.setPrefWidth(300);
        txaDestilleringer.setPrefHeight(100);
        txaDestilleringer.setEditable(false);

        HBox hbxButtons = new HBox(40);
        this.add(hbxButtons, 0, 7, 3, 1);
        hbxButtons.setPadding(new Insets(10, 0, 0, 0));
        hbxButtons.setAlignment(Pos.BASELINE_CENTER);

        Button btnCreate = new Button("Opret fad");
        hbxButtons.getChildren().add(btnCreate);
        btnCreate.setOnAction(event -> this.createFadAction());

        Button btnUpdate = new Button("Opdater fad");
        hbxButtons.getChildren().add(btnUpdate);


        Button btnDelete = new Button("Fjern fad");
        hbxButtons.getChildren().add(btnDelete);

        HBox hbxFadButtons = new HBox(40);
        this.add(hbxFadButtons, 0 , 4);

        Button btnHistorik = new Button("Se historik");
        btnHistorik.setOnAction(event -> this.seFadHistorikAction());

        Button btnTap = new Button("Tap fad");
        btnTap.setOnAction(event -> this.tapFadAction());

        hbxFadButtons.getChildren().addAll(btnHistorik, btnTap);



        if (lvwFad.getItems().size() > 0) {
            lvwFad.getSelectionModel().select(0);
        }
    }

    public void selectedFadChanged() {
        this.updateControls();
    }

    public void updateControls() {
        Fad fad = lvwFad.getSelectionModel().getSelectedItem();
        txfIndhold.setText(Double.toString(fad.getNuværendeIndhold()));
        txaPlacering.setText(fad.getPlacering() == null ? "Ikke angivet" : fad.getPlacering().toString());
        txfAlkoPct.setText(fad.getNuværendeIndhold() == 0 ? "Fadet er tomt" : Double.toString(Math.round(fad.beregnAlkoholProcent())));
        txfDatoPåfyldning.setText(fad.getDatoPåfyldning() == null ? "Ikke angivet" : fad.getDatoPåfyldning().toString());
        if (!fad.getDestillater().isEmpty()) {
            StringBuilder sbDest = new StringBuilder();
            for (Map.Entry<Destillering, Double> entry : fad.getDestillater().entrySet()) {
                sbDest.append(entry.getKey().getSpiritBatch() + " - påfyldt: " + entry.getValue() +
                        " liter, " + entry.getKey().getAlkoholProcent() + "%\n");
            }
            txaDestilleringer.setText(sbDest.toString());
        } else {
            txaDestilleringer.setText("Ingen destillater tilført fad");
        }
        txfKlarTilTapning.setText(fad.klarTilTapning() ? "Klar til tapning" : "Ikke klar til tapning");
    }

    private void createFadAction() {
        OpretDestilleringWindow dia = new OpretDestilleringWindow("Opret fad");
        dia.showAndWait();
        updateControls();

        lvwFad.getItems().setAll(Storage.getFade());
        int index = lvwFad.getItems().size() - 1;
        lvwFad.getSelectionModel().select(index);
    }
    private void seFadHistorikAction() {
        Fad fad = lvwFad.getSelectionModel().getSelectedItem();
        HistorikWindow dia = new HistorikWindow("Fad nr. " + fad.getFadNr() + " historik", fad);
        dia.showAndWait();
    }
    private void tapFadAction() {
        Fad fad = lvwFad.getSelectionModel().getSelectedItem();
        if (fad == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ingen Fad Valgt");
            alert.setHeaderText("Du skal vælge et fad, før du kan tappe.");
            alert.showAndWait();
            return;
        }

        System.out.println("Valgt fad: " + fad);

        try {
            TapWindow dia = new TapWindow("Tap fadnr. " + fad.getFadNr(), fad);
            dia.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fejl");
            alert.setHeaderText("Der opstod en fejl ved åbning af tapningsvinduet.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    // ------------------------------------------------------------------------

}
