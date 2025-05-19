package vue;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;

/**
 * VBox pour afficher des informations sur le planning, notamment le numéro de la semaine.
 */
public class VBoxAffichagePlanning extends VBox {

    private Label semaineLabel;

    /**
     * Constructeur de VBoxAffichagePlanning.
     * Initialise le Label avec le numéro de la semaine en cours.
     */
    public VBoxAffichagePlanning() {
        super(10); // Espacement vertical
        this.setAlignment(Pos.CENTER);
        this.setStyle("-fx-background-color: #1e1e1e; -fx-padding: 10px;");
        this.setPrefWidth(400); // Largeur similaire au calendrier pour l'exemple

        Label titre = new Label("Informations Planning");
        titre.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titre.setTextFill(Color.web("#60cdff"));
        this.getChildren().add(titre);

        semaineLabel = new Label();
        semaineLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        semaineLabel.setTextFill(Color.WHITE);
        this.getChildren().add(semaineLabel);

        // Initialiser avec la semaine de la date actuelle
        updateSemaineLabel(LocalDate.now());
    }

    /**
     * Met à jour le Label avec le numéro de la semaine pour la date donnée.
     * @param date La date pour laquelle afficher le numéro de la semaine.
     */
    public void updateSemaineLabel(LocalDate date) {
        if (date != null) {
            WeekFields weekFields = WeekFields.of(Locale.getDefault());
            int weekNumber = date.get(weekFields.weekOfWeekBasedYear());
            semaineLabel.setText("Semaine: " + weekNumber);
        } else {
            semaineLabel.setText("Semaine: N/A");
        }
    }

    /**
     * Met à jour le Label avec le numéro de la semaine pour la date donnée,
     * en utilisant la classe DateCalendrier si disponible et préférée.
     * @param dateC CalendrierDate pour laquelle afficher le numéro de la semaine.
     */
    /*
    public void updateSemaineLabel(modele.DateCalendrier dateC) {
        if (dateC != null) {
            semaineLabel.setText("Semaine: " + dateC.getWeekOfYear());
        } else {
            semaineLabel.setText("Semaine: N/A");
        }
    }
    */
}
