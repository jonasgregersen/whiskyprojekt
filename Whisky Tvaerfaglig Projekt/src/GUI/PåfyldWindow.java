package GUI;

import Controller.Controller;
import Model.Destillering;
import Model.Fad;
import Storage.Storage;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.concurrent.LinkedBlockingQueue;

public class PåfyldWindow extends Stage {
    private ListView<Fad> fade;
    private TextField txfMængde;
    private Destillering destillering;
    public PåfyldWindow(String title, Destillering destillering) {
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);

        this.destillering = destillering;

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

        Label lblFade = new Label("Fade");
        pane.add(lblFade, 0,0);

        fade = new ListView<>();
        pane.add(fade,0,1);
        fade.getItems().addAll(Storage.getFade());

        Label lblMængde = new Label("Påflydningsmængde:");
        pane.add(lblMængde, 1, 1);

        txfMængde = new TextField();
        pane.add(txfMængde, 2, 1);

        HBox hbxButtons = new HBox(40);
        pane.add(hbxButtons, 0, 4);

        Button btnCancel = new Button("Cancel");
        btnCancel.setOnAction(event -> this.hide());

        Button btnOk = new Button("Ok");
        btnOk.setOnAction(event -> this.okAction());

        hbxButtons.getChildren().addAll(btnCancel, btnOk);
        }
        private void okAction() {
        Fad fad = fade.getSelectionModel().getSelectedItem();
        double mængde = Double.parseDouble(txfMængde.getText());
        try {
            Controller.tilførDestilleringTilFad(destillering, fad, mængde);
            this.hide();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.setTitle("Påfyld fad");
            alert.setHeaderText("Kan ikke påfylde fad.");
            alert.showAndWait();
        }
    }
}
