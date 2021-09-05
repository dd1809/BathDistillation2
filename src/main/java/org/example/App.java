package org.example;

import controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class.getResource("MainWindow.fxml"));
        Scene scene = new Scene(loader.load(), 1200, 660);
        scene.getStylesheets().add(getClass().getResource("Font.css").toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        View view = loader.getController();
        Controller controller = new Controller(view);
        view.setController(controller);
    }

    public static void main(String[] args) {
        launch();
    }
}

