package com.example.bataille_navale_graphique;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Fenetre_de_fin_de_partie extends Parent {
    public static Stage mainWindow;

    public Fenetre_de_fin_de_partie(int vainqueur) {
        this.CreerFenetre(vainqueur);
    }

    public void CreerFenetre(int vainqueur){
        Pane root = new Pane();
        mainWindow = new Stage();
        mainWindow.setTitle("Bataille navale");
        mainWindow.setScene(new Scene(root, 200, 200));
        mainWindow.setResizable(false);

        if(vainqueur == 0){
            Text text = new Text("Vous avez perdu :(");
            text.setLayoutX(50);
            text.setLayoutY(50);
            root.getChildren().add(text);
        }

        if(vainqueur == 1){
            Text text = new Text("Vous avez gagné :)");
            text.setLayoutX(50);
            text.setLayoutY(50);
            root.getChildren().add(text);
        }

        mainWindow.show();
    }

}
