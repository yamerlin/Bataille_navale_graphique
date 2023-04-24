package com.example.bataille_navale_graphique;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Cell;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * Class qui crée les grilles de jeux, composées de 10 x 10 cellules, où placer les bateaux et effectuer les tirs
 */
public class Grille_de_jeu extends Parent {
    /**
     * Variable booléenne indiquant si le jeu a commencé ou non
     */
    boolean debutDuJeu = false;

    /**
     * Variable booléenne indiquant si le jeu est terminé ou non
     */
    public boolean finDePartie = false;

    /**
     * Tableau 2D d'entiers contenant les positions des tirs reçus et des bateaux de l'ordinateur
     */
    public static int [][] grilleOrdi = new int[10][10];

    /**
     * Tableau 2D d'entiers contenant les positions des tirs reçus et des bateaux du joueur
     */
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

    /**
     * Boite verticale pour placer les lignes de cellules pour créer la grille de cellules à afficher
     */
    private VBox lignes = new VBox();

    /**
     * Constructeur utilisé pour créer les grille de jeu à afficher
     * @param ordinateur Variable booléenne indiquant s'il s'agit de la grille de l'ordinateur ou non
     * @param handler Variable utilisé pour détecter les cliques sur les grilles
     */
    public Grille_de_jeu(boolean ordinateur, EventHandler<? super MouseEvent> handler){

        /*HBox grille = new HBox();
        HBox rowABCD = new HBox();
        for(int i = 0; i<10; i++){
            String[] lettre = new String[]{"A","B","C","D","E","F","G","H","I","J"};
            Text ABCD = new Text(lettre[i]);
            ABCD.setStyle("-fx-font: 30 arial;");
            ABCD.setTextAlignment(TextAlignment.CENTER);

            StackPane pane = new StackPane(ABCD);
            pane.setMinSize(51,51);

            rowABCD.getChildren().add(pane);
        }
        grille.getChildren().add(rowABCD);*/

        for(int y = 0; y < 10; y++) {
            //Créer 10 lignes
            HBox ligne = new HBox();

            for (int x = 0; x < 10; x++) {
                //Crée 10 cellules et lui transmettre les variables x et y des boucles for pour qu'elle les utilise en tant que coordonnées de la cellule
                Cell c = new Cell(x,y,this);

                //Détecter les cliques
                c.setOnMouseClicked(handler);

                //Ajouter 10 cellules dans chaque ligne
                ligne.getChildren().add(c);
            }

            //Ajouter les dix lignes (elles se mettent automatiquement les unes sous les autres verticalement dans une boite verticale).
            lignes.getChildren().add(ligne);
        }

        getChildren().add(lignes);

    }

    /**
     * Class interne à Grille_de_jeu qui défini les objets cellules composant les grilles de jeux. Basé sur la class Rectangle.
     */
    public class Cell extends Rectangle {

        //Coordonnés de la cellule
        public int x, y;

        //Utilisé pour définir à quelle grille (joueur ou ordinateur) appartient la cellule
        private Grille_de_jeu grille;

        /**
         * Constructeur de cellule
         * @param x Coordonné x de la cellule
         * @param y Coordonné y de la cellule
         * @param grille Grille a laquelle appartient la cellule
         */
        public Cell(int x, int y, Grille_de_jeu grille) {
            //Super est utilisé pour accéder le constructeur de rectangle, et donc créer un rectangle de 50 par 50 pixels
            super(50, 50);

            //Assigner la coordonnée x a l'objet
            this.x = x;

            //Assigner la coordonnée y a l'objet
            this.y = y;

            //Assigner une grille à l'objet
            this.grille = grille;

            //Remplissage bleu clair de la cellule
            setFill(Color.LIGHTBLUE);

            //Bord noir de la cellule
            setStroke(Color.BLACK);
        }
    }

    /**
     * Method utilisé pour charger les image des bateaux
     * @param partieDuBateau Integer indiquant la partie du bateau à récupérer (0 = head, 1 = body, 2 = tail)
     * @return Retourne l'image du bateau que l'on a choisi
     */
    public Image ImagesDesBateaux(int partieDuBateau){
        //Charger les images des bateaux
        Image head = new Image(this.getClass().getResource("/images/head.png").toExternalForm());
        Image body = new Image(this.getClass().getResource("/images/body.png").toExternalForm());
        Image tail = new Image(this.getClass().getResource("/images/tail.png").toExternalForm());

        //Créer un tableau d'images
        Image[] tableauImagesDesBateaux = new Image[3];

        //Placer les images dans le tableau
        tableauImagesDesBateaux[0] = head;
        tableauImagesDesBateaux[1] = body;
        tableauImagesDesBateaux[2] = tail;

        //Retourner l'image de bateau choisie du tableau
        return tableauImagesDesBateaux[partieDuBateau];
    }

    /**
     * Methode utilisée pour récupérer les coordinates x et y de la cellule
     * @param x Coordonné x de la cellule à récupérer
     * @param y Coordonné y de la cellule à récupérer
     * @return Retourner la cellule
     */
    public Cell recupererCell(int x, int y) {
        return (Cell)((HBox)lignes.getChildren().get(y)).getChildren().get(x);
    }

    /**
     * Méthode utilisée pour initialiser la grille de jeu, c'est-à-dire pour permettre au joueur de positionner ses bateaux
     * @param bateau Objet de type bateau, contenant sa taille, son nom, son sens et son numéro
     * @param l Integer représentant la ligne sur laquelle l'utilisateur veux placer son bateau
     * @param c Integer représentant la colonne sur laquelle l'utilisateur veux placer son bateau
     * @return Retourner la variable booléenne "isBateauPlacer" pour indiquer si le bateau a pu être placé à la position désirée ou non
     */
    public boolean initGrilleJeu(Bateaux bateau,int l, int c){

        //variable booléenne indiquant si le bateau a pu être placé à la position désirée ou non
        boolean isBateauPlacer = false;


        //PLACER LES BATEAUX DU JOUEUR

        //Si il est horizontale
        if(bateau.sensDuBateau == 1) {
            //Si la position est correcte, validée par la fonction posOk
            if (posOk(grilleJeu, l, c, bateau.sensDuBateau, bateau.tailleBateau) == true) {
                //Placer le numéro du bateau dans tous les emplacements du tableau 2D sur lesquelles le bateau est
                for (int i = c; i < c + bateau.tailleBateau; i++) {
                    grilleJeu[l][i] = bateau.numeroDuBateau;
                }

                //Récupérer la cellule correspondante et lui assigner l'image de la tete du bateau
                Cell cell = recupererCell(c, l);
                cell.setFill(new ImagePattern(ImagesDesBateaux(0)));
                cell.setRotate(-90);

                //Récupérer les cellules suivantes et leur assigner les images du corps du bateau
                for(int i = c + 1; i < c + bateau.tailleBateau - 1; i++){
                    Cell cell2 = recupererCell(i, l);
                    cell2.setFill(new ImagePattern(ImagesDesBateaux(1)));
                    cell2.setRotate(-90);
                }

                //Récupérer la dernière cellule et lui assigner l'image de la queue du bateau
                Cell cell3 = recupererCell(c + bateau.tailleBateau - 1, l);
                cell3.setFill(new ImagePattern(ImagesDesBateaux(2)));
                cell3.setRotate(-90);


                isBateauPlacer = true;
            }
        }

        //Si il est verticale
        if(bateau.sensDuBateau == 2) {
            //Si la position est correcte, validée par la fonction posOk
            if (posOk(grilleJeu, l, c, bateau.sensDuBateau, bateau.tailleBateau) == true) {
                //Placer le numéro du bateau dans tous les emplacements du tableau 2D sur lesquelles le bateau est
                for (int i = l; i < l + bateau.tailleBateau; i++) {
                    grilleJeu[i][c] = bateau.numeroDuBateau;
                }

                //Récupérer la cellule correspondante et lui assigner l'image de la tete du bateau
                Cell cell = recupererCell(c, l);
                cell.setFill(new ImagePattern(ImagesDesBateaux(0)));

                //Récupérer les cellules suivantes et leur assigner les images du corps du bateau
                for(int i = l + 1; i < l + bateau.tailleBateau - 1; i++){
                    Cell cell2 = recupererCell(c, i);
                    cell2.setFill(new ImagePattern(ImagesDesBateaux(1)));
                }

                //Récupérer la dernière cellule et lui assigner l'image de la queue du bateau
                Cell cell3 = recupererCell(c, l + bateau.tailleBateau - 1);
                cell3.setFill(new ImagePattern(ImagesDesBateaux(2)));


                isBateauPlacer = true;
            }
        }

        return isBateauPlacer;
    }

    /**
     * Fonction qui place aléatoirement les bateaux de l'ordinateur sur sa grille.
     * Utilise posOk pour valider la position de chaque bateau.
     * Elle ne retourne rien puisqu'elle modifie seulement (indirectement, grâce à la fonction initBateaux) la grille de l'ordinateur qui est une variable commune à toute la classe bataille.
     */
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
        //AfficherGrilleEnConsole(grilleOrdi);
        //AfficherGrilleEnConsole(grilleJeu);
    }

    /**
     * Fonction utilisée pour l'initialisation des grilles. Elle teste si les positions choisies pour les bateaux sont correctes.
     * C'est-à-dire si le bateau ne dépasse pas de la grille ou s'il ne se superpose pas à un autre bateau.
     *
     * @param grille Grille sur laquelle le bateau est positionné
     * @param l Entier compris entre 1 et 10 indiquant le numéro de ligne
     * @param c Entier compris entre 1 et 10 indiquant le numéro de colonne
     * @param d Entier compris entre 1 et 2 indiquant le sens du bateau
     * @param t Entier compris entre 2 et 5 indiquant la taille du bateau
     * @return isPosOk booléen indiquant si la position du bateau est valide ou non
     */
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

    /**
     *Fonction qui place les bateaux sur la grille, en modifiant les 0 par les numéros des bateaux.
     * @param grille Grille sur laquelle on doit placer le bateau
     * @param l Entier compris entre 1 et 10 indiquant le numéro de ligne
     * @param c Entier compris entre 1 et 10 indiquant le numéro de colonne
     * @param d Entier compris entre 1 et 2 indiquant le sens du bateau
     * @param t Taille
     * @param typeDeBateau Numéro correspondant au type du bateau à placer
     */
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

    /**
     * Methode de débogage utilisé pour afficher en console les grilles de jeu
     * @param grille La grille à afficher
     */
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

    /**
     * Méthode utilisée pour permettre au joueur d'effectuer un tir sur la grille de l'ordinateur
     * @param l Integer représentant le numéro de la ligne où tirer
     * @param c Integer représentant le numéro de la colonne où tirer
     */
    public void tirJoueur(int l, int c){
        //On vérifie si la partie n'a pas encore été gagnée
        if(vainqueur(grilleOrdi) == false && vainqueur(grilleJeu) == false) {
            //On vérifie si le tir a touché un bateau de l'ordinateur
            if (grilleOrdi[l][c] != 0 && grilleOrdi[l][c] != 6) {
                System.out.println("Tu as touché l'ennemi !");
                Cell cell = recupererCell(c, l);
                cell.setFill(Color.RED);
                grilleOrdi[l][c] = 6;
            }
            //Sinon le tir est dans l'eau
            else {
                if (grilleOrdi[l][c] == 0) {
                    System.out.println("Tu as tiré dans l'eau !");

                    Cell cell = recupererCell(c, l);
                    cell.setFill(Color.BLUE);
                }
            }
        }

        //Vérifier que la partie n'a pas été gagné au moment du tir du joueur
        vainqueur(grilleOrdi);
    }

    /**
     * Méthode utilisée pour permettre à l'ordinateur d'effectuer un tir sur la grille du joueur
     */
    public void tirOrdi(){
        //On vérifie si la partie n'a pas encore été gagnée
        if(vainqueur(grilleJeu) == false && vainqueur(grilleOrdi) == false) {
            //On définit 2 Integer pour les coordonnées du tir puis on leur assigne une valeur aléatoire jusqu'à en trouver une sur laquelle aucun tir n'a encore été effectué
            int l;
            int c;

            do {
                l = randRange(0, 10);
                c = randRange(0, 10);
            }
            while (grilleJeu[l][c] == 6 || grilleJeu[l][c] == 7);//Re tirer tant que la case a déjà été tiré, pour être sûr que l'IA touche une case différente à chaque tir

            //On vérifie si le tir a touché un bateau du joueur
            if (grilleJeu[l][c] != 0 && grilleJeu[l][c] != 6 && grilleJeu[l][c] != 7) {
                System.out.println("L'ennemi t'as touché !");
                Cell cell = recupererCell(c, l);
                cell.setFill(Color.RED);
                grilleJeu[l][c] = 6;
            }
            //Sinon le tir est dans l'eau
            else {
                if (grilleJeu[l][c] == 0) {
                    System.out.println("L'ennemi a tiré dans l'eau !");
                    Cell cell = recupererCell(c, l);
                    cell.setFill(Color.BLUE);
                    grilleJeu[l][c] = 7;
                }
            }
        }
    }

    /**
     * Vérifier si une grille est gagnante
     * @param grille Grille à vérifier
     * @return Une variable booléenne qui indique si la partie a été gagnée
     */
    public boolean vainqueur(int grille[][]){
        boolean isVainqueur = true;
        int quiEstLeVainqueur = 0;

        /*
         * On vérifie tout le tableau à la recherche d'un élément différent de 0 (de l'eau) ou 6 (un bateau coulé) qui signifierait que la partie n'est pas encore gagnée (un bateau toujours présent), ou 7, qui signifie un tir dans l'eau
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
