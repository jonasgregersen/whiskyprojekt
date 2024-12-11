package GUI;

import Controller.Controller;
import Model.Fad;
import Model.Lager;
import Model.Råvare;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class OpretRåvareWindow extends Stage {
    private Lager lager;

    public OpretRåvareWindow(String title, Lager lager) {
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);

        this.lager = lager;

        this.setTitle(title);
        GridPane pane = new GridPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        this.setScene(scene);
    }

    public OpretRåvareWindow(String title) {
        this(title, null);
    }

    // -------------------------------------------------------------------------

    private TextField txfMaltBatch, txfMængde;
    private ComboBox<Råvare.KornSort> cmbxKornSort;
    private Label lblError;

    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);

        Label lblMaltBatch = new Label("Maltbatch:");
        pane.add(lblMaltBatch, 0, 0);

        txfMaltBatch = new TextField();
        pane.add(txfMaltBatch, 0, 1);

        Label lblMængde = new Label("Mængde:");
        pane.add(lblMængde, 0, 2);

        txfMængde = new TextField();
        pane.add(txfMængde, 0, 3);

        Label lblKornSort = new Label("Kornsort:");
        pane.add(lblKornSort, 1, 0);

        cmbxKornSort = new ComboBox<Råvare.KornSort>();
        pane.add(cmbxKornSort, 1, 1);
        cmbxKornSort.getItems().setAll(Råvare.KornSort.values());

        Button btnCancel = new Button("Cancel");
        pane.add(btnCancel, 0, 4);
        GridPane.setHalignment(btnCancel, HPos.LEFT);
        btnCancel.setOnAction(event -> this.cancelAction());

        Button btnOK = new Button("OK");
        pane.add(btnOK, 0, 4);
        GridPane.setHalignment(btnOK, HPos.RIGHT);
        btnOK.setOnAction(event -> this.okAction());

        lblError = new Label();
        pane.add(lblError, 1, 4);
        lblError.setStyle("-fx-text-fill: red");
    }

    // -------------------------------------------------------------------------

    private void cancelAction() {
        this.hide();
    }

    private void okAction() {
        String maltBatchText = txfMaltBatch.getText().trim();
        int mængde = Integer.parseInt(txfMængde.getText());
        Råvare.KornSort kornSort = cmbxKornSort.getSelectionModel().getSelectedItem();
        try {
            Controller.opretRåvare(lager, kornSort, maltBatchText, mængde);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Opret råvare");
            alert.setHeaderText(maltBatchText + " - " + kornSort + " oprettet med mængde på " + mængde + ".");
            alert.showAndWait();
            this.hide();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.setTitle("Opret råvare");
            alert.setHeaderText("Kan ikke oprette råvare");
            alert.showAndWait();
        }
    }

}
