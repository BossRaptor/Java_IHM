package vue;

import controleur.Controleur;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import modele.Planning;
import modele.PlanningCollections;

/**
 * Conteneur principal simplifié
 */
public class HBoxRoot extends HBox {
    // Composants principaux
    private VBoxCalendrier calendrier;
    private GridPaneFormulaireReservation formulaire;
    private static VBoxAffichagePlanning affichagePlanningPane; // Nouveau champ
    private static Planning planning;
    private static Controleur controleur;
    private VBoxCalendrier calendrierPane;
    private GridPaneFormulaireReservation reservationPane;



    /**
     * Constructeur
     */
    public HBoxRoot() {
        super(20); // 20 pixels d'espacement
        
        // Style du conteneur principal
        this.setStyle("-fx-background-color: #121212; -fx-padding: 20px;");
        this.setAlignment(Pos.CENTER); // Centrer les composants
        
        // Créer les composants
        calendrier = new VBoxCalendrier();
        formulaire = new GridPaneFormulaireReservation();
        affichagePlanningPane = new VBoxAffichagePlanning(); // Instanciation
        
        // S'assurer que les composants ont une hauteur minimale
        calendrier.setMinHeight(500);
        formulaire.setMinHeight(500);
        affichagePlanningPane.setMinHeight(500); // Hauteur pour le nouveau composant
        
        // Ajouter les composants à l'interface
        getChildren().addAll(calendrier, formulaire, affichagePlanningPane); // Ajout du nouveau composant
        
        // Connecter le calendrier au formulaire et à l'affichage du planning
        calendrier.addDateChangeListener(event -> {
            formulaire.setDate(event.getDate());
            affichagePlanningPane.updateSemaineLabel(event.getDate()); // Mettre à jour l'affichage planning
        });
        
        // Définir les largeurs préférées (ajustées pour trois composants)
        double largeurPart = 0.32; // Environ 1/3 pour chaque
        calendrier.prefWidthProperty().bind(this.widthProperty().multiply(largeurPart));
        formulaire.prefWidthProperty().bind(this.widthProperty().multiply(largeurPart));
        affichagePlanningPane.prefWidthProperty().bind(this.widthProperty().multiply(largeurPart));
    }

    // Accesseur pour affichagePlanningPane
    public static VBoxAffichagePlanning getAffichagePlanningPane() {
        return affichagePlanningPane;
    }
}
