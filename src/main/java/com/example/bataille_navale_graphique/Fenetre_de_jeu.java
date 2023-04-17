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
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
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
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;

import java.util.Random;

import com.example.bataille_navale_graphique.Grille_de_jeu.Cell;

public class Fenetre_de_jeu extends Parent {

    public static Stage mainWindow;
    public static Scene scene;
    public static Text description;
    public static Text textGrilleJoueur;
    public static Text textGrilleOrdi;
    MenuBar menuBar = new MenuBar();
    HBox hbox;
    HBox hbox2;
    VBox vbox;

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
        scene = new Scene(Jeu());

        //Créer la bar de menu en haut de l'écran
        Menu menuHautDeLEcran = new Menu("Menu");
        MenuItem rejouer = new MenuItem("Rejouer");
        MenuItem quitter = new MenuItem("Quitter");
        MenuItem tricher = new MenuItem("Tricher");
        menuHautDeLEcran.getItems().addAll(rejouer,quitter,tricher);
        menuBar.getMenus().add(menuHautDeLEcran);

        //Créer les actions lors des clicks sur les items du menu

        rejouer.setOnAction(e -> {
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
        });

        quitter.setOnAction(
                e -> {System.exit(0);
        });

        tricher.setOnAction(e -> {
            tricher();
        });

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

        description = new Text("Placer vos bateaux avec des clics dans la grille du joueur. Utiliser R pour effectuer une rotation du bateau");
        description.setStyle("-fx-font: 24 arial;");
        description.setTextAlignment(TextAlignment.CENTER);


        grilleDeJeuJoueur = new Grille_de_jeu(false, event -> {

            //Si le jeu a deja commencé, on fait un return pour empécher de placer d'autres bateaux
            if(grilleDeJeuOrdi.debutDuJeu == true){
                return;
            }

            //Sinon, on place les bateaux
            else if(grilleDeJeuOrdi.finDePartie == false && grilleDeJeuJoueur.finDePartie == false)
            {

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

                        description = new Text("Cliquer sur la grille ennemi pour tenter de toucher ses bateaux");
                        description.setStyle("-fx-font: 24 arial;");

                        vbox = new VBox(50, description, hbox, hbox2);

                        hbox.setAlignment(Pos.CENTER);
                        vbox.setAlignment(Pos.CENTER);

                        root.setCenter(vbox);
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
            else if(grilleDeJeuJoueur.finDePartie == false && grilleDeJeuOrdi.finDePartie == false)
            {

                Cell cell = (Cell) event.getSource();
                cell = grilleDeJeuOrdi.getCell(cell.x, cell.y);
                System.out.println("X : " + cell.x + "    Y : " + cell.y);

                //Faire tirer l'ordinateur
                grilleDeJeuJoueur.tirOrdi();

                //Vérifier si l'ordinateur n'a pas gagné juste avant de faire tirer le joueur
                if(grilleDeJeuJoueur.finDePartie == false){
                    grilleDeJeuOrdi.tirJoueur(cell.y, cell.x);
                }
            }
        });

        textGrilleJoueur = new Text("Grille du Joueur");
        textGrilleJoueur.setStyle("-fx-font: 24 arial;");

        textGrilleOrdi = new Text("Grille de l'Ennemi");
        textGrilleOrdi.setStyle("-fx-font: 24 arial;");

        hbox = new HBox(50, grilleDeJeuJoueur, grilleDeJeuOrdi);
        hbox2 = new HBox(400, textGrilleJoueur, textGrilleOrdi);
        vbox = new VBox(50, description, hbox, hbox2);

        hbox.setAlignment(Pos.CENTER);
        hbox2.setAlignment(Pos.CENTER);
        vbox.setAlignment(Pos.CENTER);

        root.setCenter(vbox);
        root.setTop(menuBar);


        return root;
    }

    public void tricher(){
        for(int i=0; i<10; i++){
            for(int j=0; j<10; j++){
                if(Grille_de_jeu.grilleOrdi[i][j] !=0 && Grille_de_jeu.grilleOrdi[i][j] !=6 && Grille_de_jeu.grilleOrdi[i][j] !=7){
                    grilleDeJeuOrdi.getCell(j,i).setFill(Color.GREENYELLOW);
                }
            }
        }
    }
}
