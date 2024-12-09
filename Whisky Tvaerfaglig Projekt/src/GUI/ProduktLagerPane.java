package GUI;

import Model.WhiskyProdukt;
import Storage.Storage;
import javafx.geometry.Insets;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

import javafx.scene.control.Label;

public class ProduktLagerPane extends GridPane {
    private ListView<WhiskyProdukt> lvwProdukter;
    public ProduktLagerPane() {
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        Label lblProdukter = new Label("Produkter");
        this.add(lblProdukter, 0,0);

        lvwProdukter = new ListView<>();
        lvwProdukter.getItems().setAll(Storage.getProdukter());
        this.add(lvwProdukter, 0, 1);

    }
}
