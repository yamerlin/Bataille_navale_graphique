package com.example.bataille_navale_graphique;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;

import java.util.Random;

import com.example.bataille_navale_graphique.Grille_de_jeu.Cell;

public class Fenetre_de_jeu extends Parent {

    public static Stage mainWindow;
    int numeroDuBateau = 1;
    int[] tailleBateaux = new int[]{ 0,5,4,3,3,2 };
    int sensDuBateau = 1;
    public Fenetre_de_jeu() {
        this.CreerFenetre();
    }
    public Grille_de_jeu grilleDeJeuJoueur, grilleDeJeuOrdi;

    //@Override
    public void CreerFenetre() {
        mainWindow = new Stage();
        Scene scene = new Scene(Jeu());

        mainWindow.setTitle("Bataille navale");
        mainWindow.setScene(scene);
        mainWindow.setResizable(false);
        mainWindow.show();

        //ROTATION DU BATEAU AVEC LA TOUCHE R
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case R : if(sensDuBateau == 1){
                        sensDuBateau = 2;
                    }
                    else{
                        sensDuBateau = 1;
                    }
                }
            }
        });
    }

    public Parent Jeu(){
        BorderPane root = new BorderPane();
        root.setPrefSize(1200,800);



        grilleDeJeuJoueur = new Grille_de_jeu(false, event -> {

            //Si le jeu a deja commencé, on fait un return pour empécher de placer d'autres bateaux
            if(grilleDeJeuOrdi.debutDuJeu == true){
                return;
            }

            //Sinon, on place les bateaux
            else {

                //Récupérer la cellule ou l'utilisateur clique (seulement sur la grille de jeu du joueur)
                Cell cell = (Cell) event.getSource();

                //Printer les coordonnées de cette cellule
                System.out.println("X : " + cell.x + "    Y : " + cell.y);

                cell = grilleDeJeuJoueur.getCell(cell.x, cell.y);

                //cell.setFill(Color.BLACK);

                //Créer le bateau, transmettre sa position a initGrilleJeu, puis tester s'il a pu être placé
                if (grilleDeJeuJoueur.initGrilleJeu(new Bateaux(tailleBateaux[numeroDuBateau], sensDuBateau, numeroDuBateau), cell.y, cell.x)) {


                    //Si le bateau a pu être placé, incrementer le compteur de bateaux placés
                    numeroDuBateau++;

                    //Si tous les bateaux du joueur ont été placés, placer les bateaux de l'ordinateur
                    if (numeroDuBateau == 6) {
                        grilleDeJeuOrdi.initGrilleOrdi();
                    }

                }
            }

        });

        grilleDeJeuOrdi = new Grille_de_jeu(true, event -> {
            //Si le jeu n'a pas encore commencé (tous les bateaux ne sont pas placés) alors on ne peut rien faire sur la grille de l'ordinateur (return)
            if(grilleDeJeuOrdi.debutDuJeu != true){
                return;
            }
            //Si le jeu est commencé on peut cliquer sur la grille de l'ordinateur pour découvrir les bateaux
            else{
                Cell cell = (Cell) event.getSource();
                cell = grilleDeJeuOrdi.getCell(cell.x, cell.y);
                System.out.println("X : " + cell.x + "    Y : " + cell.y);

                grilleDeJeuOrdi.tirJoueur(cell.y, cell.x);
                grilleDeJeuJoueur.tirOrdi();
            }
        });

        HBox hbox = new HBox(50, grilleDeJeuJoueur, grilleDeJeuOrdi);
        hbox.setAlignment(Pos.CENTER);

        root.setCenter(hbox);

        return root;
    }
}