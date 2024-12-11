package GUI;

import Controller.Controller;
import Model.Fad;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.time.LocalDate;
import java.util.Date;

public class OpretDestilleringWindow extends Stage {

    public OpretDestilleringWindow(String title) {
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

    private TextField txfSpiritBatch;
    private DatePicker dpStartDato, dpSlutDato;

    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);
        pane.setPrefWidth(350);

        Label lblStartDato = new Label("Startdato:");
        pane.add(lblStartDato, 0, 0);

        dpStartDato = new DatePicker();
        pane.add(dpStartDato, 1, 0);

        Label lblSlutDato = new Label("Slutdato:");
        pane.add(lblSlutDato, 0, 1);

        dpSlutDato = new DatePicker();
        pane.add(dpSlutDato, 1, 1);

        Label lblSpiritBatch = new Label("Spiritbatch:");
        pane.add(lblSpiritBatch,0,2);

        txfSpiritBatch = new TextField();
        pane.add(txfSpiritBatch,1,2);

        HBox hbxButtons = new HBox(20);
        pane.add(hbxButtons,0,4);

        Button btnCancel = new Button("Cancel");
        btnCancel.setOnAction(event -> this.cancelAction());

        Button btnOK = new Button("OK");
        btnOK.setOnAction(event -> this.okAction());

        hbxButtons.getChildren().addAll(btnCancel, btnOK);


    }

    // -------------------------------------------------------------------------

    private void cancelAction() {
        this.hide();
    }

    private void okAction() {
        LocalDate startDato = dpStartDato.getValue();
        LocalDate slutDato = dpSlutDato.getValue();
        String spiritBatch = txfSpiritBatch.getText().trim();
        try {
            Controller.opretDestillering(startDato, slutDato, spiritBatch);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Opret destillering");
            alert.setHeaderText(spiritBatch + " oprettet.");
            alert.showAndWait();
            this.hide();
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.setTitle("Opret destillering");
            alert.setHeaderText("Kan ikke oprette destillering.");
            alert.showAndWait();
        }
    }

}
