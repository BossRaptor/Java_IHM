package vue;

import javafx.scene.layout.HBox;
import java.time.LocalDate;

/**
 * Conteneur principal de l'application
 * Organise horizontalement le calendrier et le formulaire de réservation
 */
public class HBoxRoot extends HBox {
     // Composant calendrier à afficher à gauche
     VBoxCalendrier calendrier = new VBoxCalendrier();
     // Formulaire de réservation à afficher à droite
     GridPaneFormulaireReservation formulaire = new GridPaneFormulaireReservation();

     /**
      * Constructeur qui initialise le conteneur principal
      */
     public HBoxRoot() {
          super(20); // Espacement horizontal de 20 pixels entre les composants
          
          // Ajout du calendrier à gauche
          getChildren().add(calendrier);
          
          // Ajout du formulaire de réservation à droite
          getChildren().add(formulaire);
          
          // Connexion entre le calendrier et le formulaire
          // Quand une date est sélectionnée dans le calendrier, elle est automatiquement 
          // mise à jour dans le formulaire
          calendrier.selectedDateProperty().addListener((observable, oldValue, newValue) -> {
               if (newValue != null) {
                    // Mettre à jour la date dans le formulaire
                    formulaire.setDate(newValue);
               }
          });
          
          // Initialisation avec la date actuelle
          LocalDate dateInitiale = calendrier.getSelectedDate();
          if (dateInitiale != null) {
               formulaire.setDate(dateInitiale);
          }
     }
}
