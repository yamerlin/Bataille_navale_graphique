package com.example.bataille_navale_graphique;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Class qui crée la fenêtre de fin de partie
 */
public class Fenetre_de_fin_de_partie extends Parent {
    public static Stage mainWindow;

    /**
     * Constructeur qui appelle la méthode CreerFenetre()
     * @param vainqueur Integer indiquant qui est le vainqueur de la partie (0 = Ordinateur, 1 = Joueur)
     */
    public Fenetre_de_fin_de_partie(int vainqueur) {
        this.CreerFenetre(vainqueur);
    }

    /**
     * Méthode utilisée pour créer la fenêtre de fin de partie
     * @param vainqueur Integer indiquant qui est le vainqueur de la partie (0 = Ordinateur, 1 = Joueur)
     */
    public void CreerFenetre(int vainqueur){
        Pane root = new Pane();
        mainWindow = new Stage();
        mainWindow.setTitle("Bataille navale");
        mainWindow.setScene(new Scene(root, 200, 200));
        mainWindow.setResizable(false);

        //Si le vainqueur est l'ordinateur
        if(vainqueur == 0){
            Text text = new Text("Vous avez perdu :(");
            text.setLayoutX(50);
            text.setLayoutY(50);
            root.getChildren().add(text);
        }

        //Si le vainqueur est le joueur
        if(vainqueur == 1){
            Text text = new Text("Vous avez gagné :)");
            text.setLayoutX(50);
            text.setLayoutY(50);
            root.getChildren().add(text);
        }

        //Bouton rejouer
        Button rejouer = new Button("Rejouer");
        rejouer.setLayoutX(70);
        rejouer.setLayoutY(100);
        rejouer.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {

                for(int i = 0; i < 10; i++) {
                    for(int j = 0; j < 10; j++) {
                        Grille_de_jeu.grilleJeu[i][j] = 0;
                    }
                }

                for(int i = 0; i < 10; i++) {
                    for(int j = 0; j < 10; j++) {
                        Grille_de_jeu.grilleOrdi[i][j] = 0;
                    }
                }


                mainWindow.close();
                Fenetre_de_jeu.mainWindow.close();
                new Fenetre_de_jeu();
            }
        });

        //Bouton quitter
        Button quitter = new Button("Quitter");
        quitter.setLayoutX(70);
        quitter.setLayoutY(150);
        quitter.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {System.exit(0);
            }
        });

        root.getChildren().addAll(rejouer,quitter);

        mainWindow.show();
    }

}
