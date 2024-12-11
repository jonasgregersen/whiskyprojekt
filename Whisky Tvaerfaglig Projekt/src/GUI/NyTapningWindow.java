package GUI;

import Controller.Controller;
import Model.Fad;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class NyTapningWindow extends Stage {

    public NyTapningWindow(String title) {
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);

        this.setTitle(title);
        GridPane pane = new GridPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        this.setScene(scene);
    }

    // -------------------------------------------------------------------------

    private TextField txfTapningBatch, txfFortyndingsMængde;
    private Label lblError;

    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);

        Label lblTapningsBatch = new Label("Tapningsbatch:");
        pane.add(lblTapningsBatch, 0, 0);

        txfTapningBatch = new TextField();
        pane.add(txfTapningBatch, 0, 1);

        Label lblFortyndingsMængde = new Label("Fortyndingsmængde:");
        pane.add(lblFortyndingsMængde, 1, 0);

        txfFortyndingsMængde = new TextField();
        pane.add(txfFortyndingsMængde, 1, 1);


        Button btnCancel = new Button("Cancel");
        pane.add(btnCancel, 0, 4);
        GridPane.setHalignment(btnCancel, HPos.LEFT);
        btnCancel.setOnAction(event -> this.cancelAction());

        Button btnOK = new Button("OK");
        pane.add(btnOK, 0, 4);
        GridPane.setHalignment(btnOK, HPos.RIGHT);
        btnOK.setOnAction(event -> this.okAction());

        lblError = new Label();
        pane.add(lblError, 0, 5);
        lblError.setStyle("-fx-text-fill: red");

        this.initControls();
    }

    private void initControls() {
        txfFortyndingsMængde.clear();
        txfTapningBatch.clear();
    }

    // -------------------------------------------------------------------------

    private void cancelAction() {
        this.hide();
    }

    private void okAction() {
        try {
            String tapningsBatch = txfTapningBatch.getText();
            double fortyndingsMængde = Double.parseDouble(txfFortyndingsMængde.getText());
            Controller.opretTapning(tapningsBatch, fortyndingsMængde);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Opret tapning");
            alert.setHeaderText(tapningsBatch + " oprettet med fortyndingsmængde på " + fortyndingsMængde + " liter.");
            alert.showAndWait();
            this.hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
