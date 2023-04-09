package com.example.bataille_navale_graphique;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Fenetre_de_fin_de_partie extends Parent {
    public static Stage mainWindow;

    public Fenetre_de_fin_de_partie() {
        this.CreerFenetre();
    }

    public void CreerFenetre(){
        Pane root = new Pane();

        mainWindow = new Stage();
        mainWindow.setTitle("Bataille navale");
        mainWindow.setScene(new Scene(root, 800.0, 600.0));
        mainWindow.setResizable(false);
        mainWindow.show();
    }

}
