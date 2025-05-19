package controleur;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ToggleButton;
import modele.PlanningCollections;
import vue.HBoxRoot;
import vue.GridPaneFormulaireReservation;

public class Controleur implements EventHandler {

    @Override
    public void handle(Event event) {
        PlanningCollections planning = HBoxRoot.getPlanning();
        GridPaneFormulaireReservation reservationpane = HBoxRoot.getReservationPane();

        if (event.getSource() instanceof ToggleButton) {

    }
}
