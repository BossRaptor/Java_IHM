package vue;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import java.time.LocalDate;
import javafx.application.Platform;

/**
 * Formulaire de réservation de cours organisé en grille
 */
public class GridPaneFormulaireReservation extends GridPane {

    // Composants du formulaire
    private DatePicker datePicker;                // Sélecteur de date
    private TextField nomCoursTextField;          // Champ pour le nom du cours
    private ComboBox<Integer> heureDebutComboBox; // Heures de début (8-20)
    private ComboBox<Integer> minuteDebutComboBox; // Minutes de début (0,15,30,45)
    private ComboBox<Integer> heureFinComboBox;   // Heures de fin (8-20)
    private ComboBox<Integer> minuteFinComboBox;  // Minutes de fin (0,15,30,45)
    private ToggleGroup typeCoursTG;              // Groupe pour les boutons radio
    private RadioButton rbPresentiel, rbHybride, rbDistanciel; // Options de niveau
    private Button enregistrerButton;             // Bouton pour enregistrer
    private Button annulerButton;                 // Bouton pour annuler

    /**
     * Constructeur qui initialise le formulaire
     */
    public GridPaneFormulaireReservation() {
        // Configuration de base du GridPane
        this.setPadding(new Insets(20));
        this.setHgap(10);
        this.setVgap(10);
        this.setAlignment(Pos.CENTER);
        this.setGridLinesVisible(true); // Pour visualiser les lignes pendant la mise au point
        
        int ligne = 0;
        
        // 1. Date de réservation
        Label dateLabel = new Label("Date de réservation :");
        datePicker = new DatePicker(LocalDate.now()); // Date du jour par défaut
        datePicker.setEditable(false); // Rend le DatePicker non éditable
        datePicker.getStyleClass().add("date-picker-plain"); // Applique un style personnalisé
        this.add(dateLabel, 0, ligne);
        this.add(datePicker, 1, ligne++);
        
        // 2. Nom du cours avec mnémonique
        Label nomCoursLabel = new Label("_Nom du cours :"); // Le _ indique le mnémonique
        nomCoursLabel.setMnemonicParsing(true);
        nomCoursTextField = new TextField();
        nomCoursLabel.setLabelFor(nomCoursTextField); // Association pour l'accessibilité
        
        // Configurer le champ pour recevoir le focus initial
        nomCoursTextField.setFocusTraversable(true);
        
        this.add(nomCoursLabel, 0, ligne);
        this.add(nomCoursTextField, 1, ligne++);
        
        // 3. Type de cours avec boutons radio
        Label typeCoursLabel = new Label("Niveau :");
        
        // Groupe pour les boutons radio (une seule sélection possible)
        typeCoursTG = new ToggleGroup();
        
        // Trois options de niveau avec mnémoniques
        rbPresentiel = new RadioButton("_débutant");
        rbPresentiel.setMnemonicParsing(true);
        rbPresentiel.setToggleGroup(typeCoursTG);
        rbPresentiel.setSelected(true); // Option par défaut
        
        rbHybride = new RadioButton("_moyen");
        rbHybride.setMnemonicParsing(true);
        rbHybride.setToggleGroup(typeCoursTG);
        
        rbDistanciel = new RadioButton("_avancé");
        rbDistanciel.setMnemonicParsing(true);
        rbDistanciel.setToggleGroup(typeCoursTG);
        
        // Rangée horizontale pour les boutons radio
        HBox radioBox = new HBox(10, rbPresentiel, rbHybride, rbDistanciel);
        
        this.add(typeCoursLabel, 0, ligne);
        this.add(radioBox, 1, ligne++);
        
        // 4. Horaire de début
        Label heureDebutLabel = new Label("Heure de début :");
        
        // ComboBox pour les heures de début (8-20)
        heureDebutComboBox = new ComboBox<>();
        populateHoursComboBox(heureDebutComboBox);
        
        // ComboBox pour les minutes de début (0, 15, 30, 45)
        minuteDebutComboBox = new ComboBox<>();
        populateMinutesComboBox(minuteDebutComboBox);
        
        // Séparateur entre heures et minutes
        Label separateurDebutLabel = new Label("h");
        
        // Organisation horizontale pour l'heure de début
        HBox heureDebutBox = new HBox(5, heureDebutComboBox, separateurDebutLabel, minuteDebutComboBox);
        
        this.add(heureDebutLabel, 0, ligne);
        this.add(heureDebutBox, 1, ligne++);
        
        // 5. Horaire de fin
        Label heureFinLabel = new Label("Heure de fin :");
        
        // ComboBox pour les heures de fin (8-20)
        heureFinComboBox = new ComboBox<>();
        populateHoursComboBox(heureFinComboBox);
        
        // ComboBox pour les minutes de fin (0, 15, 30, 45)
        minuteFinComboBox = new ComboBox<>();
        populateMinutesComboBox(minuteFinComboBox);
        
        // Séparateur entre heures et minutes
        Label separateurFinLabel = new Label("h");
        
        // Organisation horizontale pour l'heure de fin
        HBox heureFinBox = new HBox(5, heureFinComboBox, separateurFinLabel, minuteFinComboBox);
        
        this.add(heureFinLabel, 0, ligne);
        this.add(heureFinBox, 1, ligne++);
        
        // 6. Boutons avec mnémoniques
        enregistrerButton = new Button("_Enregistrer");
        enregistrerButton.setMnemonicParsing(true);
        
        // Bouton Annuler avec action pour réinitialiser le formulaire
        annulerButton = new Button("_Annuler");
        annulerButton.setMnemonicParsing(true);
        annulerButton.setOnAction(e -> {
            // Réinitialisation de tous les champs
            nomCoursTextField.clear();
            datePicker.setValue(LocalDate.now());
            heureDebutComboBox.getSelectionModel().selectFirst();
            minuteDebutComboBox.getSelectionModel().selectFirst();
            heureFinComboBox.getSelectionModel().select(1); // Sélectionne 9h par défaut
            minuteFinComboBox.getSelectionModel().selectFirst();
            rbPresentiel.setSelected(true);
            nomCoursTextField.requestFocus(); // Replacement du focus
        });
        
        // Organisation horizontale pour les boutons
        HBox buttonsBox = new HBox(10, annulerButton, enregistrerButton);
        buttonsBox.setAlignment(Pos.CENTER);
        
        // Ajout des boutons sur les deux colonnes
        this.add(buttonsBox, 0, ligne, 2, 1);
        
        // Désactiver le focus traversable pour les autres composants
        // pour assurer que le nom du cours reçoive le focus initial
        datePicker.setFocusTraversable(false);
        rbPresentiel.setFocusTraversable(false);
        rbHybride.setFocusTraversable(false);
        rbDistanciel.setFocusTraversable(false);
        heureDebutComboBox.setFocusTraversable(false);
        minuteDebutComboBox.setFocusTraversable(false);
        heureFinComboBox.setFocusTraversable(false);
        minuteFinComboBox.setFocusTraversable(false);
        
        // Application des styles CSS
        this.getStyleClass().add("formulaire-reservation");
        enregistrerButton.getStyleClass().add("bouton-enregistrer");
        annulerButton.getStyleClass().add("bouton-annuler");
        
        // Donner le focus initial au champ du nom du cours
        nomCoursTextField.requestFocus();
        
        // Exécution différée pour garantir le focus
        Platform.runLater(() -> {
            nomCoursTextField.requestFocus();
            nomCoursTextField.selectAll();
        });
    }
    
    /**
     * Remplit un ComboBox avec des heures de 8 à 20
     */
    private void populateHoursComboBox(ComboBox<Integer> comboBox) {
        // Ajout des heures de 8h à 20h
        for (int heure = 8; heure <= 20; heure++) {
            comboBox.getItems().add(heure);
        }
        comboBox.getSelectionModel().selectFirst(); // Sélectionne 8h par défaut
    }
    
    /**
     * Remplit un ComboBox avec les minutes par cran de 15 minutes (0, 15, 30, 45)
     */
    private void populateMinutesComboBox(ComboBox<Integer> comboBox) {
        // Ajout des minutes par pas de 15
        comboBox.getItems().addAll(0, 15, 30, 45);
        comboBox.getSelectionModel().selectFirst(); // Sélectionne 0 minute par défaut
    }
    
    /**
     * Met à jour la date du formulaire à partir d'une sélection dans le calendrier
     * @param date La date sélectionnée
     */
    public void setDate(LocalDate date) {
        if (date != null) {
            datePicker.setValue(date);
        }
    }
}
