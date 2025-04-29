package vue;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Formulaire de réservation simple
 */
public class GridPaneFormulaireReservation extends GridPane {

    // Composants du formulaire
    private TextField dateTextField;            // Simple champ texte pour la date
    private TextField nomCoursTextField;        // Champ pour le nom du cours
    private ComboBox<String> heureDebutComboBox;    // Heures de début (format "HH:MM")
    private ComboBox<String> heureFinComboBox;      // Heures de fin (format "HH:MM")
    private RadioButton rbDebutant, rbMoyen, rbAvance;  // Niveaux
    private Button enregistrerButton;           // Bouton pour enregistrer
    private Button annulerButton;               // Bouton pour annuler

    /**
     * Constructeur qui initialise le formulaire
     */
    public GridPaneFormulaireReservation() {
        // Configuration de base du GridPane
        this.setPadding(new Insets(20));
        this.setHgap(10);
        this.setVgap(10);
        this.setAlignment(Pos.CENTER);
        this.setStyle("-fx-background-color: #1e1e1e; -fx-padding: 15px;");
        this.setPrefWidth(400); // Même largeur que le calendrier
        
        int ligne = 0;
        
        // Titre du formulaire
        Label titreFormulaire = new Label("Formulaire de réservation");
        titreFormulaire.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #60cdff;");
        titreFormulaire.setMaxWidth(Double.MAX_VALUE); // Permet au label de prendre toute la largeur
        titreFormulaire.setAlignment(Pos.CENTER);
        this.add(titreFormulaire, 0, ligne++, 2, 1);
        
        // Espace après le titre
        this.add(new Label(""), 0, ligne++);
        
        // 1. Date de réservation (simple TextField non-éditable)
        Label dateLabel = new Label("Date de réservation :");
        dateLabel.setStyle("-fx-text-fill: #d0d0d0; -fx-font-weight: bold;");
        dateTextField = new TextField();
        dateTextField.setEditable(false);  // Ne peut pas être modifié directement
        dateTextField.setStyle("-fx-background-color: #333333; -fx-text-fill: white;");
        // Date initiale (aujourd'hui)
        dateTextField.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        this.add(dateLabel, 0, ligne);
        this.add(dateTextField, 1, ligne++);
        
        // 2. Nom du cours
        Label nomCoursLabel = new Label("Nom du cours :");
        nomCoursLabel.setStyle("-fx-text-fill: #d0d0d0; -fx-font-weight: bold;");
        nomCoursTextField = new TextField();
        nomCoursTextField.setStyle("-fx-background-color: #333333; -fx-text-fill: white;");
        this.add(nomCoursLabel, 0, ligne);
        this.add(nomCoursTextField, 1, ligne++);
        
        // 3. Niveau avec boutons radio
        Label niveauLabel = new Label("Niveau :");
        niveauLabel.setStyle("-fx-text-fill: #d0d0d0; -fx-font-weight: bold;");
        
        // Groupe pour les boutons radio
        ToggleGroup niveauGroup = new ToggleGroup();
        
        // Options de niveau
        rbDebutant = new RadioButton("_Débutant");
        rbDebutant.setMnemonicParsing(true);  // Activer le raccourci clavier
        rbDebutant.setStyle("-fx-text-fill: #d0d0d0;");
        rbDebutant.setToggleGroup(niveauGroup);
        rbDebutant.setSelected(true);  // Option par défaut
        
        rbMoyen = new RadioButton("_Moyen");
        rbMoyen.setMnemonicParsing(true);  // Activer le raccourci clavier
        rbMoyen.setStyle("-fx-text-fill: #d0d0d0;");
        rbMoyen.setToggleGroup(niveauGroup);
        
        rbAvance = new RadioButton("_Avancé");
        rbAvance.setMnemonicParsing(true);  // Activer le raccourci clavier
        rbAvance.setStyle("-fx-text-fill: #d0d0d0;");
        rbAvance.setToggleGroup(niveauGroup);
        
        // Rangée horizontale pour les boutons radio
        HBox radioBox = new HBox(10, rbDebutant, rbMoyen, rbAvance);
        
        this.add(niveauLabel, 0, ligne);
        this.add(radioBox, 1, ligne++);
        
        // 4. Horaire de début
        Label heureDebutLabel = new Label("Heure de début :");
        heureDebutLabel.setStyle("-fx-text-fill: #d0d0d0; -fx-font-weight: bold;");
        heureDebutComboBox = new ComboBox<>();
        heureDebutComboBox.setStyle("-fx-background-color: #333333; -fx-text-fill: white;");
        
        // Remplir avec des heures prédéfinies (par tranches de 15 minutes)
        for (int heure = 8; heure <= 20; heure++) {
            heureDebutComboBox.getItems().add(heure + ":00");
            heureDebutComboBox.getItems().add(heure + ":15");
            heureDebutComboBox.getItems().add(heure + ":30");
            heureDebutComboBox.getItems().add(heure + ":45");
        }
        heureDebutComboBox.setValue("8:00");  // Valeur par défaut
        
        this.add(heureDebutLabel, 0, ligne);
        this.add(heureDebutComboBox, 1, ligne++);
        
        // 5. Horaire de fin
        Label heureFinLabel = new Label("Heure de fin :");
        heureFinLabel.setStyle("-fx-text-fill: #d0d0d0; -fx-font-weight: bold;");
        heureFinComboBox = new ComboBox<>();
        heureFinComboBox.setStyle("-fx-background-color: #333333; -fx-text-fill: white;");
        
        // Remplir avec des heures prédéfinies (par tranches de 15 minutes)
        for (int heure = 8; heure <= 20; heure++) {
            heureFinComboBox.getItems().add(heure + ":00");
            heureFinComboBox.getItems().add(heure + ":15");
            heureFinComboBox.getItems().add(heure + ":30");
            heureFinComboBox.getItems().add(heure + ":45");
        }
        heureFinComboBox.setValue("9:00");  // Valeur par défaut
        
        this.add(heureFinLabel, 0, ligne);
        this.add(heureFinComboBox, 1, ligne++);
        
        // Espace avant les boutons
        this.add(new Label(""), 0, ligne++);
        
        // 6. Boutons
        enregistrerButton = new Button("_Enregistrer");
        enregistrerButton.setMnemonicParsing(true);  // Activer le raccourci clavier
        enregistrerButton.setStyle("-fx-background-color: #3d5a73; -fx-text-fill: #e0e0e0; -fx-font-weight: bold;");
        enregistrerButton.setPrefWidth(120);
        // Effet au survol du bouton
        enregistrerButton.setOnMouseEntered(e -> enregistrerButton.setStyle("-fx-background-color: #60cdff; -fx-text-fill: #121212; -fx-font-weight: bold;"));
        enregistrerButton.setOnMouseExited(e -> enregistrerButton.setStyle("-fx-background-color: #3d5a73; -fx-text-fill: #e0e0e0; -fx-font-weight: bold;"));
        
        annulerButton = new Button("An_nuler");
        annulerButton.setMnemonicParsing(true);  // Activer le raccourci clavier
        annulerButton.setStyle("-fx-background-color: #4d3339; -fx-text-fill: #e0e0e0; -fx-font-weight: bold;");
        annulerButton.setPrefWidth(120);
        // Effet au survol du bouton
        annulerButton.setOnMouseEntered(e -> annulerButton.setStyle("-fx-background-color: #ff6b6b; -fx-text-fill: #121212; -fx-font-weight: bold;"));
        annulerButton.setOnMouseExited(e -> annulerButton.setStyle("-fx-background-color: #4d3339; -fx-text-fill: #e0e0e0; -fx-font-weight: bold;"));
        
        annulerButton.setOnAction(e -> {
            // Réinitialisation de tous les champs
            nomCoursTextField.clear();
            dateTextField.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            heureDebutComboBox.setValue("8:00");
            heureFinComboBox.setValue("9:00");
            rbDebutant.setSelected(true);
            nomCoursTextField.requestFocus();  // Replacement du focus
        });
        
        // Organisation horizontale pour les boutons
        HBox buttonsBox = new HBox(10, annulerButton, enregistrerButton);
        buttonsBox.setAlignment(Pos.CENTER);
        
        // Ajout des boutons sur les deux colonnes
        this.add(buttonsBox, 0, ligne, 2, 1);
        
        // Donner le focus initial au champ du nom du cours
        nomCoursTextField.requestFocus();
    }
    
    /**
     * Met à jour la date du formulaire
     * @param date La date à afficher
     */
    public void setDate(LocalDate date) {
        if (date != null) {
            dateTextField.setText(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }
    }
}
