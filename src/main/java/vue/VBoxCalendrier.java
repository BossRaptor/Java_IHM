package vue;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import modele.DateCalendrier;
import modele.CalendrierDuMois;
import java.time.LocalDate;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Composant qui affiche un calendrier mensuel avec navigation
 */
public class VBoxCalendrier extends VBox {
    // Mois actuellement affiché (1-12)
    private int currentMonthIndex;
    // Étiquette affichant le nom du mois
    private Label monthLabel;
    // Conteneur pour empiler les 12 mois
    private StackPane stackPaneMois;
    // Date actuellement sélectionnée
    private Label selectedDateLabel;
    // Propriété observable pour la date sélectionnée
    private ObjectProperty<LocalDate> selectedDate = new SimpleObjectProperty<>();
    // Année courante
    private int anneeCourante;

    /**
     * Constructeur qui initialise le calendrier
     */
    public VBoxCalendrier() {
        super(10); // Espacement de 10 entre les éléments
        getStyleClass().add("root-container");

        // Titre de bienvenue
        Label labelHello = new Label("Bonjour !");
        labelHello.getStyleClass().add("label-primary");
        getChildren().add(labelHello);

        // Sous-titre
        Label labelHelloBis = new Label("Voici le calendrier");
        labelHelloBis.getStyleClass().add("label-secondary");
        getChildren().add(labelHelloBis);

        // Obtenir la date d'aujourd'hui et l'année courante
        LocalDate aujourdhui = LocalDate.now();
        int jourAujourdhui = aujourdhui.getDayOfMonth();
        int moisAujourdhui = aujourdhui.getMonthValue();
        anneeCourante = aujourdhui.getYear();

        // Création d'un StackPane pour empiler les 12 mois
        stackPaneMois = new StackPane();
        stackPaneMois.getStyleClass().add("stack-pane-mois");
        stackPaneMois.setPrefHeight(200); // Hauteur préférée

        // Création du conteneur principal
        BorderPane mainContainer = new BorderPane();

        // Création du conteneur pour le mois et les boutons
        HBox bottomContainer = new HBox(10);
        bottomContainer.setAlignment(Pos.CENTER); // Alignement centré

        // Label pour le mois
        monthLabel = new Label("");
        monthLabel.getStyleClass().add("month-label");

        // Bouton pour le mois précédent
        Button prevMonthBtn = new Button("←");
        prevMonthBtn.getStyleClass().add("nav-button");
        prevMonthBtn.setOnAction(e -> showPrevMonth());

        // Bouton pour le mois suivant
        Button nextMonthBtn = new Button("→");
        nextMonthBtn.getStyleClass().add("nav-button");
        nextMonthBtn.setOnAction(e -> showNextMonth());

        bottomContainer.getChildren().addAll(prevMonthBtn, monthLabel, nextMonthBtn);

        // Placement des éléments
        mainContainer.setCenter(stackPaneMois);
        mainContainer.setBottom(bottomContainer);

        // Création des 12 mois du calendrier
        for (int mois = 1; mois <= 12; mois++) {
            // Obtenir les données du calendrier pour ce mois
            CalendrierDuMois calendrier = new CalendrierDuMois(mois, anneeCourante);

            // Conteneur pour un mois
            VBox monthContainer = new VBox(5);
            monthContainer.getStyleClass().add("month-container");

            // Création de l'en-tête pour les jours de la semaine
            HBox weekDaysHeader = new HBox(5);
            weekDaysHeader.getStyleClass().add("week-days-header");

            // Ajouter les en-têtes des jours de la semaine
            String[] joursAbrev = { "Lu", "Ma", "Me", "Je", "Ve", "Sa", "Di" };
            for (String jour : joursAbrev) {
                Label labelJour = new Label(jour);
                labelJour.getStyleClass().add("label-jour-semaine");
                weekDaysHeader.getChildren().add(labelJour);
            }

            monthContainer.getChildren().add(weekDaysHeader);

            // Création de la grille pour les dates
            GridPane datesGrid = new GridPane();
            datesGrid.getStyleClass().add("dates-container");
            datesGrid.setHgap(5);
            datesGrid.setVgap(5);

            // Calcul de la position du premier jour du mois
            DateCalendrier premierJour = null;
            for (DateCalendrier date : calendrier.getDates()) {
                if (date.getJour() == 0) {
                    premierJour = date;
                    break;
                }
            }

            int jourSemainePremierJour = (premierJour != null) ? premierJour.getJourSemaine() : 1;
            int column = jourSemainePremierJour - 1;
            if (column < 0)
                column = 6;
            int row = 0;

            // Ajout de toutes les dates du mois à la grille
            for (DateCalendrier date : calendrier.getDates()) {
                int jour = date.getJour();
                if (jour > 0) { // On n'affiche que les jours valides (pas les 0)
                    Label labelDate = new Label(String.valueOf(jour));
                    labelDate.getStyleClass().add("label-date");
                    
                    // Stocker les informations de date dans les propriétés du label
                    labelDate.getProperties().put("jour", jour);
                    labelDate.getProperties().put("mois", mois);
                    labelDate.getProperties().put("annee", anneeCourante);

                    // Mettre en évidence la date du jour
                    if (date.getJour() == jourAujourdhui &&
                            date.getMois() == moisAujourdhui &&
                            date.getAnnee() == anneeCourante) {
                        labelDate.getStyleClass().add("today");
                        // Sélection par défaut de la date du jour
                        selectedDateLabel = labelDate;
                        selectedDate.set(LocalDate.of(anneeCourante, mois, jour));
                        labelDate.getStyleClass().add("selected");
                    }

                    // Ajouter un gestionnaire de clic pour sélectionner la date
                    final Label finalLabelDate = labelDate;
                    labelDate.setOnMouseClicked(e -> {
                        selectDate(finalLabelDate);
                    });

                    // Ajouter la date à la grille et passer à la position suivante
                    datesGrid.add(labelDate, column, row);
                }
                
                column++;
                if (column > 6) {
                    column = 0;
                    row++;
                }
            }

            // Ajouter la grille de dates au conteneur du mois
            monthContainer.getChildren().add(datesGrid);
            // Ajouter le mois au stack pane
            stackPaneMois.getChildren().add(monthContainer);
            // Marquer le mois pour le retrouver plus tard
            monthContainer.setAccessibleText(String.valueOf(mois));
        }

        // Initialiser le mois courant et l'afficher
        currentMonthIndex = moisAujourdhui;
        updateMonthDisplay();
        showCurrentMonth();

        // Ajouter tout au conteneur principal
        getChildren().add(mainContainer);
    }
    
    /**
     * Sélectionne une date dans le calendrier
     * @param dateLabel L'étiquette de la date à sélectionner
     */
    private void selectDate(Label dateLabel) {
        // Désélectionner l'ancienne date si elle existe
        if (selectedDateLabel != null) {
            selectedDateLabel.getStyleClass().remove("selected");
        }
        
        // Sélectionner la nouvelle date
        selectedDateLabel = dateLabel;
        dateLabel.getStyleClass().add("selected");
        
        // Récupérer les informations de date
        int jour = (Integer) dateLabel.getProperties().get("jour");
        int mois = (Integer) dateLabel.getProperties().get("mois");
        int annee = (Integer) dateLabel.getProperties().get("annee");
        
        // Mettre à jour la propriété observable
        selectedDate.set(LocalDate.of(annee, mois, jour));
    }
    
    /**
     * Retourne la propriété observable de la date sélectionnée
     */
    public ObjectProperty<LocalDate> selectedDateProperty() {
        return selectedDate;
    }
    
    /**
     * Retourne la date actuellement sélectionnée
     */
    public LocalDate getSelectedDate() {
        return selectedDate.get();
    }

    /**
     * Affiche le mois suivant
     */
    private void showNextMonth() {
        currentMonthIndex = (currentMonthIndex % 12) + 1;
        updateMonthDisplay();
        showCurrentMonth();
    }

    /**
     * Affiche le mois précédent
     */
    private void showPrevMonth() {
        currentMonthIndex = (currentMonthIndex > 1) ? (currentMonthIndex - 1) : 12;
        updateMonthDisplay();
        showCurrentMonth();
    }

    /**
     * Met à jour l'affichage du nom du mois
     */
    private void updateMonthDisplay() {
        CalendrierDuMois calendrier = new CalendrierDuMois(currentMonthIndex, LocalDate.now().getYear());

        // Convertir le numéro du mois en nom de mois
        String[] nomsMois = { "", "Janvier", "Février", "Mars", "Avril", "Mai", "Juin",
                "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre" };

        String nomMois = nomsMois[currentMonthIndex];
        monthLabel.setText(nomMois);
    }

    /**
     * Affiche le mois courant en le mettant au premier plan
     */
    private void showCurrentMonth() {
        String monthToShow = String.valueOf(currentMonthIndex);
        for (javafx.scene.Node node : stackPaneMois.getChildren()) {
            if (node instanceof VBox) {
                VBox monthContainer = (VBox) node;
                if (monthContainer.getAccessibleText().equals(monthToShow)) {
                    monthContainer.toFront();
                    break;
                }
            }
        }
    }

    /**
     * Affiche le premier mois (janvier)
     */
    private void showFirstMonth() {
        currentMonthIndex = 1;
        updateMonthDisplay();
        showCurrentMonth();
    }

    /**
     * Affiche le dernier mois (décembre)
     */
    private void showLastMonth() {
        currentMonthIndex = 12;
        updateMonthDisplay();
        showCurrentMonth();
    }

    /**
     * Méthode principale pour tester le composant
     */
    public static void main(String[] args) {
        PremiereApplication.main(args);
    }
}
