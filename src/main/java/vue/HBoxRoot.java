package vue;

import javafx.scene.layout.HBox;

public class HBoxRoot extends HBox {
     VBoxCalendrier calendrier = new VBoxCalendrier();
     GridPaneFormulaireReservation formulaire = new GridPaneFormulaireReservation();

     public HBoxRoot() {
          super(10); // Espacement horizontal de 10 pixels entre les composants
          
          // Ajout du calendrier à la HBox
          getChildren().addAll(calendrier);
          
     }
}
