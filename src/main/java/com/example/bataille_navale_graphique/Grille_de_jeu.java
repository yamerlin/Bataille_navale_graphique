package com.example.bataille_navale_graphique;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Cell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import java.util.ArrayList;
import java.util.List;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Grille_de_jeu extends Parent {

    public static int [][] grilleOrdi = new int[10][10];

    public static int [][] grilleJeu = new int[10][10];

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
        public boolean wasShot = false;

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

    public static void AfficherGrille(int grille[][]){

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
}
