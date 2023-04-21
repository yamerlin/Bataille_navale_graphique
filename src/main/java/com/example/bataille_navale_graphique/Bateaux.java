package com.example.bataille_navale_graphique;

import javafx.scene.Parent;

public class Bateaux extends Parent {

    //Variable utilisée pour assigner son sens à un bateau
    int sensDuBateau; //1=Horizontale, 2=Verticale

    //Variable utilisée pour assigner son numéro à un bateau
    int numeroDuBateau;

    //Variable utilisée pour assigner sa taille à un bateau
    int tailleBateau; //5 ou 4 ou 3 ou 3 ou 2

    //Variable utilisée pour assigner son nom à un bateau
    String nomBateau;

    /**
     * Méthode utilisée pour retourner le nom du bateau
     * @param index Integer représentant la position du nom souhaité dans le tableau de noms
     * @return Une string qui est le nom du bateau demandé
     */
    public String FonctionNomBateau(int index){
        String[] tableauDeBateaux = new String[6];
        tableauDeBateaux[1] = "porte-avions";
        tableauDeBateaux[2] = "croiseur";
        tableauDeBateaux[3] = "contre-torpilleurs";
        tableauDeBateaux[4] = "sous-marin";
        tableauDeBateaux[5] = "torpilleur";

        return tableauDeBateaux[index];
    }

    /**
     * Constructeur de la classe bateau. Sert à créer un bateau
     * @param tailleBateau La taille du bateau
     * @param sensDuBateau Le sens du bateau
     * @param numeroDuBateau Le numéro du bateau
     */
    public Bateaux(int tailleBateau, int sensDuBateau, int numeroDuBateau){
        this.tailleBateau = tailleBateau;
        this.sensDuBateau = sensDuBateau;
        this.numeroDuBateau = numeroDuBateau;
        this.nomBateau = FonctionNomBateau(numeroDuBateau);
    }

}
