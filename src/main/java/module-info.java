module com.example.bataille_navale_graphique {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.bataille_navale_graphique to javafx.fxml;
    exports com.example.bataille_navale_graphique;
}