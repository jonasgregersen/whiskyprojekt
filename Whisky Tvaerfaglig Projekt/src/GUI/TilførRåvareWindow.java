package GUI;

import Model.Destillering;
import Model.Lager;
import Model.Råvare;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Map;

public class TilførRåvareWindow extends Stage {
    private Destillering destillering;
    private RegistrerDestilleringWindow registrerDestilleringWindow;
    private ListView<Lager> lvwLagre;
    private ListView<Råvare> lvwRåvarer;
    private TextField txfMængde, txfTilfør;

    public TilførRåvareWindow(String title, Destillering destillering, RegistrerDestilleringWindow registrerDestilleringWindow) {
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);

        this.registrerDestilleringWindow = registrerDestilleringWindow;
        this.destillering = destillering;
        this.setTitle(title);
        GridPane pane = new GridPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        this.setScene(scene);
    }

    private void initContent(GridPane pane) {

        pane.setPadding(new Insets(20));
        pane.setHgap(20);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);

        Label lblLagre = new Label("Lager");
        pane.add(lblLagre, 0, 0);

        lvwLagre = new ListView<>();
        pane.add(lvwLagre, 0, 1);
        lvwLagre.getItems().setAll(Storage.Storage.getLagre());

        ChangeListener<Lager> LagerListener = (ov, oldLager, newLager) -> this.updateControls();
        lvwLagre.getSelectionModel().selectedItemProperty().addListener(LagerListener);

        Label lblRåvarer = new Label("Råvarer");
        pane.add(lblRåvarer, 1, 0);

        lvwRåvarer = new ListView<>();
        pane.add(lvwRåvarer, 1, 1);

        ChangeListener<Råvare> råvareListener = (ov, oldRåvare, newRåvare) -> this.updateControls();
        lvwRåvarer.getSelectionModel().selectedItemProperty().addListener(råvareListener);
        HBox hbxMængde = new HBox(20);
        pane.add(hbxMængde,1,2);
        Label lblResterendeMængde = new Label("Mængde på lager:");
        hbxMængde.getChildren().add(lblResterendeMængde);

        txfMængde = new TextField();
        txfMængde.setEditable(false);
        hbxMængde.getChildren().add(txfMængde);

        HBox hbxTilføj = new HBox(20);
        pane.add(hbxTilføj,0,2);

        Label lblTilførMængde = new Label("Mængde tilføres");
        hbxTilføj.getChildren().add(lblTilførMængde);

        txfTilfør = new TextField();
        hbxTilføj.getChildren().add(txfTilfør);

        HBox hbxButtons = new HBox(40);
        pane.add(hbxButtons, 0, 3);

        Button btnCancel = new Button("Cancel");
        btnCancel.setOnAction(event -> this.hide());

        Button btnOk = new Button("Ok");
        btnOk.setOnAction(event -> this.okAction());

        hbxButtons.getChildren().addAll(btnCancel, btnOk);

    }

    private void updateControls() {
        Lager lager = lvwLagre.getSelectionModel().getSelectedItem();
        ObservableList<Råvare> råvarer = FXCollections.observableArrayList();
        for (Map.Entry<Råvare, Double> entry : lager.getRåvarer().entrySet()) {
            Råvare råvare = entry.getKey();
            råvarer.add(råvare);
        }
        lvwRåvarer.getItems().setAll(råvarer);
        Råvare råvare = lvwRåvarer.getSelectionModel().getSelectedItem();
        double mængde = lager.getRåvarer().get(råvare);
        txfMængde.setText(Double.toString(mængde));
    }
    private void okAction() {
        Lager lager = lvwLagre.getSelectionModel().getSelectedItem();
        Råvare råvare = lvwRåvarer.getSelectionModel().getSelectedItem();
        double råvareMængde = Double.parseDouble(txfMængde.getText());
        if (råvareMængde <= 0) {
            throw new IllegalArgumentException("Råvare mængden skal være over 0.");
        }
        registrerDestilleringWindow.setRåvare(lager, råvare, råvareMængde);
        this.hide();
    }
}