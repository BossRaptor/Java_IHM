package vue;

import java.io.File;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

/**
 * Classe principale qui démarre l'application JavaFX
 */
public class PremiereApplication extends Application {

    public void start(@SuppressWarnings("exports") Stage stage) {
        // Récupération du fichier CSS pour le style
        File css = new File("css" + File.separator + "premierStyles.css");

        // Création du conteneur principal
        HBoxRoot root = new HBoxRoot();

        // Création de la scène avec le conteneur principal
        Scene scene = new Scene(root, 800, 600);
        // Ajout de la feuille de style CSS
        scene.getStylesheets().add(css.toURI().toString());

        // Configuration de la fenêtre principale
        stage.setScene(scene);
        stage.setTitle("Hello JavaFX");
        stage.show();
    }

    /**
     * Point d'entrée de l'application
     */
    public static void main(String[] args) {
        Application.launch();
    }
}