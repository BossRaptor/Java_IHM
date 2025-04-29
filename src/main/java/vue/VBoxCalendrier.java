package vue;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.time.LocalDate;

/**
 * Calendrier simplifié
 */
public class VBoxCalendrier extends VBox {
    // Propriétés simples
    private int moisCourant;
    private int anneeCourante;
    private Label titreLabel;
    private GridPane grilleJours;
    private Label selectedLabel; // Label de la date sélectionnée
    private LocalDate selectedDate; // Date sélectionnée
    
    // Noms des mois pour l'affichage
    private final String[] NOMS_MOIS = {
        "Janvier", "Février", "Mars", "Avril", "Mai", "Juin",
        "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"
    };
    
    /**
     * Constructeur simple
     */
    public VBoxCalendrier() {
        super(10); // Espacement vertical
        
        // Obtenir la date d'aujourd'hui
        LocalDate aujourdhui = LocalDate.now();
        moisCourant = aujourdhui.getMonthValue() - 1; // 0-11 pour l'index du tableau
        anneeCourante = aujourdhui.getYear();
        selectedDate = aujourdhui;
        
        // Configuration de base
        this.setStyle("-fx-background-color: #1e1e1e; -fx-padding: 10px;");
        this.setAlignment(Pos.CENTER); // Centre tout le contenu
        this.setPrefWidth(400); // Définit une largeur préférée
        
        // Titre du calendrier
        Label titre = new Label("Calendrier");
        titre.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titre.setTextFill(Color.web("#60cdff"));
        this.getChildren().add(titre);
        
        // Titre du mois - centré
        titreLabel = new Label();
        titreLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        titreLabel.setTextFill(Color.WHITE);
        titreLabel.setAlignment(Pos.CENTER);
        titreLabel.setMaxWidth(Double.MAX_VALUE); // Permet au label de prendre toute la largeur
        this.getChildren().add(titreLabel);
        
        // Boutons de navigation
        HBox navigationBox = new HBox(10);
        navigationBox.setAlignment(Pos.CENTER);
        
        Button precedentBtn = new Button("←");
        precedentBtn.setStyle("-fx-background-color: #3d5a73; -fx-text-fill: #e0e0e0; -fx-font-size: 14px;");
        precedentBtn.setPrefSize(40, 40);
        precedentBtn.setOnAction(e -> {
            moisPrecedent();
            afficherMois();
        });
        // Effet au survol du bouton
        precedentBtn.setOnMouseEntered(e -> precedentBtn.setStyle("-fx-background-color: #60cdff; -fx-text-fill: #121212; -fx-font-size: 14px;"));
        precedentBtn.setOnMouseExited(e -> precedentBtn.setStyle("-fx-background-color: #3d5a73; -fx-text-fill: #e0e0e0; -fx-font-size: 14px;"));
        
        Button suivantBtn = new Button("→");
        suivantBtn.setStyle("-fx-background-color: #3d5a73; -fx-text-fill: #e0e0e0; -fx-font-size: 14px;");
        suivantBtn.setPrefSize(40, 40);
        suivantBtn.setOnAction(e -> {
            moisSuivant();
            afficherMois();
        });
        // Effet au survol du bouton
        suivantBtn.setOnMouseEntered(e -> suivantBtn.setStyle("-fx-background-color: #60cdff; -fx-text-fill: #121212; -fx-font-size: 14px;"));
        suivantBtn.setOnMouseExited(e -> suivantBtn.setStyle("-fx-background-color: #3d5a73; -fx-text-fill: #e0e0e0; -fx-font-size: 14px;"));
        
        // Placer les boutons à gauche et à droite du titre
        HBox moisNavigation = new HBox(20);
        moisNavigation.setAlignment(Pos.CENTER);
        moisNavigation.getChildren().addAll(precedentBtn, suivantBtn);
        this.getChildren().add(moisNavigation);
        
        // En-tête des jours de la semaine
        HBox joursEntete = new HBox(5);
        joursEntete.setAlignment(Pos.CENTER);
        
        String[] joursNoms = {"Lu", "Ma", "Me", "Je", "Ve", "Sa", "Di"};
        for (String jour : joursNoms) {
            Label jourLabel = new Label(jour);
            jourLabel.setPrefWidth(30);
            jourLabel.setAlignment(Pos.CENTER);
            jourLabel.setTextFill(Color.web("#60cdff"));
            jourLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
            joursEntete.getChildren().add(jourLabel);
        }
        
        this.getChildren().add(joursEntete);
        
        // Grille des jours
        grilleJours = new GridPane();
        grilleJours.setHgap(5);
        grilleJours.setVgap(5);
        grilleJours.setAlignment(Pos.CENTER);
        this.getChildren().add(grilleJours);
        
        // Afficher le mois courant
        afficherMois();
    }
    
    /**
     * Affiche le mois actuel
     */
    private void afficherMois() {
        // Mise à jour du titre
        titreLabel.setText(NOMS_MOIS[moisCourant] + " " + anneeCourante);
        
        // Vider la grille
        grilleJours.getChildren().clear();
        
        // Calculer le premier jour du mois
        LocalDate premierJour = LocalDate.of(anneeCourante, moisCourant + 1, 1);
        int jourSemaine = premierJour.getDayOfWeek().getValue(); // 1 = Lundi, 7 = Dimanche
        
        // Nombre de jours dans le mois
        int nombreJours = premierJour.lengthOfMonth();
        
        // Afficher les jours
        int ligne = 0;
        int colonne = jourSemaine - 1;
        
        for (int jour = 1; jour <= nombreJours; jour++) {
            final int jourFinal = jour;
            
            // Créer le label pour ce jour
            Label jourLabel = new Label(String.valueOf(jour));
            jourLabel.setPrefSize(30, 30);
            jourLabel.setAlignment(Pos.CENTER);
            
            // Style pour le jour
            jourLabel.setTextFill(Color.WHITE);
            jourLabel.setStyle("-fx-background-color: #333333; -fx-background-radius: 15;");
            
            // Vérifier si c'est aujourd'hui
            LocalDate dateJour = LocalDate.of(anneeCourante, moisCourant + 1, jour);
            if (dateJour.equals(LocalDate.now())) {
                jourLabel.setTextFill(Color.web("#3d5a73"));
                jourLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
            }
            
            // Si c'est la date sélectionnée
            if (dateJour.equals(selectedDate)) {
                jourLabel.setStyle("-fx-background-color: rgba(255, 140, 50, 0.2); -fx-background-radius: 15;");
                jourLabel.setTextFill(Color.web("#ff8c32"));
                jourLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
                selectedLabel = jourLabel;
            }
            
            // Ajouter un gestionnaire de clic
            jourLabel.setOnMouseClicked(e -> {
                // Désélectionner l'ancien jour
                if (selectedLabel != null) {
                    selectedLabel.setStyle("-fx-background-color: #333333; -fx-background-radius: 15;");
                    selectedLabel.setTextFill(Color.WHITE);
                    selectedLabel.setFont(Font.font("Arial", 12));
                }
                
                // Sélectionner le nouveau jour
                selectedLabel = jourLabel;
                jourLabel.setStyle("-fx-background-color: rgba(255, 140, 50, 0.2); -fx-background-radius: 15;");
                jourLabel.setTextFill(Color.web("#ff8c32"));
                jourLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
                
                // Mettre à jour la date sélectionnée
                selectedDate = LocalDate.of(anneeCourante, moisCourant + 1, jourFinal);
                
                // Notification de changement de date
                fireEvent(new DateEvent(this, selectedDate));
            });
            
            // Ajouter le label à la grille
            grilleJours.add(jourLabel, colonne, ligne);
            
            colonne++;
            if (colonne > 6) {
                colonne = 0;
                ligne++;
            }
        }
    }
    
    /**
     * Passe au mois précédent
     */
    private void moisPrecedent() {
        moisCourant--;
        if (moisCourant < 0) {
            moisCourant = 11;  // Décembre
            anneeCourante--;
        }
    }
    
    /**
     * Passe au mois suivant
     */
    private void moisSuivant() {
        moisCourant++;
        if (moisCourant > 11) {
            moisCourant = 0;  // Janvier
            anneeCourante++;
        }
    }
    
    /**
     * Retourne la date sélectionnée
     */
    public LocalDate getSelectedDate() {
        return selectedDate;
    }
    
    /**
     * Classe d'événement simple pour notifier du changement de date
     */
    public static class DateEvent extends javafx.event.Event {
        public static final javafx.event.EventType<DateEvent> DATE_CHANGED = 
            new javafx.event.EventType<>(javafx.event.Event.ANY, "DATE_CHANGED");
        
        private final LocalDate date;
        
        public DateEvent(VBoxCalendrier source, LocalDate date) {
            super(DATE_CHANGED);
            this.date = date;
        }
        
        public LocalDate getDate() {
            return date;
        }
    }
    
    /**
     * Ajoute un écouteur d'événement pour le changement de date
     */
    public void addDateChangeListener(javafx.event.EventHandler<DateEvent> handler) {
        this.addEventHandler(DateEvent.DATE_CHANGED, handler);
    }
}
