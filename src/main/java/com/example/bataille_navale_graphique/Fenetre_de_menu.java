package com.example.bataille_navale_graphique;

import java.io.Console;
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

/**
 * Ce programme est un jeu de bataille navale contre un ordinateur qui tire aléatoirement, via une interface graphique utilisant JavaFX <br>
 * Réalisé dans le cadre du cours de programmation d'environnement en Java <br>
 * Auteur : MERLIN Yann <br>
 * Professeur : Samuel LINDSAY <br>
 * Date de remise : 19/04/2023 <br>
 * CEGEP de Sept-Iles <br>
 * Session d'hiver 2023
 *
 * @author Yann MERLIN
 * @version 1
 */

/**
 * Class Instanciée au début du programme, sert à créer la fenêtre principale de l'application, et à contenir la method START() qui est le point d'entrée de toute application JavaFX
 */
public class Fenetre_de_menu extends Application {
    public static Stage mainWindow;

    public Fenetre_de_menu() {
    }

    /**
     * Method START(), point d'entrée de toute application JavaFX
     *
     * @param mainWindow La fenêtre principale de l'application
     * @throws IOException
     */
    public void start(final Stage mainWindow) throws IOException {

        //Ajouter un titre
        mainWindow.setTitle("Bataille navale");

        //Créer la scene
        Pane root = new Pane();
        mainWindow.setScene(new Scene(root, 800.0, 600.0));
        mainWindow.setResizable(false);
        mainWindow.show();

        //Bouton pour lancer le jeu
        Button lancer_partie = new Button();
        lancer_partie.setPrefSize(200.0, 100.0);
        lancer_partie.setText("Jouer");
        lancer_partie.setStyle("-fx-font: 24 Helvetica;");
        lancer_partie.setLayoutX(300.0);
        lancer_partie.setLayoutY(50.0);
        lancer_partie.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                //Cacher la fenêtre de menu
                mainWindow.hide();

                //Instancier la fenêtre de jeu
                new Fenetre_de_jeu();
            }
        });

        //Bouton pour quitter le jeu
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

        //Une image de fond représentant un navire
        Image image = new Image(this.getClass().getResource("/images/battleship.png").toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setImage(image);
        imageView.setFitHeight(350.0);
        imageView.setFitWidth(700.0);
        imageView.setLayoutY(320.0);
        imageView.setLayoutX(30.0);

        //Un dégradé bleu pour le fond
        root.setBackground(Background.fill(new LinearGradient(0.0, 0.0, 1.0, 1.0, true, CycleMethod.NO_CYCLE, new Stop[]{new Stop(0.0, Color.web("#2e86c1")), new Stop(1.0, Color.web("#fdfefe"))})));

        //Ajouter tous les éléments à la scène
        root.getChildren().addAll(new Node[]{imageView, lancer_partie, quitter});
    }

    public static void main(String[] args) {
        launch(new String[0]);
    }
}
