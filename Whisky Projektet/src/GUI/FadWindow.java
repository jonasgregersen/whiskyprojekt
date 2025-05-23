package GUI;

import Model.Fad;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FadWindow extends Stage {
    private Fad fad;

    public FadWindow(String title, Fad fad) {
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

    public FadWindow(String title) {
        this(title, null);
    }

    // -------------------------------------------------------------------------

    private TextField txfFadNr, txfKapacitet, txfIndkøbt;
    private ComboBox<Fad.FadType> cmbxFadType;
    private Label lblError;

    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);

        Label lblFadNr = new Label("FadNr");
        pane.add(lblFadNr, 0, 0);

        txfFadNr = new TextField();
        pane.add(txfFadNr, 0, 1);

        Label lblKapacitet = new Label("Kapacitet");
        pane.add(lblKapacitet, 0, 2);

        txfKapacitet = new TextField();
        pane.add(txfKapacitet, 0, 3);

        Label lblFadType = new Label("Fadtype");
        pane.add(lblFadType, 1, 0);

        cmbxFadType = new ComboBox<Fad.FadType>();
        pane.add(cmbxFadType, 1,1);
        cmbxFadType.getItems().setAll(Fad.FadType.values());

        Label lblIndkøbt = new Label("Indkøbt");
        pane.add(lblIndkøbt, 1, 3);

        txfIndkøbt = new TextField();
        pane.add(txfIndkøbt,1,4);


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
        if (fad != null) {
            txfFadNr.setText(Integer.toString(fad.getFadNr()));
            txfKapacitet.setText("" + fad.getKapacitet());
        } else {
            txfFadNr.clear();
            txfKapacitet.clear();
        }
    }

    // -------------------------------------------------------------------------

    private void cancelAction() {
        this.hide();
    }

    private void okAction() {
        int fadNr = Integer.parseInt(txfFadNr.getText());
        double kapacitet = Double.parseDouble(txfKapacitet.getText());
        Fad.FadType fadType = (Fad.FadType)cmbxFadType.getSelectionModel().getSelectedItem();
        String indkøbt = txfIndkøbt.getText();
        Controller.opretFad(fadNr, fadType, kapacitet, indkøbt);
        this.hide();
    }

}
