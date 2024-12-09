package GUI;

import Model.Reol;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class OpretHyldeWindow extends Stage {
    private TextField txfHyldeId;
    private TextField txfKapacitet;
    private Reol reol;
    public OpretHyldeWindow(String title, Reol reol) {
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);

        this.reol = reol;

        this.setTitle(title);
        GridPane pane = new GridPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        this.setScene(scene);
    }
    public OpretHyldeWindow(String title) {

    }

    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);

        Label lblHyldeId = new Label("Hylde ID:");
        pane.add(lblHyldeId, 0, 0);

        txfHyldeId = new TextField();
        pane.add(txfHyldeId, 1, 0);

        Label lblKapacitet = new Label("Kapacitet:");
        pane.add(lblKapacitet, 0, 1);

        txfKapacitet = new TextField();
        pane.add(txfKapacitet, 1, 1);

        HBox hbxButtons = new HBox(40);
        pane.add(hbxButtons, 0, 2);

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(event -> this.hide());

        Button okButton = new Button("Ok");
        okButton.setOnAction(event -> this.okButtonAction());

        hbxButtons.getChildren().addAll(cancelButton, okButton);
    }
    private void okButtonAction() {
        String reolId = txfHyldeId.getText();
        int kapacitet = Integer.parseInt(txfKapacitet.getText());
        reol.opretHylde(reolId, kapacitet);
        this.hide();
    }
}
