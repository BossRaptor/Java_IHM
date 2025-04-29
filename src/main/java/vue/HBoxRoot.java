package vue;

import javafx.scene.layout.HBox;

public class HBoxRoot extends HBox {
     VBoxCalendrier calendrier = new VBoxCalendrier();
     GridPaneFormulaireReservation formulaire = new GridPaneFormulaireReservation();

     public HBoxRoot() {
          super(20); // Espacement horizontal de 20 pixels entre les composants
          
          getChildren().add(calendrier);

          getChildren().add(formulaire);
     }
}
