package GUI;

import Model.Fad;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Date;

public class SetPåfyldningsDatoWindow extends Stage {
    private Fad fad;
    private DatePicker dpPåfyld;
    public SetPåfyldningsDatoWindow(String title, Fad fad) {
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);

        this.fad = fad;

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

        Label lblPåfyld = new Label("Påfyldningsdato:");
        lblPåfyld.setPrefWidth(200);
        pane.add(lblPåfyld,0,0);

        dpPåfyld = new DatePicker();
        pane.add(dpPåfyld, 1,0);

        HBox hbxButtons = new HBox(20);
        pane.add(hbxButtons,0,1);

        Button btnCancel = new Button("Cancel");
        btnCancel.setOnAction(event -> this.hide());

        Button btnOk = new Button("Ok");
        btnOk.setOnAction(event -> this.okAction());

        hbxButtons.getChildren().addAll(btnCancel, btnOk);
    }
    private void okAction() {
        try {
            fad.setDatoPåfyldning(dpPåfyld.getValue());
            this.hide();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.setTitle("Set påfyldningsdato");
            alert.setHeaderText("Kan ikke sætte påfyldningsdato.");
            alert.showAndWait();
        }
    }
}
