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

public class OpretReolWindow extends Stage {
    private TextField txfReolId;
    private Lager lager;
    public OpretReolWindow(String title, Lager lager) {
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
    public OpretReolWindow(String title) {

    }

    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);

        Label lblReolId = new Label("Reol ID:");
        pane.add(lblReolId, 0, 0);

        txfReolId = new TextField();
        pane.add(txfReolId, 1, 0);

        HBox hbxButtons = new HBox(40);
        pane.add(hbxButtons, 0, 1);

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(event -> this.hide());

        Button okButton = new Button("Ok");
        okButton.setOnAction(event -> this.okButtonAction());

        hbxButtons.getChildren().addAll(cancelButton, okButton);
    }
    private void okButtonAction() {
        String reolId = txfReolId.getText();
        lager.opretReol(reolId);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Opret reol");
        alert.setHeaderText("Reol: " + reolId + " oprettet.");
        alert.showAndWait();
        this.hide();
    }
}
