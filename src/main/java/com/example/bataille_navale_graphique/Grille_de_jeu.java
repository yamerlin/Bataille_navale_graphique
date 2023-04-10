package com.example.bataille_navale_graphique;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Cell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Grille_de_jeu extends Parent {
    boolean debutDuJeu = false;

    public boolean finDePartie = false;

    public static int [][] grilleOrdi = new int[10][10];

    public static int [][] grilleJeu = new int[10][10];

    /*
     * Objet utilisé pour la génération de nombres aléatoires
     */
    public static Random rand = new Random();

    /**
     * Fonction qui retourne un entier aléatoire compris entre a (inclus) et b (exclus).
     * @param a Entier borne inférieur
     * @param b Entier borne supérieur
     * @return Un nombre aléatoire compris entre a (inclus) et b (exclus).
     */
    public static int randRange(int a, int b){
        return rand.nextInt(b-a)+a;
    }

    private VBox rows = new VBox();

    public Grille_de_jeu(boolean ordinateur, EventHandler<? super MouseEvent> handler){

        for(int y = 0; y < 10; y++) {
            HBox row = new HBox();
            for (int x = 0; x < 10; x++) {
                Cell c = new Cell(x,y,this);
                c.setOnMouseClicked(handler);
                row.getChildren().add(c);
            }
            rows.getChildren().add(row);
        }
        getChildren().add(rows);
    }

    public class Cell extends Rectangle {
        public int x, y;
        private Grille_de_jeu grille;

        public Cell(int x, int y, Grille_de_jeu grille) {
            super(50, 50);
            this.x = x;
            this.y = y;
            this.grille = grille;
            setFill(Color.LIGHTBLUE);
            setStroke(Color.BLACK);
        }
    }

    public Cell getCell(int x, int y) {
        return (Cell)((HBox)rows.getChildren().get(y)).getChildren().get(x);
    }

    public boolean initGrilleJeu(Bateaux bateau,int l, int c){
        boolean isBateauPlacer = false;

        //PLACER LES BATEAUX DU JOUEUR

        //Si il est horizontale
        if(bateau.sensDuBateau == 1) {
            if (posOk(grilleJeu, l, c, bateau.sensDuBateau, bateau.tailleBateau) == true) {
                for (int i = c; i < c + bateau.tailleBateau; i++) {
                    Cell cell = getCell(i, l);
                    cell.setFill(Color.BLACK);
                    grilleJeu[l][i] = bateau.numeroDuBateau;
                }
                isBateauPlacer = true;
            }
        }

        //Si il est verticale
        if(bateau.sensDuBateau == 2) {
            if (posOk(grilleJeu, l, c, bateau.sensDuBateau, bateau.tailleBateau) == true) {
                for (int i = l; i < l + bateau.tailleBateau; i++) {
                    Cell cell = getCell(c, i);
                    cell.setFill(Color.BLACK);
                    grilleJeu[i][c] = bateau.numeroDuBateau;
                }
                isBateauPlacer = true;
            }
        }

        return isBateauPlacer;
    }

    public void initGrilleOrdi() {
        /*
         * Tableau contenant les tailles de chaque bateau, il commence à la position 1 au lieu de commencer à la position 0 pour correspondre au numéro du bateau (1,2,3,4 ou 5)
         */
        int[] tailleBateaux = new int[]{0,5,4,3,3,2};

        /*
         * On commence par assigner des valeurs aléatoires aux variables ligne, colonne et sens du bateau.
         */
        int l = randRange(0, 10);
        int c = randRange(0, 10);
        int d = randRange(1, 3);

        /*
         * On répète ensuite l'opération pour chacun des 5 bateaux, tant que leur position n'est pas validée par la fonction posOk
         */
        for (int i = 1; i < 6; i++) {
            while (posOk(grilleOrdi, l, c, d, tailleBateaux[i]) != true) {
                l = randRange(0, 10);
                c = randRange(0, 10);
                d = randRange(1, 3);
            }
            /*
             * Si la position est validée, on peut alors envoyer les informations du bateau à la fonction initBateaux qui va s'occuper de le placer sur la grille.
             */
            if (posOk(grilleOrdi, l, c, d, tailleBateaux[i]) == true) {
                initBateaux(grilleOrdi,l, c, d, tailleBateaux[i], i /*Numéro du bateau*/);
            }
        }

        debutDuJeu = true;
        AfficherGrilleEnConsole(grilleOrdi);
        AfficherGrilleEnConsole(grilleJeu);
    }

    public static boolean posOk(int [][]grille, int l, int c, int d, int t){
        boolean isPosOk = true;

        /*
         * On vérifie si le bateau est horizontale
         */
        if(d == 1){
            /*
             * On vérifie si la position + la longueur du bateau est plus petite que la taille de la grille
             */
            if(c+t-1 >= 10){
                isPosOk = false;
            }
            else{
                /*
                 * On vérifie sur chaque case il n'y a pas déjà un autre bateau
                 */
                for(int i = 0; i < t; i++){
                    if(grille[l][c+i] != 0){
                        isPosOk = false;
                    }
                }
            }
        }

        /*
         * On vérifie si le bateau est vertical
         */
        if(d == 2){
            if(l+t-1 >= 10){
                isPosOk = false;
            }
            else{
                for(int i = 0; i < t; i++){
                    if(grille[l+i][c] != 0){
                        isPosOk = false;
                    }
                }
            }
        }

        return isPosOk;
    }

    public static void initBateaux(int grille[][], int l, int c, int d, int t, int typeDeBateau){
        /*
         * On teste si le bateau est horizontale
         */
        if (d == 1){
            for(int i = 0; i<t; i++){
                /*
                 * On incrémente la coordonnée de la colonne, de 0 a la taille du bateau, pour remplacer chaque 0 par le numéro du bateau
                 */
                grille[l][c+i] = typeDeBateau;
            }
        }
        /*
         * On teste si le bateau est vertical
         */
        if (d == 2){
            for(int i = 0; i<t; i++){
                /*
                 * On incrémente la coordonnée de la ligne, de 0 a la taille du bateau, pour remplacer chaque 0 par le numéro du bateau
                 */
                grille[l+i][c] = typeDeBateau;
            }
        }
    }

    public static void AfficherGrilleEnConsole(int grille[][]){

        /*
         * Variable utilisée pour afficher le numéro de la ligne
         */
        int numero_de_la_ligne = 0;

        /*
         * Affichage des lettres des colonnes
         */
        System.out.print("   A B C D E F G H I J");

        /*
         * Défiler chaque ligne
         */
        for(int i = 0; i <10; i++){
            /*
             * Incrémenter le numéro de la ligne
             */
            numero_de_la_ligne++;

            /*
             * Aller a la ligne suivante
             */
            System.out.println("");
            System.out.print(numero_de_la_ligne);

            /*
             * Pour un affichage propre, on ne veut pas mettre d'espace apres le numéro 10, car il prend deja deux caractères de place
             */
            if(numero_de_la_ligne != 10){
                System.out.print(" ");
            }

            /*
             * Défiler chaque colonne
             */
            for(int j = 0; j <10; j++) {
                System.out.print(" ");

                /*
                 * Affichage de la grille
                 */
                System.out.print(grille[i][j]);
            }
        }
    }

    public void tirJoueur(int l, int c){
        if(vainqueur(grilleOrdi) == false && vainqueur(grilleJeu) == false) {
            if (grilleOrdi[l][c] != 0 && grilleOrdi[l][c] != 6) {
                System.out.println("Tu as touché l'ennemi !");
                Cell cell = getCell(c, l);
                cell.setFill(Color.RED);
                grilleOrdi[l][c] = 6;
            } else {
                if (grilleOrdi[l][c] == 0) {
                    System.out.println("Tu as tiré dans l'eau !");

                    Cell cell = getCell(c, l);
                    cell.setFill(Color.BLUE);
                }
            }
        }

        //Vérifier que la partie n'a pas été gagné au moment du tir du joueur
        vainqueur(grilleOrdi);
    }

    public void tirOrdi(){
        if(vainqueur(grilleJeu) == false && vainqueur(grilleOrdi) == false) {
            int l;
            int c;

            do {
                l = randRange(0, 10);
                c = randRange(0, 10);
            }
            while (grilleJeu[l][c] == 6 || grilleJeu[l][c] == 7);//Re tirer tant que la case a déjà été tiré

            if (grilleJeu[l][c] != 0 && grilleJeu[l][c] != 6 && grilleJeu[l][c] != 7) {
                System.out.println("L'ennemi t'as touché !");
                Cell cell = getCell(c, l);
                cell.setFill(Color.RED);
                grilleJeu[l][c] = 6;
            } else {
                if (grilleJeu[l][c] == 0) {
                    System.out.println("L'ennemi a tiré dans l'eau !");
                    Cell cell = getCell(c, l);
                    cell.setFill(Color.BLUE);
                    grilleJeu[l][c] = 7;
                }
            }
        }
    }

    public boolean vainqueur(int grille[][]){
        boolean isVainqueur = true;
        int quiEstLeVainqueur = 0;

        /*
         * On vérifie tout le tableau à la recherche d'un élément différent de 0 (de l'eau) ou 6 (un bateau coulé) qui signifierait que la partie n'est pas encore gagnée (un bateau toujours présent)
         */
        for(int i = 0; i<10; i++){
            for (int j=0; j<10; j++){
                if(grille[i][j] != 0 && grille[i][j] != 6 && grille[i][j] != 7){
                    isVainqueur = false;
                }
            }
        }

        if(isVainqueur == true){
            //Le vainqueur est l'ordinateur
            if(grille == grilleJeu){
                quiEstLeVainqueur = 0;
                finDePartie = true;
            }

            //Le vainqueur est le joueur
            if(grille == grilleOrdi){
                quiEstLeVainqueur = 1;
                finDePartie = true;
            }

            //Fenetre_de_jeu.mainWindow.hide();
            new Fenetre_de_fin_de_partie(quiEstLeVainqueur);
        }

        return isVainqueur;
    }
}
