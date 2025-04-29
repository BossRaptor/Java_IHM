package vue;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class GridPaneFormulaireReservation extends GridPane {

    // Déclaration des composants du formulaire
    private DatePicker datePicker;
    private TextField nomCoursTextField;
    private ComboBox<String> heureDebutComboBox;
    private ComboBox<String> heureFinComboBox;
    private Button enregistrerButton;
    
    // Labels
    private Label dateLabel;
    private Label nomCoursLabel;
    private Label heureDebutLabel;
    private Label heureFinLabel;

    public GridPaneFormulaireReservation() {
        // Configuration de base du GridPane
        this.setPadding(new Insets(20));
        this.setHgap(10);
        this.setVgap(10);
        this.setAlignment(Pos.CENTER);
        
        // Pendant la mise au point - afficher les lignes de la grille
        this.setGridLinesVisible(true);
        
        // Initialisation des composants dans l'ordre d'ajout au formulaire
        
        // 1. Date de réservation - Ligne 0
        dateLabel = new Label("Date de réservation :");
        datePicker = new DatePicker(LocalDate.now());
        
        // Ajout des composants à la ligne 0
        this.add(dateLabel, 0, 0);
        this.add(datePicker, 1, 0);
        
        // 2. Nom du cours - Ligne 1
        nomCoursLabel = new Label("Nom du cours :");
        nomCoursTextField = new TextField();
        nomCoursTextField.setPromptText("Entrez le nom du cours");
        
        // Ajout des composants à la ligne 1
        this.add(nomCoursLabel, 0, 1);
        this.add(nomCoursTextField, 1, 1);
        
        // 3. Horaire de début - Ligne 2
        heureDebutLabel = new Label("Heure de début :");
        heureDebutComboBox = new ComboBox<>();
        
        // Remplissage du ComboBox avec des heures de 8h à 20h par tranches de 30 minutes
        populateHoursComboBox(heureDebutComboBox);
        
        // Ajout des composants à la ligne 2
        this.add(heureDebutLabel, 0, 2);
        this.add(heureDebutComboBox, 1, 2);
        
        // 4. Horaire de fin - Ligne 3
        heureFinLabel = new Label("Heure de fin :");
        heureFinComboBox = new ComboBox<>();
        
        // Remplissage du ComboBox avec des heures de 8h à 20h par tranches de 30 minutes
        populateHoursComboBox(heureFinComboBox);
        
        // Ajout des composants à la ligne 3
        this.add(heureFinLabel, 0, 3);
        this.add(heureFinComboBox, 1, 3);
        
        // 5. Bouton d'enregistrement - Ligne 4
        enregistrerButton = new Button("Enregistrer la réservation");
        enregistrerButton.setOnAction(e -> enregistrerReservation());
        
        // Ajout du bouton à la ligne 4, centré sur les deux colonnes
        this.add(enregistrerButton, 0, 4, 2, 1); // colonne, ligne, colspan, rowspan
        GridPane.setHalignment(enregistrerButton, javafx.geometry.HPos.CENTER);
        
        // Application de styles CSS
        this.getStyleClass().add("formulaire-reservation");
        enregistrerButton.getStyleClass().add("bouton-enregistrer");
    }
    
    /**
     * Remplit un ComboBox avec des heures de 8h00 à 20h00 par tranches de 30 minutes
     */
    private void populateHoursComboBox(ComboBox<String> comboBox) {
        LocalTime time = LocalTime.of(8, 0); // Début à 8h00
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        
        while (!time.isAfter(LocalTime.of(20, 0))) { // Jusqu'à 20h00
            comboBox.getItems().add(time.format(formatter));
            time = time.plusMinutes(30); // Incrémentation de 30 minutes
        }
        
        // Sélection par défaut
        comboBox.getSelectionModel().selectFirst();
    }
    
    /**
     * Méthode appelée lors du clic sur le bouton Enregistrer
     */
    private void enregistrerReservation() {
        // Récupération des valeurs
        LocalDate date = datePicker.getValue();
        String nomCours = nomCoursTextField.getText();
        String heureDebut = heureDebutComboBox.getValue();
        String heureFin = heureFinComboBox.getValue();

        // Validation basique
        if (nomCours == null || nomCours.trim().isEmpty()) {
            afficherErreur("Le nom du cours est obligatoire.");
            return;
        }

        // Vérification que l'heure de fin est après l'heure de début
        if (heureDebut != null && heureFin != null) {
            LocalTime debut = LocalTime.parse(heureDebut, DateTimeFormatter.ofPattern("HH:mm"));
            LocalTime fin = LocalTime.parse(heureFin, DateTimeFormatter.ofPattern("HH:mm"));

            if (!fin.isAfter(debut)) {
                afficherErreur("L'heure de fin doit être après l'heure de début.");
                return;
            }
        }

        // Affichage d'une confirmation (à remplacer par le code de sauvegarde réel)
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Réservation enregistrée");
        alert.setHeaderText("Votre réservation a été enregistrée avec succès");
        alert.setContentText(String.format(
                "Date: %s\nCours: %s\nHoraire: %s - %s",
                date.toString(), nomCours, heureDebut, heureFin));
        alert.showAndWait();
    }

    /**
     * Affiche un message d'erreur
     */
    private void afficherErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Une erreur est survenue");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
