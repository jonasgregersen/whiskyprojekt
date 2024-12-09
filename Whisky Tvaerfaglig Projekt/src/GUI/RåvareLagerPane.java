package GUI;

import Model.Lager;
import Model.Råvare;
import Storage.Storage;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RåvareLagerPane extends GridPane {
    private ListView<Lager> lvwLagre;
    private ListView<String> lvwRåvarer;

    public RåvareLagerPane() {
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        Label lblLagre = new Label("Lager");
        this.add(lblLagre, 0, 0);

        lvwLagre = new ListView<>();
        this.add(lvwLagre, 0, 1);
        lvwLagre.getItems().setAll(Storage.getLagre());

        ChangeListener<Lager> LagerListener = (ov, oldLager, newLager) -> this.updateControls();
        lvwLagre.getSelectionModel().selectedItemProperty().addListener(LagerListener);

        Label lblRåvarer = new Label("Råvarer");
        this.add(lblRåvarer, 1, 0);

        lvwRåvarer = new ListView<>();
        this.add(lvwRåvarer, 1, 1);

        Button btnOpretRåvare = new Button("Opret råvare");
        this.add(btnOpretRåvare, 0, 2);
        btnOpretRåvare.setOnAction(event -> this.opretRåvareAction());
    }

    private void opretRåvareAction() {
        Lager lager = lvwLagre.getSelectionModel().getSelectedItem();
        OpretRåvareWindow opretRåvareWindow = new OpretRåvareWindow("Opret råvare", lager);
        opretRåvareWindow.showAndWait();
        updateControls();
    }

    private void updateControls() {
        Lager lager = lvwLagre.getSelectionModel().getSelectedItem();
        ObservableList<String> råvarer = FXCollections.observableArrayList();
        for (Map.Entry<Råvare, Double> entry : lager.getRåvarer().entrySet()) {
            String råvare = entry.getKey() + ", Mængde: " + entry.getValue();
            råvarer.add(råvare);
        }
        lvwRåvarer.getItems().setAll(råvarer);
    }
}
