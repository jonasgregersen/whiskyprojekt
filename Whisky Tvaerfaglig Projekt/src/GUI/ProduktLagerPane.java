package GUI;

import Model.Destillering;
import Model.Lager;
import Model.WhiskyProdukt;
import Storage.Storage;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.List;

public class ProduktLagerPane extends GridPane {
    private TextField txfPlacering;
    private ListView<WhiskyProdukt> lvwProdukter;
    private ListView<Lager> lvwLagre;
    private TextArea txaProduktHistorik;
    public ProduktLagerPane() {
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        Label lblLagre = new Label("Lager:");
        this.add(lblLagre, 0,0);

        lvwLagre = new ListView<>();
        this.add(lvwLagre, 0, 1);
        lvwLagre.getItems().setAll(Storage.getLagre());

        ChangeListener<Lager> lagerListener = (ov, oldLager, newLager) -> this.updateControls();
        lvwLagre.getSelectionModel().selectedItemProperty().addListener(lagerListener);

        Label lblProdukter = new Label("Produkter:");
        this.add(lblProdukter, 1,0);

        lvwProdukter = new ListView<>();
        this.add(lvwProdukter, 1, 1);

        ChangeListener<WhiskyProdukt> produktListener = (ov, oldProdukt, newProdukt) -> this.updateControls();
        lvwProdukter.getSelectionModel().selectedItemProperty().addListener(produktListener);

        Label lblHistorik = new Label("Produkt historik:");
        this.add(lblHistorik, 2,0);

        txaProduktHistorik = new TextArea();
        this.add(txaProduktHistorik,2,1);
        txaProduktHistorik.setEditable(false);

        Button btnOpretProdukt = new Button("Opret produkt");
        this.add(btnOpretProdukt, 0, 2);
        btnOpretProdukt.setOnAction(event -> this.opretProduktAction());


    }
    private void updateControls() {
        lvwProdukter.getItems().clear();
        Lager selectedLager = lvwLagre.getSelectionModel().getSelectedItem();
        if (selectedLager != null) {
            List<WhiskyProdukt> produkter = selectedLager.getProdukter();
            if (produkter != null && !produkter.isEmpty()) {
                lvwProdukter.getItems().setAll(produkter);
            }
        }
        WhiskyProdukt selectedProdukt = lvwProdukter.getSelectionModel().getSelectedItem();
        if (selectedProdukt != null) {
            txaProduktHistorik.setText(selectedProdukt.udskrivProduktionsProcess());
        } else {
            txaProduktHistorik.clear();
        }
    }
    private void opretProduktAction() {
        Lager lager = lvwLagre.getSelectionModel().getSelectedItem();
        OpretProduktWindow dia = new OpretProduktWindow("Opret produkt til " + lager.getNavn(), lager);
        dia.showAndWait();
        updateControls();
    }
}

