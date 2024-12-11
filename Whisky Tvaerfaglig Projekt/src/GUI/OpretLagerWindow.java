package GUI;

import Controller.Controller;
import Model.Lager;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class OpretLagerWindow extends Stage {
    private TextField txfLagerId, txfLagerNavn;
    public OpretLagerWindow(String title) {
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);

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

        Label lblLagerId = new Label("Lager ID:");
        pane.add(lblLagerId, 0,0);

        txfLagerId = new TextField();
        pane.add(txfLagerId, 1, 0);

        Label lblLagerNavn = new Label("Lager navn:");
        pane.add(lblLagerNavn, 0, 1);

        txfLagerNavn = new TextField();
        pane.add(txfLagerNavn, 1,1);

        HBox hbxButtons = new HBox(40);
        pane.add(hbxButtons, 0, 2);

        Button btnCancel = new Button("Cancel");
        btnCancel.setOnAction(event -> this.hide());

        Button btnOk = new Button("Ok");
        btnOk.setOnAction(event -> this.okButtonAction());

        hbxButtons.getChildren().addAll(btnCancel, btnOk);
    }
    private void okButtonAction() {
        int lagerId = Integer.parseInt(txfLagerId.getText());
        String lagerNavn = txfLagerNavn.getText().trim();
        Controller.opretLager(lagerId, lagerNavn);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Opret lager");
        alert.setHeaderText("Lager: " + lagerId + " - " + lagerNavn + " oprettet.");
        alert.showAndWait();
        this.hide();
    }

}
