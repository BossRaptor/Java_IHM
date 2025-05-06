package controleur;

import javafx.event.EventHandler;
import modele.PlanningCollections;

public class Controleur implements EventHandler {

    @Override
    public void handle(Event event) {
        PlanningCollections planning = HBoxRoot.getPlanning();
        GridPaneFormulaireReservation reservationpane = HBoxRoot.getReservationPane();

        if (event.getSource() instanceof ToggleButton) {

    }
}
