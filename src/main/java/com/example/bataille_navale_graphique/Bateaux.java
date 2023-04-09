package com.example.bataille_navale_graphique;

import javafx.scene.Parent;

public class Bateaux extends Parent {
    int sensDuBateau; //1=Horizontale, 2=Verticale
    int numeroDuBateau;
    int tailleBateau; //5 ou 4 ou 3 ou 3 ou 2

    String nomBateau;

    public String FonctionNomBateau(int index){
        String[] tableauDeBateaux = new String[6];
        tableauDeBateaux[1] = "porte-avions";
        tableauDeBateaux[2] = "croiseur";
        tableauDeBateaux[3] = "contre-torpilleurs";
        tableauDeBateaux[4] = "sous-marin";
        tableauDeBateaux[5] = "torpilleur";

        return tableauDeBateaux[index];
    }

    public Bateaux(int tailleBateau, int sensDuBateau, int numeroDuBateau){
        this.tailleBateau = tailleBateau;
        this.sensDuBateau = sensDuBateau;
        this.numeroDuBateau = numeroDuBateau;
        this.nomBateau = FonctionNomBateau(numeroDuBateau);
    }

}
