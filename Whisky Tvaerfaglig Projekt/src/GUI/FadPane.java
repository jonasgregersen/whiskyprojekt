package GUI;

import Controller.Controller;
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

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

public class FadPane extends GridPane {
    private TextField txfIndhold, txfAlkoPct, txfDatoPåfyldning, txfSlutDato, txfKlarTilTapning, txfKapacitet;
    private TextArea txaDestilleringer, txaCustms, txaPlacering;
    private ListView<Fad> lvwFad;
    private ToggleButton tgbtnModneFad;

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
        this.add(lblIndhold, 1, 3);

        txfIndhold = new TextField();
        this.add(txfIndhold, 2, 3);
        txfIndhold.setEditable(false);

        Label lblKapacitet = new Label("Kapacitet:");
        this.add(lblKapacitet, 1,2);

        txfKapacitet = new TextField();
        this.add(txfKapacitet,2,2);
        txfKapacitet.setEditable(false);

        Label lblStartDato = new Label("Påfyldningsdato:");
        this.add(lblStartDato, 1, 1);

        txfDatoPåfyldning = new TextField();
        this.add(txfDatoPåfyldning, 2, 1);
        txfDatoPåfyldning.setEditable(false);

        Label lblKlarTilTapning = new Label("Status:");
        this.add(lblKlarTilTapning, 1, 4);

        txfKlarTilTapning = new TextField();
        this.add(txfKlarTilTapning, 2, 4);
        txfKlarTilTapning.setEditable(false);

        Label lblAlkoPct = new Label("Alkohol procent:");
        this.add(lblAlkoPct, 1, 5);

        txfAlkoPct = new TextField();
        this.add(txfAlkoPct, 2, 5);
        txfAlkoPct.setEditable(false);

        Label lblPlacering = new Label("Placering:");
        this.add(lblPlacering, 1, 6);
        GridPane.setValignment(lblPlacering, VPos.BASELINE);
        lblPlacering.setPadding(new Insets(4, 0, 4, 0));

        txaPlacering = new TextArea();
        this.add(txaPlacering, 2, 6);
        txaPlacering.setPrefHeight(60);
        txaPlacering.setPrefWidth(300);
        txaPlacering.setEditable(false);

        Label lblDestilleringer = new Label("Destilleringer");
        this.add(lblDestilleringer, 1, 7);
        GridPane.setValignment(lblDestilleringer, VPos.BASELINE);
        lblDestilleringer.setPadding(new Insets(4, 0, 4, 0));

        txaDestilleringer = new TextArea();
        this.add(txaDestilleringer, 2, 7);
        txaDestilleringer.setPrefWidth(300);
        txaDestilleringer.setPrefHeight(100);
        txaDestilleringer.setEditable(false);

        HBox hbxButtons = new HBox(40);
        this.add(hbxButtons, 0, 8, 3, 1);
        hbxButtons.setPadding(new Insets(10, 0, 0, 0));
        hbxButtons.setAlignment(Pos.BASELINE_CENTER);

        Button btnCreate = new Button("Opret fad");
        hbxButtons.getChildren().add(btnCreate);
        btnCreate.setOnAction(event -> this.createFadAction());

        Button btnPåfyldDato = new Button("Set påfyldningsdato");
        hbxButtons.getChildren().add(btnPåfyldDato);
        btnPåfyldDato.setOnAction(event -> this.setPåfyldningsDatoAction());

        Button btnPlacering = new Button("Set fadplacering");
        hbxButtons.getChildren().add(btnPlacering);
        btnPlacering.setOnAction(event -> this.setPlaceringAction());

        Button btnDelete = new Button("Fjern fad");
        hbxButtons.getChildren().add(btnDelete);
        btnDelete.setOnAction(event -> {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Bekræft handling");
            confirmationAlert.setHeaderText("Vil du slette fad?");

            ButtonType okButton = new ButtonType("Ok");
            ButtonType cancelButton = new ButtonType("Cancel");
            confirmationAlert.getButtonTypes().setAll(cancelButton, okButton);

            Optional<ButtonType> result = confirmationAlert.showAndWait();

            if (result.isPresent() && result.get() == okButton) {
                fjernFadAction();
            } else {
                confirmationAlert.hide();
            }
        });

        HBox hbxFadButtons = new HBox(40);
        this.add(hbxFadButtons, 0 , 5);

        Button btnHistorik = new Button("Se historik");
        btnHistorik.setOnAction(event -> this.seFadHistorikAction());

        Button btnTap = new Button("Tap fad");
        btnTap.setOnAction(event -> this.tapFadAction());

        tgbtnModneFad = new ToggleButton("Filter modne fad");
        tgbtnModneFad.setOnAction(event -> this.seModneFadAction());


        hbxFadButtons.getChildren().addAll(btnHistorik, btnTap, tgbtnModneFad);



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
        txfKapacitet.setText(Double.toString(fad.getKapacitet()));
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
        FadWindow dia = new FadWindow("Opret fad");
        dia.showAndWait();
        updateControls();

        lvwFad.getItems().setAll(Storage.getFade());
        int index = lvwFad.getItems().size() - 1;
        lvwFad.getSelectionModel().select(index);
    }
    private void setPlaceringAction() {
        Fad fad = lvwFad.getSelectionModel().getSelectedItem();
        setPlaceringWindow dia = new setPlaceringWindow("Set placering", fad);
        dia.showAndWait();
        updateControls();
    }
    private void fjernFadAction() {
        Fad fad = lvwFad.getSelectionModel().getSelectedItem();
        try {
            Controller.fjernFad(fad);
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Fjern fad");
            alert.setHeaderText("Kan ikke fjerne fad.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        lvwFad.getItems().setAll(Storage.getFade());
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
    private void seModneFadAction() {
        if (!tgbtnModneFad.isSelected()) {
            lvwFad.getItems().setAll(Storage.getFade());
        } else {
            ArrayList<Fad> modneFad = new ArrayList<>();
            for (Fad fad : Storage.getFade()) {
                if (fad.klarTilTapning()) {
                    modneFad.add(fad);
                }
            }
            lvwFad.getItems().setAll(modneFad);
        }
        lvwFad.refresh();
    }
    private void setPåfyldningsDatoAction() {
        Fad fad = lvwFad.getSelectionModel().getSelectedItem();
        SetPåfyldningsDatoWindow dia = new SetPåfyldningsDatoWindow("Set påfyldningsdato", fad);
        dia.showAndWait();
        updateControls();
    }
    // ------------------------------------------------------------------------

}
