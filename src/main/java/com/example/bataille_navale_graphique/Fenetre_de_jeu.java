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

/**
 * Class qui crée la fenêtre de jeu comprenant les deux grilles de jeu
 */
public class Fenetre_de_jeu extends Parent {

    /**
     * Fenêtre principale
     */
    public static Stage mainWindow;

    /**
     * Scene de jeu, contenu dans la fenêtre principale
     */
    public static Scene scene;

    /**
     * Text pour décrire au joueur les actions à réaliser
     */
    public static Text description;

    /**
     * Text pour indiquer au joueur quelle grille est la grille du Joueur
     */
    public static Text textGrilleJoueur;

    /**
     * Text pour indiquer au joueur quelle grille est la grille de l'Ordinateur
     */
    public static Text textGrilleOrdi;

    /**
     * Bar de menu en haut de l'écran pour contenir les options quitter, rejouer et tricher
     */
    MenuBar menuBar = new MenuBar();

    /**
     * Boite horizontale pour contenir les grilles
     */
    HBox hbox;

    /**
     * Boite horizontale pour contenir les texts
     */
    HBox hbox2;

    /**
     * Boite verticale pour contenir les deux boites horizontale
     */
    VBox vbox;

    /**
     * Variable Integer pour compter les bateaux lors de leur placement
     */
    int numeroDuBateau = 1;

    /**
     * Tableau d'entier pour contenir les tailles en nombre de cases de chaque bateau, pas de bateau a la position [0] car on commence avec le bateau numéro 1
     */
    int[] tailleBateaux = new int[]{ 0,5,4,3,3,2 };

    /**
     * Variable qui retient le sens du bateau lors de son placement, 1 = Horizontale et 2 = Verticale
     */
    int sensDuBateau = 1;

    /**
     * Constructeur de Fenetre_de_jeu, appel la fonction CreerFenetre()
     */
    public Fenetre_de_jeu() {
        this.CreerFenetre();
    }

    /**
     * Déclaration des grilles de jeu, de la classe Grille_de_jeu
     */
    public Grille_de_jeu grilleDeJeuJoueur, grilleDeJeuOrdi;

    //@Override

    /**
     * Fonction qui crée la fenêtre de jeu graphique
     */
    public void CreerFenetre() {

        //Créer un stage et une scene, la scène est créée à partir d'un border pane retourner par la fonction Jeu()
        mainWindow = new Stage();
        scene = new Scene(Jeu()); //Appel la fonction Jeu() qui contient le placement et les tirs sur les bateaux

        //Créer la bare de menu en haut de l'écran
        Menu menuHautDeLEcran = new Menu("Menu");
        MenuItem rejouer = new MenuItem("Rejouer");
        MenuItem quitter = new MenuItem("Quitter");
        MenuItem tricher = new MenuItem("Tricher");
        menuHautDeLEcran.getItems().addAll(rejouer,quitter,tricher);
        menuBar.getMenus().add(menuHautDeLEcran);


        //Créer les actions lors des cliques sur les items du menu

        //Bouton rejouer
        rejouer.setOnAction(e -> {
            //Remplir de zero les tableaux des grilles de jeu pour réinitialiser les positions des bateaux
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

            //Fermer la fenêtre de jeu en cours
            mainWindow.close();
            Fenetre_de_jeu.mainWindow.close();

            //Ouvrir une nouvelle fenêtre de jeu
            new Fenetre_de_jeu();
        });

        //Bouton quitter
        quitter.setOnAction(
                //Tuer le programme
                e -> {System.exit(0);
        });

        //Bouton tricher
        tricher.setOnAction(
                //Appeler la fonction tricher()
                e -> {tricher();
        });

        mainWindow.setTitle("Bataille navale");
        mainWindow.setScene(scene);
        mainWindow.setResizable(false);
        //Montrer la fenêtre de jeu
        mainWindow.show();

        //ROTATION DU BATEAU AVEC LA TOUCHE R
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    //Si un appui sur la touche R est détecté
                    case R : if(sensDuBateau == 1){
                        //Le sens du bateau devient verticale (2) s'il était précedement horizontale (1)
                        sensDuBateau = 2;
                    }
                    else{
                        //Le sens du bateau devient horizontale (1) s'il était précedement verticale (2)
                        sensDuBateau = 1;
                    }
                }
            }
        });
    }

    /**
     * Fonction contenant les mécanismes de jeu, c'est-à-dire création des grilles, placement des bateaux et tirs sur les bateaux
     * @return "root", qui est un border pane contenant les éléments de la scène (grille de jeu et textes)
     */
    public Parent Jeu(){
        //Créer le border pane
        BorderPane root = new BorderPane();
        root.setPrefSize(1200,800);

        //Texte description de l'action à réaliser
        description = new Text("Placer vos bateaux avec des clics dans la grille du joueur. Utiliser R pour effectuer une rotation du bateau");
        description.setStyle("-fx-font: 24 arial;");
        description.setTextAlignment(TextAlignment.CENTER);

        //Créer la grille du joueur
        grilleDeJeuJoueur = new Grille_de_jeu(false, event -> {

            //Si le jeu a deja commencé, on fait un return pour empêcher de placer d'autres bateaux
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

                cell = grilleDeJeuJoueur.recupererCell(cell.x, cell.y);

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

        //Créer la grille de jeu de l'ordinateur
        grilleDeJeuOrdi = new Grille_de_jeu(true, event -> {
            //Si le jeu n'a pas encore commencé (tous les bateaux ne sont pas placés) alors on ne peut rien faire sur la grille de l'ordinateur (return)
            if(grilleDeJeuOrdi.debutDuJeu != true){
                return;
            }
            //Si le jeu est commencé on peut cliquer sur la grille de l'ordinateur pour découvrir les bateaux
            else if(grilleDeJeuJoueur.finDePartie == false && grilleDeJeuOrdi.finDePartie == false)
            {

                Cell cell = (Cell) event.getSource();
                cell = grilleDeJeuOrdi.recupererCell(cell.x, cell.y);
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

    /**
     * Method tricher(), lis le tableau de position des bateaux de l'ordinateur est change de couleur les cases correspondantes.
     */
    public void tricher(){
        //Défiler tout le tableau de position des bateaux de l'ordinateur
        for(int i=0; i<10; i++){
            for(int j=0; j<10; j++){
                //Si un bateau est trouvé (soit un chiffre différent de l'eau(0), d'un tir a l'eau(7) et d'un bateau déjà touché(6)), alors on colorie la case correspondent en vert fluo sur la grille de l'ordinateur
                if(Grille_de_jeu.grilleOrdi[i][j] !=0 && Grille_de_jeu.grilleOrdi[i][j] !=6 && Grille_de_jeu.grilleOrdi[i][j] !=7){
                    grilleDeJeuOrdi.recupererCell(j,i).setFill(Color.GREENYELLOW);
                }
            }
        }
    }
}
