package modele;

import java.util.Calendar;

/**
 * Classe représentant une date spécifique dans un calendrier
 * Étend la classe Date et ajoute des fonctionnalités spécifiques au calendrier
 */
public class DateCalendrier extends Date implements JoursSemaine, Mois, Comparable<Date> {

    private int jourSemaine; // Numéro du jour dans la semaine (1-7)
    private int weekOfYear;  // Numéro de la semaine dans l'année

    /**
     * Constructeur par défaut qui crée une date correspondant à aujourd'hui
     */
    public DateCalendrier() {
        // Obtenir la date actuelle du système
        Calendar dateAuj = Calendar.getInstance();
        chAnnee = dateAuj.get(Calendar.YEAR);
        chMois = dateAuj.get(Calendar.MONTH) + 1; // +1 car MONTH commence à 0 dans Calendar
        chJour = dateAuj.get(Calendar.DAY_OF_MONTH);
        
        // Conversion du jour de la semaine (dimanche=1 dans Calendar, mais dimanche=7 pour nous)
        jourSemaine = dateAuj.get(Calendar.DAY_OF_WEEK);
        if (jourSemaine == 1)
            jourSemaine = 7;
        else
            jourSemaine -= 1;
            
        weekOfYear = dateAuj.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * Constructeur avec paramètres pour une date spécifique
     */
    public DateCalendrier(int parJour, int parMois, int parAnnee) {
        super(parJour, parMois, parAnnee);
        
        // Calcul du jour de la semaine pour cette date
        Calendar date = Calendar.getInstance();
        date.set(chAnnee, chMois - 1, chJour);
        jourSemaine = date.get(Calendar.DAY_OF_WEEK);
        if (jourSemaine == 1)
            jourSemaine = 7;
        else
            jourSemaine -= 1;
            
        weekOfYear = date.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * Renvoie une représentation textuelle de la date (jour semaine, jour, mois)
     */
    public String toString() {
        return JOURS_SEMAINE[jourSemaine - 1] + " " + chJour + " " + MOIS[chMois - 1];
    }

    /**
     * Renvoie le numéro du jour dans la semaine (1-7)
     */
    public int getJourSemaine() {
        return jourSemaine;
    }

    /**
     * Vérifie si la date est aujourd'hui
     */
    public boolean isToday() {
        return this.compareTo(new DateCalendrier()) == 0;
    }

    /**
     * Renvoie le numéro de semaine dans l'année
     */
    public int getWeekOfYear() {
        return weekOfYear;
    }

    /**
     * Calcule la date du lendemain
     */
    public DateCalendrier dateDuLendemain() {
        Date dateLendemain = super.dateDuLendemain();
        return new DateCalendrier(dateLendemain.chJour, dateLendemain.chMois, dateLendemain.chAnnee);
    }

    /**
     * Calcule la date de la veille
     */
    public DateCalendrier dateDeLaVeille() {
        Date dateVeille = super.dateDeLaVeille();
        return new DateCalendrier(dateVeille.chJour, dateVeille.chMois, dateVeille.chAnnee);
    }
}