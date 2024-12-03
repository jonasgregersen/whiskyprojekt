package GUI;

import Model.Historik;
import Model.Historikable;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class HistorikWindow extends Stage {
    private TextArea txaHistorik;
    private Historikable historikable;
    public HistorikWindow(String title, Historikable historikable) {
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);

        this.historikable = historikable;

        this.setTitle(title);
        GridPane pane = new GridPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        this.setScene(scene);
    }
    public void initContent(GridPane pane) {
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);
        pane.setPrefWidth(1000);


        Label lblHist = new Label("Historik");
        pane.add(lblHist, 0,0);

        txaHistorik = new TextArea();
        pane.add(txaHistorik, 0, 1, 1, 3);
        txaHistorik.setPrefWidth(1000);
        txaHistorik.setPrefHeight(200);
        txaHistorik.setEditable(false);

        StringBuilder sbHist = new StringBuilder();

        for (Historik h : historikable.getHistorik()) {
            sbHist.append(h.toString() + "\n");
        }

        txaHistorik.setText(sbHist.toString());
    }
}
