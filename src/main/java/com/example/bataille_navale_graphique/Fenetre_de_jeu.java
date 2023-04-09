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
            //Récupérer la cellule ou l'utilisateur clique (seulement sur la grille de jeu du joueur)
            Cell cell = (Cell) event.getSource();

            //Printer les coordonnées de cette cellule
            System.out.println("X : " + cell.x + "    Y : " +cell.y);

            cell = grilleDeJeuJoueur.getCell(cell.x, cell.y);

            //cell.setFill(Color.BLACK);

            if(grilleDeJeuJoueur.initGrilleJeu(new Bateaux(tailleBateaux[numeroDuBateau], sensDuBateau, numeroDuBateau), cell.y, cell.x)){
                numeroDuBateau++;
                if(numeroDuBateau == 6){
                    grilleDeJeuOrdi.initGrilleOrdi();
                }
            }
        });

        grilleDeJeuOrdi = new Grille_de_jeu(true, event -> {
            Cell cell = (Cell) event.getSource();
        });

        HBox hbox = new HBox(50, grilleDeJeuJoueur, grilleDeJeuOrdi);
        hbox.setAlignment(Pos.CENTER);

        root.setCenter(hbox);

        return root;
    }
}