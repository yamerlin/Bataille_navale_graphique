package com.example.bataille_navale_graphique;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;

public class Fenetre_de_menu extends Application {
    public static Stage mainWindow;

    public Fenetre_de_menu() {
    }

    public void start(final Stage mainWindow) throws IOException {

        mainWindow.setTitle("Bataille navale");
        Pane root = new Pane();
        mainWindow.setScene(new Scene(root, 800.0, 600.0));
        mainWindow.setResizable(false);
        mainWindow.show();
        Button lancer_partie = new Button();
        lancer_partie.setPrefSize(200.0, 100.0);
        lancer_partie.setText("Jouer");
        lancer_partie.setStyle("-fx-font: 24 Helvetica;");
        lancer_partie.setLayoutX(300.0);
        lancer_partie.setLayoutY(50.0);
        lancer_partie.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                mainWindow.hide();
                new Fenetre_de_jeu();
            }
        });

        Button quitter = new Button();
        quitter.setPrefSize(200.0, 100.0);
        quitter.setText("Quitter");
        quitter.setStyle("-fx-font: 24 Helvetica;");
        quitter.setLayoutX(300.0);
        quitter.setLayoutY(220.0);
        quitter.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                System.exit(0);
            }
        });

        Image image = new Image(this.getClass().getResource("/images/battleship.png").toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setImage(image);
        imageView.setFitHeight(350.0);
        imageView.setFitWidth(700.0);
        imageView.setLayoutY(320.0);
        imageView.setLayoutX(30.0);
        root.setBackground(Background.fill(new LinearGradient(0.0, 0.0, 1.0, 1.0, true, CycleMethod.NO_CYCLE, new Stop[]{new Stop(0.0, Color.web("#2e86c1")), new Stop(1.0, Color.web("#fdfefe"))})));
        root.getChildren().addAll(new Node[]{imageView, lancer_partie, quitter});
    }


    public static void main(String[] args) {
        launch(new String[0]);
    }
}
