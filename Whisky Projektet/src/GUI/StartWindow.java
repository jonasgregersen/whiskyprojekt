package GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class StartWindow extends Application {


    @Override
    public void start(Stage stage) {
        stage.setTitle("Sall Whisky Distilleri");
        BorderPane pane = new BorderPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    // -------------------------------------------------------------------------

    private void initContent(BorderPane pane) {
        TabPane tabPane = new TabPane();
        this.initTabPane(tabPane);
        pane.setCenter(tabPane);
    }

    private void initTabPane(TabPane tabPane) {
        tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

        Tab tabFad = new Tab("Fad");
        tabPane.getTabs().add(tabFad);

        FadPane fadPane = new FadPane();
        tabFad.setContent(fadPane);
        tabFad.setOnSelectionChanged(event -> fadPane.updateControls());

        Tab tabEmployees = new Tab("Destilleringer");
        tabPane.getTabs().add(tabEmployees);


    }

}
