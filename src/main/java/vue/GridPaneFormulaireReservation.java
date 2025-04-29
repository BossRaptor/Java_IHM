package vue;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class GridPaneFormulaireReservation extends GridPane {

    // Composants du formulaire
    private DatePicker datePicker;
    private TextField nomCoursTextField;
    private ComboBox<String> heureDebutComboBox;
    private ComboBox<String> heureFinComboBox;
    private ToggleGroup typeCoursTG;
    private RadioButton rbPresentiel, rbHybride, rbDistanciel;
    private Button enregistrerButton;
    private Button annulerButton;

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
        datePicker = new DatePicker(LocalDate.now());
        this.add(dateLabel, 0, ligne);
        this.add(datePicker, 1, ligne++);
        
        // 2. Nom du cours avec mnémonique
        Label nomCoursLabel = new Label("_Nom du cours :");
        nomCoursLabel.setMnemonicParsing(true);
        nomCoursTextField = new TextField();
        nomCoursLabel.setLabelFor(nomCoursTextField);
        this.add(nomCoursLabel, 0, ligne);
        this.add(nomCoursTextField, 1, ligne++);
        
        // 3. Type de cours avec boutons radio
        Label typeCoursLabel = new Label("Type de cours :");
        
        typeCoursTG = new ToggleGroup();
        
        rbPresentiel = new RadioButton("_Présentiel");
        rbPresentiel.setMnemonicParsing(true);
        rbPresentiel.setToggleGroup(typeCoursTG);
        rbPresentiel.setSelected(true);
        
        rbHybride = new RadioButton("_Hybride");
        rbHybride.setMnemonicParsing(true);
        rbHybride.setToggleGroup(typeCoursTG);
        
        rbDistanciel = new RadioButton("_Distanciel");
        rbDistanciel.setMnemonicParsing(true);
        rbDistanciel.setToggleGroup(typeCoursTG);
        
        HBox radioBox = new HBox(10, rbPresentiel, rbHybride, rbDistanciel);
        
        this.add(typeCoursLabel, 0, ligne);
        this.add(radioBox, 1, ligne++);
        
        // 4. Horaire de début
        Label heureDebutLabel = new Label("Heure de début :");
        heureDebutComboBox = new ComboBox<>();
        populateHoursComboBox(heureDebutComboBox);
        this.add(heureDebutLabel, 0, ligne);
        this.add(heureDebutComboBox, 1, ligne++);
        
        // 5. Horaire de fin
        Label heureFinLabel = new Label("Heure de fin :");
        heureFinComboBox = new ComboBox<>();
        populateHoursComboBox(heureFinComboBox);
        this.add(heureFinLabel, 0, ligne);
        this.add(heureFinComboBox, 1, ligne++);
        
        // 6. Boutons avec mnémoniques
        enregistrerButton = new Button("_Enregistrer");
        enregistrerButton.setMnemonicParsing(true);
        
        annulerButton = new Button("_Annuler");
        annulerButton.setMnemonicParsing(true);
        annulerButton.setOnAction(e -> {
            nomCoursTextField.clear();
            datePicker.setValue(LocalDate.now());
            heureDebutComboBox.getSelectionModel().selectFirst();
            heureFinComboBox.getSelectionModel().selectFirst();
            rbPresentiel.setSelected(true);
            nomCoursTextField.requestFocus();
        });
        
        HBox buttonsBox = new HBox(10, annulerButton, enregistrerButton);
        buttonsBox.setAlignment(Pos.CENTER);
        
        this.add(buttonsBox, 0, ligne, 2, 1);
        
        // Styles CSS
        this.getStyleClass().add("formulaire-reservation");
        enregistrerButton.getStyleClass().add("bouton-enregistrer");
        annulerButton.getStyleClass().add("bouton-annuler");
        
        // Donner le focus initial au champ du nom du cours
        javafx.application.Platform.runLater(() -> nomCoursTextField.requestFocus());
    }
    
    /**
     * Remplit un ComboBox avec des heures de 8h00 à 20h00 par tranches de 30 minutes
     */
    private void populateHoursComboBox(ComboBox<String> comboBox) {
        LocalTime time = LocalTime.of(8, 0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        
        while (!time.isAfter(LocalTime.of(20, 0))) {
            comboBox.getItems().add(time.format(formatter));
            time = time.plusMinutes(30);
        }
        
        comboBox.getSelectionModel().selectFirst();
    }
}
