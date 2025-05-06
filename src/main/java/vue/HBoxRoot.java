package vue;

import controleur.Controleur;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import modele.Planning;
import modele.PlanningCollections;
import controleur.Controleur;

/**
 * Conteneur principal simplifié
 */
public class HBoxRoot extends HBox {
    // Composants principaux
    private VBoxCalendrier calendrier;
    private GridPaneFormulaireReservation formulaire;
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
        
        // S'assurer que les deux composants ont la même hauteur
        calendrier.setMinHeight(500);
        formulaire.setMinHeight(500);
        
        // Ajouter les composants à l'interface
        getChildren().addAll(calendrier, formulaire);
        
        // Connecter le calendrier au formulaire
        calendrier.addDateChangeListener(event -> {
            formulaire.setDate(event.getDate());
        });
        
        // Définir les largeurs préférées (50% chacun)
        calendrier.prefWidthProperty().bind(this.widthProperty().multiply(0.48));
        formulaire.prefWidthProperty().bind(this.widthProperty().multiply(0.48));
    }
}
