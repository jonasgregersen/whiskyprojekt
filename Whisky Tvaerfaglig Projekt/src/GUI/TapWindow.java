package GUI;

import Controller.Controller;
import Model.Fad;
import Model.Tapning;
import Storage.Storage;
import javafx.beans.value.ChangeListener;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Map;

public class TapWindow extends Stage {
    private Fad fad;

    public TapWindow(String title, Fad fad) {
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

    public TapWindow(String title) {
        this(title, null);
    }

    // -------------------------------------------------------------------------

    private TextField txfTapMængde, txfFadIndhold;
    private ListView<Tapning> lvwTapninger = new ListView<>();
    private TextArea txaTapIndhold;
    private Label lblError;

    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);

        Label lblTapninger = new Label("Tapningsbatches");
        pane.add(lblTapninger, 0, 0);

        pane.add(lvwTapninger, 0, 1);
        lvwTapninger.getItems().setAll(Storage.getTapninger());

        HBox indhold = new HBox(40);
        pane.add(indhold, 0, 2);

        Label lblFadIndhold = new Label("Mængde på fad:");

        txfFadIndhold = new TextField();
        txfFadIndhold.setText(Double.toString(fad.getNuværendeIndhold()));
        txfFadIndhold.setEditable(false);

        indhold.getChildren().addAll(lblFadIndhold, txfFadIndhold);

        HBox hbxMængde =  new HBox(40);
        pane.add(hbxMængde, 1, 2);

        Label lblTapMængde = new Label("Tapmængde:");

        txfTapMængde = new TextField();

        hbxMængde.getChildren().addAll(lblTapMængde, txfTapMængde);

        ChangeListener<Tapning> listener = (ov, oldTap, newTap) -> this.selectedTapningChanged();
        lvwTapninger.getSelectionModel().selectedItemProperty().addListener(listener);

        Label lblIndhold = new Label("Tap indhold");
        pane.add(lblIndhold, 1, 0);

        txaTapIndhold = new TextArea();
        pane.add(txaTapIndhold, 1,1);
        txaTapIndhold.setEditable(false);

        HBox hbxButtons = new HBox(40);
        pane.add(hbxButtons, 1, 4);

        Button btnCancel = new Button("Cancel");
        GridPane.setHalignment(btnCancel, HPos.LEFT);
        btnCancel.setOnAction(event -> this.cancelAction());

        Button btnOK = new Button("OK");
        GridPane.setHalignment(btnOK, HPos.RIGHT);
        btnOK.setOnAction(event -> this.okAction());

        hbxButtons.getChildren().addAll(btnCancel, btnOK);

        Button btnOpretTapning = new Button("Opret ny tapning");
        pane.add(btnOpretTapning, 0 , 4);
        GridPane.setHalignment(btnOpretTapning, HPos.LEFT);
        btnOpretTapning.setOnAction(event -> this.OpretTapningAction());

        lblError = new Label();
        pane.add(lblError, 0, 5);
        lblError.setStyle("-fx-text-fill: red");

        this.initControls();
    }

    private void initControls() {
        txfTapMængde.clear();
    }

    // -------------------------------------------------------------------------

    private void cancelAction() {
        this.hide();
    }

    private void okAction() {
        try {
            Tapning tapning = lvwTapninger.getSelectionModel().getSelectedItem();
            double tapningsMængde = Double.parseDouble(txfTapMængde.getText());
            Controller.tapFadEksisterendeTapning(tapning, fad, tapningsMængde);
            this.hide();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.setTitle("Tap fad");
            alert.setHeaderText("Kan ikke tappe fad");
            alert.showAndWait();
        }
    }
    private void OpretTapningAction() {
        NyTapningWindow dia = new NyTapningWindow("Opret tapning");
        dia.showAndWait();
        updateControls();
    }
    private void updateControls() {
        lvwTapninger.getItems().setAll(Storage.getTapninger());
        txfFadIndhold.setText(Double.toString(fad.getNuværendeIndhold()));
        Tapning selectedTapning = lvwTapninger.getSelectionModel().getSelectedItem();
        StringBuilder sbIndhold = new StringBuilder();
        for (Map.Entry<Fad, Double> entry : selectedTapning.getFadVæske().entrySet()) {
            sbIndhold.append("Fad nr. " + entry.getKey().getFadNr() + " tappet mængde: " + entry.getValue() + " liter.\n");
        }
        txaTapIndhold.setText(sbIndhold.toString());
    }

    private void selectedTapningChanged() {
        updateControls();
    }

}
