package GUI;

import Controller.Controller;
import Model.Destillering;
import Model.Fad;
import Storage.Storage;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PåfyldWindow extends Stage {
    private ListView<Fad> fade;
    private TextField txfPåfyldMængde, txfFadIndhold, txfFadKapacitet;
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

        VBox vbxInfo = new VBox(20);
        pane.add(vbxInfo, 1, 1);

        HBox hbxKapacitet = new HBox(20);
        vbxInfo.getChildren().add(hbxKapacitet);

        Label lblFadKapacitet = new Label("Fad kapacitet:");
        hbxKapacitet.getChildren().add(lblFadKapacitet);

        txfFadKapacitet = new TextField();
        txfFadKapacitet.setEditable(false);
        hbxKapacitet.getChildren().add(txfFadKapacitet);

        HBox hbxIndhold = new HBox(20);
        vbxInfo.getChildren().add(hbxIndhold);

        Label lblFadIndhold = new Label("Fad nuværende indhold:");
        hbxIndhold.getChildren().add(lblFadIndhold);

        txfFadIndhold = new TextField();
        txfFadIndhold.setEditable(false);
        hbxIndhold.getChildren().add(txfFadIndhold);

        HBox hbxPåfyld = new HBox(20);
        pane.add(hbxPåfyld,0,2);

        Label lblPåfyldMængde = new Label("Påflydningsmængde:");
        hbxPåfyld.getChildren().add(lblPåfyldMængde);

        txfPåfyldMængde = new TextField();
        hbxPåfyld.getChildren().add(txfPåfyldMængde);

        HBox hbxButtons = new HBox(40);
        pane.add(hbxButtons, 0, 4);

        Button btnCancel = new Button("Cancel");
        btnCancel.setOnAction(event -> this.hide());

        Button btnOk = new Button("Ok");
        btnOk.setOnAction(event -> this.okAction());

        hbxButtons.getChildren().addAll(btnCancel, btnOk);

        ChangeListener<Fad> fadListener = (ov, oldFad, newFad) -> this.updateControls();
        fade.getSelectionModel().selectedItemProperty().addListener(fadListener);
        }
        private void okAction() {
        Fad fad = fade.getSelectionModel().getSelectedItem();
        double mængde = Double.parseDouble(txfPåfyldMængde.getText());
        try {
            Controller.tilførDestillatTilFad(destillering, fad, mængde);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Tilfør fad");
            alert.setHeaderText("Fadnr. " + fad.getFadNr() + " påfyldt med " + mængde + " liter fra " + destillering.getSpiritBatch());
            alert.showAndWait();
            this.hide();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.setTitle("Påfyld fad");
            alert.setHeaderText("Kan ikke påfylde fad.");
            alert.showAndWait();
        }
    }
    private void updateControls() {
        Fad fad = fade.getSelectionModel().getSelectedItem();
        txfFadKapacitet.setText(Double.toString(fad.getKapacitet()));
        txfFadIndhold.setText(Double.toString(fad.getNuværendeIndhold()));
        
    }
}
