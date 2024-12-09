package GUI;

import Model.Lager;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
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
    }
}
