package models;
import java.lang.Thread;

/**
 * Classe utilitaire pour gérer les pauses dans l'exécution du programme
 */
public class Attendre {
    /**
     * Met en pause l'exécution pendant un nombre entier de secondes
     * @param s Le nombre de secondes à attendre
     */
    public static void attendreInt(int s)
    {
        // Conversion des secondes en millisecondes
        int ms = s * 1000;
        try
        {
            // Mise en pause du thread
            Thread.sleep(ms);
        }
        catch (InterruptedException e)
        {
            // En cas d'interruption, restaure le statut d'interruption
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Met en pause l'exécution pendant un nombre décimal de secondes
     * @param s Le nombre de secondes à attendre (peut être décimal)
     */
    public static void attendreDouble(double s) {
        // Conversion des secondes (décimales) en millisecondes (entier)
        int ms = (int)(s * 1000);
        try {
            // Mise en pause du thread
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            // En cas d'interruption, restaure le statut d'interruption
            Thread.currentThread().interrupt();
        }
    }
}
