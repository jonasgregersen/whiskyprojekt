package GUI;

import Controller.Controller;
import Model.Destillering;
import Model.Lager;
import Model.Råvare;
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

public class RegistrerDestilleringWindow extends Stage {
    private Destillering destillering;
    private TextField txfMængde, txfAlkoholProcent, txfMaltBatch;
    private Råvare råvare;
    private double råvareMængde;
    private Lager lager;
    public RegistrerDestilleringWindow(String title, Destillering destillering) {
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

        Label lblMængde = new Label("Indholdsmængde:");
        pane.add(lblMængde,0,0);

        txfMængde = new TextField();
        pane.add(txfMængde, 1,0);

        Label lblAlkoholProcent = new Label("Alkoholprocent:");
        pane.add(lblAlkoholProcent, 0,1);

        txfAlkoholProcent = new TextField();
        pane.add(txfAlkoholProcent, 1,1);

        Button btnTilføjMalt = new Button("Tilføj råvare");
        pane.add(btnTilføjMalt,0,2);
        btnTilføjMalt.setOnAction(event -> this.tilføjRåvareAction());

        txfMaltBatch = new TextField();
        pane.add(txfMaltBatch,1,2);
        txfMaltBatch.setEditable(false);

        HBox hbxButtons = new HBox(20);
        pane.add(hbxButtons, 0,3);

        Button btnCancel = new Button("Cancel");
        btnCancel.setOnAction(event -> this.hide());

        Button btnOk = new Button("Ok");
        btnOk.setOnAction(event -> this.okAction());

        hbxButtons.getChildren().addAll(btnCancel, btnOk);
    }
    private void okAction() {
        double mængde = Double.parseDouble(txfMængde.getText());
        double alkoholProcent = Double.parseDouble(txfAlkoholProcent.getText().trim());
        try {
            Controller.registrerDestilleringsData(destillering, mængde, alkoholProcent);
            destillering.tilføjRåvare(lager, råvare, råvareMængde);
            this.hide();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.setTitle("Registrer data");
            alert.setHeaderText("Kan ikke registrere destilleringsdata");
            alert.showAndWait();
        }
    }
    private void tilføjRåvareAction() {
        TilførRåvareWindow dia = new TilførRåvareWindow("Tilfør råvare", destillering, this);
        dia.showAndWait();
    }
    public void setRåvare(Lager lager, Råvare råvare, double råvareMængde) {
        this.råvare = råvare;
        this.råvareMængde = råvareMængde;
        this.lager = lager;
        txfMaltBatch.setText(råvare.getMaltBatch());
    }
}
